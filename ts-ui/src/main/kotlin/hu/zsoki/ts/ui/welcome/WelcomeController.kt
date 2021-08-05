package hu.zsoki.ts.ui.welcome

import hu.zsoki.ts.crawler.Crawler
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class WelcomeController : Controller() {

    val ieDriverPathProperty = SimpleStringProperty()
    val timesheetUrlProperty = SimpleStringProperty()

    private val crawler = Crawler()

    fun onLaunch() {
        crawler.startTimeSheetFillout(ieDriverPathProperty.value, timesheetUrlProperty.value)
    }
}