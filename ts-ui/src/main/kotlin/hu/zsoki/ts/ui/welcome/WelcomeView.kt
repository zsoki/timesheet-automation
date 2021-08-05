package hu.zsoki.ts.ui.welcome

import javafx.scene.layout.Priority
import javafx.stage.FileChooser
import tornadofx.*

class WelcomeView : View("Timesheet Automation") {

    val controller: WelcomeController by inject()

    override val root = vbox(10) {
        paddingAll = 20

        label("Path to IEDriverServer.exe")
        hbox(10) {
            textfield(controller.ieDriverPathProperty) { hboxConstraints { hGrow = Priority.ALWAYS } }
            button("Browse") {
                action {
                    val files = chooseFile("Select IEDriverServer.exe", arrayOf(FileChooser.ExtensionFilter("Executable", "*.exe")))
                    if (files.isNotEmpty()) {
                        controller.ieDriverPathProperty.value = files[0].path
                    }
                }
            }
        }

        label("Timesheet URL")
        hbox(10) {
            vboxConstraints { marginBottom = 10.0 }
            textfield(controller.timesheetUrlProperty) { hboxConstraints { hGrow = Priority.ALWAYS } }
        }

        button("Launch") {
            action {
                runAsync {
                    controller.onLaunch()
                }
            }
        }
    }
}