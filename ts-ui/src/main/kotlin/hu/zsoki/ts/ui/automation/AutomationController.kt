package hu.zsoki.ts.ui.automation

import hu.zsoki.ts.config.TsAppConfigSpec
import hu.zsoki.ts.config.overWriteConfig
import hu.zsoki.ts.config.tsAppConfig
import hu.zsoki.ts.crawler.Crawler
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class AutomationController : Controller() {

    val ieDriverPathProperty = SimpleStringProperty(tsAppConfig[TsAppConfigSpec.ieDriverPath])
    val timesheetUrlProperty = SimpleStringProperty(tsAppConfig[TsAppConfigSpec.timesheetUrl])

    private val crawler = Crawler()

    fun onLaunch() {
        tsAppConfig[TsAppConfigSpec.ieDriverPath] = ieDriverPathProperty.value
        tsAppConfig[TsAppConfigSpec.timesheetUrl] = timesheetUrlProperty.value
        tsAppConfig.overWriteConfig()
        crawler.startTimeSheetFillout(ieDriverPathProperty.value, timesheetUrlProperty.value)
    }
}