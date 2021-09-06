package hu.zsoki.ts.ui.automation

import hu.zsoki.ts.config.TsAppConfig
import hu.zsoki.ts.crawler.Crawler
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class AutomationController : Controller() {

    val ieDriverPathProperty = SimpleStringProperty(TsAppConfig.ieDriverPath)
    val timesheetUrlProperty = SimpleStringProperty(TsAppConfig.timesheetUrl)

    private val crawler = Crawler()

    fun onLaunch() {
        TsAppConfig.ieDriverPath = ieDriverPathProperty.value
        TsAppConfig.timesheetUrl = timesheetUrlProperty.value
        TsAppConfig.overWriteConfig()
        crawler.startTimeSheetFillout(ieDriverPathProperty.value, timesheetUrlProperty.value)
    }
}