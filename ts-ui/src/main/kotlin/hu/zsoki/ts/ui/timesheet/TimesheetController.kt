package hu.zsoki.ts.ui.timesheet

import hu.zsoki.ts.model.TimesheetModel
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*
import java.time.LocalDate

class TimesheetController : Controller() {

    val model = TimesheetModel()

    val quickFillProperty = SimpleStringProperty()

    val projects = FXCollections.observableArrayList<String>()
    val selectedProjectProperty = SimpleStringProperty().apply { onChange { selectedProject -> loadTasks(selectedProject) } }

    val tasks = FXCollections.observableArrayList<String>()
    val selectedTaskProperty = SimpleStringProperty().apply { onChange { selectedTask -> loadNotes(selectedTask) } }

    val notes = FXCollections.observableArrayList<String>()
    val selectedNoteProperty = SimpleStringProperty()

    val durationProperty = SimpleStringProperty()
    val fromProperty = SimpleStringProperty()
    val toProperty = SimpleStringProperty()

    val selectedDateProperty = SimpleObjectProperty<LocalDate>().apply { onChange { selectedDate -> loadLoggedHours(selectedDate) } }
    val loggedHourRecords = FXCollections.observableArrayList<TimesheetRecordVO>()

    fun loadProjects() {
        projects.clear()
        // TODO async
        projects.addAll(model.getProjects())
        if (projects.isNotEmpty()) selectedProjectProperty.set(projects[0])
    }

    private fun loadTasks(selectedProject: String?) {
        tasks.clear()
        if (!selectedProject.isNullOrBlank()) {
            // TODO async
            tasks.addAll(model.getTasks(selectedProject))
            if (tasks.isNotEmpty()) selectedTaskProperty.set(tasks[0])
        }
    }

    private fun loadNotes(selectedTask: String?) {
        notes.clear()
        if (!selectedTask.isNullOrBlank()) {
            // TODO async
            notes.addAll(model.getNotes(selectedTask))
            if (notes.isNotEmpty()) selectedNoteProperty.set(notes[0])
        }
    }

    private fun loadLoggedHours(selectedDate: LocalDate?) {
        loggedHourRecords.clear()
        if (selectedDate != null) {
            loggedHourRecords.addAll(model.getLoggedHoursOrEmpty(selectedDate).map(TimesheetRecordVO::fromDomain))
        }
    }

    fun fill() {
        TODO("Not yet implemented")
    }

    fun commit() {
        TODO("Not yet implemented")
    }
}
