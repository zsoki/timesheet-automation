package hu.zsoki.ts.crawler

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.ie.InternetExplorerDriver
import java.time.Duration

class Crawler {
    fun startTimeSheetFillout(ieDriverPath: String, timesheetUrl: String) {
        System.setProperty("webdriver.ie.driver", ieDriverPath)
        val driver: WebDriver = InternetExplorerDriver()

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40))
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30))

        driver.get(timesheetUrl)

        navigateToTimeSheet(driver)

        var dateIndex = 0
        do {
            println("Navigated to time sheet.")

            val dateRows = findRows(driver)
            dateRows[dateIndex].click()

            println("Navigated to daily records.")

            driver.findElement(By.cssSelector("table.ax-bodyareaframe * table * input[value=\"Új órasor\"]")).click()
            val taskRowWebElements = findRows(driver)

            val taskRecords = taskRowWebElements
                .map { rowWebElement -> rowWebElement.findElements(By.tagName("td")) }

            val chooseElement = taskRecords.find { it[1].text.startsWith('<') }

            taskRecords.filterNot { it[1].text.startsWith('<') }
                .forEach { println(it) }

            driver.navigate().back()

            dateIndex++
        } while (dateIndex < dateRows.size)

    }

}

class TaskRecord(element: List<WebElement>) {
    val project = element[1].text
    val task = element[2].text
    val from = element[3].text
    val to = element[4].text
    val note = element[9].text

    override fun toString(): String {
        return "TaskRecord(project='$project', task='$task', from='$from', to='$to', note='$note')"
    }
}

class DateRecord(element: WebElement) {
    var choice: Int = 0
    val dateElement: WebElement = element.findElement(By.cssSelector(":nth-child(1)"))
    val dateText = dateElement.text
    val workday = !dateElement.getAttribute("style").contains("db5c5c")
}

private fun navigateToTimeSheet(driver: WebDriver) {
    val timeSheetRows = findRows(driver)

    class TimeSheetRecord(element: WebElement) {
        var choice: Int = 0
        val link: String = element.findElement(By.tagName("a")).getAttribute("href")
        val from: String = element.findElement(By.cssSelector(":nth-child(3)")).text
        val to: String = element.findElement(By.cssSelector(":nth-child(4)")).text
    }

    val timeSheetRecords = timeSheetRows
        .map(::TimeSheetRecord)
        .sortedByDescending(TimeSheetRecord::from)
        .mapIndexed { i, timeSheetRecord ->
            timeSheetRecord.choice = i
            println("Choice [${timeSheetRecord.choice}] --> ${timeSheetRecord.from} - ${timeSheetRecord.to}")
            timeSheetRecord
        }

    driver.get(timeSheetRecords[chooseNumericOption(timeSheetRecords.size)].link)
}

private fun findRows(driver: WebDriver) = driver.findElements(By.cssSelector("table.dyn-gridviewtable > tbody > tr")).dropLast(1)

private fun chooseNumericOption(max: Int): Int {
    println("\nChoose an option:")

    var choice: Int = -1
    while (choice == -1) {
        val input = readLine()
        if (input != null && input.matches(Regex("\\d+")) && input.toInt() >= 0 && input.toInt() < max) {
            choice = input.toInt()
        }
    }
    return choice
}