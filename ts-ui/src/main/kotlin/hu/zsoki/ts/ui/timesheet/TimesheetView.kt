package hu.zsoki.ts.ui.timesheet

import javafx.collections.FXCollections
import javafx.scene.control.cell.ComboBoxTableCell
import javafx.scene.layout.Priority
import tornadofx.*
import java.time.LocalTime

class TimesheetView : View("Timesheet") {
    private val controller: TimesheetController by inject()

    override val root = vbox(10) {
        paddingAll = 20

        // Test data
        val testData = FXCollections.observableArrayList("Item 1", "Item 2", "Item 3")
        val records = listOf(
            TimesheetRecordVO("Project 1", "Task 1", "Bugfixing", LocalTime.of(9, 0), LocalTime.of(0, 30)),
            TimesheetRecordVO("Project 2", "Task 2", "Support", LocalTime.of(10, 0), LocalTime.of(6, 0)),
            TimesheetRecordVO("Onboarding", "Environment setup", "Docker setup", LocalTime.of(16, 0), LocalTime.of(1, 0))
        ).asObservable()
        // Test data end

        label("History quick fill")
        hbox(10) {
            textfield { hboxConstraints { hGrow = Priority.ALWAYS } }
            button("Fill") {
                minWidth = 80.0
            }
        }

        gridpane {
            row {
                label("Project")
                label("Task")
                label("Note")
            }
            row {
                combobox(controller.selectedProjectProperty, controller.projects) {
                    useMaxWidth = true
                    gridpaneConstraints {
                        marginRight = 10.0
                        hGrow = Priority.ALWAYS
                    }
                }
                combobox(controller.selectedTaskProperty, controller.tasks) {
                    useMaxWidth = true
                    gridpaneConstraints {
                        marginRight = 10.0
                        hGrow = Priority.ALWAYS
                    }
                }
                combobox(controller.selectedNoteProperty, controller.notes) {
                    isEditable = true
                    useMaxWidth = true
                    gridpaneConstraints {
                        marginRight = 10.0
                        hGrow = Priority.ALWAYS
                    }
                }
            }
        }

        tableview(records) {
            vboxConstraints { marginTop = 20.0 }
            column("Project", TimesheetRecordVO::projectProperty).makeEditable().apply {
                setCellValueFactory { it.value.projectProperty }
                setCellFactory(ComboBoxTableCell.forTableColumn(testData))
            }
            column("Task", TimesheetRecordVO::taskProperty).makeEditable()
            column("Note", TimesheetRecordVO::noteProperty).makeEditable()
            readonlyColumn("From", TimesheetRecordVO::from)
            readonlyColumn("To", TimesheetRecordVO::to)
            readonlyColumn("Duration", TimesheetRecordVO::duration)
        }
    }

    override fun onDock() {
        super.onDock()
        controller.loadProjects()
    }
}
