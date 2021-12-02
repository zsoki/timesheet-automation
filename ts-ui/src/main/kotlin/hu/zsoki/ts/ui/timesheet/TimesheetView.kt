package hu.zsoki.ts.ui.timesheet

import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.control.cell.ComboBoxTableCell
import javafx.scene.layout.Priority
import tornadofx.*

class TimesheetView : View("Timesheet") {
    private val controller: TimesheetController by inject()

    override val root = vbox(10) {
        paddingAll = 20

        // Test data
        val testData = FXCollections.observableArrayList("Item 1", "Item 2", "Item 3")
        // Test data end

        label("History quick fill")
        hbox(10) {
            textfield(controller.quickFillProperty) { hboxConstraints { hGrow = Priority.ALWAYS } }
            button("Fill") {
                minWidth = 80.0
                action {
                    controller.fill()
                }
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
                        margin = Insets(0.0, 10.0, 10.0, 0.0)
                        hGrow = Priority.ALWAYS
                    }
                }
                combobox(controller.selectedTaskProperty, controller.tasks) {
                    useMaxWidth = true
                    gridpaneConstraints {
                        margin = Insets(0.0, 10.0, 10.0, 0.0)
                        hGrow = Priority.ALWAYS
                    }
                }
                combobox(controller.selectedNoteProperty, controller.notes) {
                    isEditable = true
                    useMaxWidth = true
                    gridpaneConstraints {
                        columnSpan = 2
                        margin = Insets(0.0, 10.0, 10.0, 0.0)
                        hGrow = Priority.ALWAYS
                    }
                }
            }
            row {
                label("Duration")
                label("From")
                label("To")
            }
            row {
                textfield(controller.durationProperty) {
                    gridpaneConstraints {
                        margin = Insets(0.0, 10.0, 10.0, 0.0)
                    }
                }
                textfield(controller.fromProperty) {
                    gridpaneConstraints {
                        margin = Insets(0.0, 10.0, 10.0, 0.0)
                    }
                }
                textfield(controller.toProperty) {
                    gridpaneConstraints {
                        margin = Insets(0.0, 10.0, 10.0, 0.0)
                    }
                }
                button("Commit") {
                    minWidth = 80.0
                    gridpaneConstraints {
                        margin = Insets(0.0, 10.0, 10.0, 0.0)
                    }
                    action { controller.commit() }
                }
            }
        }

        tableview(controller.loggedHourRecords) {
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
        controller.initLoggedHourRecords()
    }
}
