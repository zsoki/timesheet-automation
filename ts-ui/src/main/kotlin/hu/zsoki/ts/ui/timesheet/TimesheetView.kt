package hu.zsoki.ts.ui.timesheet

import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.control.TableCell
import javafx.scene.control.cell.ComboBoxTableCell
import javafx.scene.layout.Priority
import tornadofx.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

        vbox {
            vboxConstraints { marginTop = 20.0 }
            hbox {
                label("Select date to log hours:") {
                    paddingRight = 10
                }
                datepicker(controller.selectedDateProperty)
            }
            tableview(controller.timesheetRows) {
                vboxConstraints { marginTop = 10.0 }
                column("Project", TimesheetRowViewModel::projectProperty).makeEditable().apply {
                    setCellValueFactory { it.value.projectProperty }
                    setCellFactory(ComboBoxTableCell.forTableColumn(testData))
                }
                column("Task", TimesheetRowViewModel::taskProperty).makeEditable()
                column("Note", TimesheetRowViewModel::noteProperty).makeEditable()
                readonlyColumn("From", TimesheetRowViewModel::from).apply {
                    setCellValueFactory { it.value.fromProperty }
                    setCellFactory { TimesheetRecordFormattedDateTimeCell() }
                }
                readonlyColumn("To", TimesheetRowViewModel::to).apply {
                    setCellValueFactory { it.value.toProperty }
                    setCellFactory { TimesheetRecordFormattedDateTimeCell() }
                }
                readonlyColumn("Duration", TimesheetRowViewModel::duration).apply {
                    setCellValueFactory { it.value.durationProperty }
                    setCellFactory {
                        object : TableCell<TimesheetRowViewModel, LocalTime>() {
                            override fun updateItem(item: LocalTime?, empty: Boolean) {
                                super.updateItem(item, empty)
                                text = if (empty) null else item?.format(DateTimeFormatter.ofPattern("H:mm"))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDock() {
        super.onDock()
        controller.loadProjects()
        controller.selectedDateProperty.set(LocalDate.now())
    }
}
