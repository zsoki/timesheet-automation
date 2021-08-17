package hu.zsoki.ts.ui.drawer

import hu.zsoki.ts.ui.automation.AutomationView
import hu.zsoki.ts.ui.timesheet.TimesheetView
import tornadofx.*

class DrawerView : View("Timesheet 2") {
    override val root = drawer {
        item(TimesheetView::class)
        item(AutomationView::class)
    }
}