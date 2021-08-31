package hu.zsoki.ts.ui.timesheet

import javafx.collections.FXCollections
import javafx.scene.control.cell.ComboBoxTableCell
import javafx.scene.layout.Priority
import tornadofx.*
import java.time.LocalTime

class TimesheetView : View("Timesheet") {
    override val root = vbox(10) {
        paddingAll = 20

        // Test data
        val texasCities = FXCollections.observableArrayList("Austin", "Dallas", "Midland", "San Antonio", "Fort Worth")
        val records = listOf(
            TimesheetRecordVO("Rezsi", "Belso megbeszelesek", "Nautilus daily standup", LocalTime.of(9, 0), LocalTime.of(0, 30)),
            TimesheetRecordVO("Fraud", "Central printing", "OTPFI-569", LocalTime.of(10, 0), LocalTime.of(6, 0)),
            TimesheetRecordVO("CSX", "Fejlesztes", "IO-manager", LocalTime.of(16, 0), LocalTime.of(1, 0))
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
                combobox(values = texasCities) {
                    useMaxWidth = true
                    gridpaneConstraints {
                        marginRight = 10.0
                        hGrow = Priority.ALWAYS
                    }
                }
                combobox(values = texasCities) {
                    useMaxWidth = true
                    gridpaneConstraints {
                        marginRight = 10.0
                        hGrow = Priority.ALWAYS
                    }
                }
                combobox(values = texasCities) {
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
                setCellFactory(ComboBoxTableCell.forTableColumn(texasCities))
            }
            column("Task", TimesheetRecordVO::taskProperty).makeEditable()
            column("Note", TimesheetRecordVO::noteProperty).makeEditable()
            readonlyColumn("From", TimesheetRecordVO::from)
            readonlyColumn("To", TimesheetRecordVO::to)
            readonlyColumn("Duration", TimesheetRecordVO::duration)
        }
    }
}
