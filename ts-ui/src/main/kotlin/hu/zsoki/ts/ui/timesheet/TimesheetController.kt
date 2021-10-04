package hu.zsoki.ts.ui.timesheet

import hu.zsoki.ts.TimesheetModel
import hu.zsoki.ts.domain.Note
import hu.zsoki.ts.domain.Project
import hu.zsoki.ts.domain.Task
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*

class TimesheetController : Controller() {

    val model = TimesheetModel()

    val projects = FXCollections.observableArrayList<String>()
    val selectedProjectProperty = SimpleStringProperty().apply { onChange { selectedProject -> loadTasks(selectedProject) } }

    val tasks = FXCollections.observableArrayList<String>()
    val selectedTaskProperty = SimpleStringProperty().apply { onChange { selectedTask -> loadNotes(selectedTask) } }

    val notes = FXCollections.observableArrayList<String>()
    val selectedNoteProperty = SimpleStringProperty()

    fun loadProjects() {
        projects.clear()
        // TODO async
        projects.addAll(model.getProjects().map(Project::name))
        if (projects.isNotEmpty()) selectedProjectProperty.set(projects[0])
    }

    private fun loadTasks(selectedProject: String?) {
        tasks.clear()
        if (!selectedProject.isNullOrBlank()) {
            // TODO async
            tasks.addAll(model.getTasks(selectedProject).map(Task::name))
            if (tasks.isNotEmpty()) selectedTaskProperty.set(tasks[0])
        }
    }

    private fun loadNotes(selectedTask: String?) {
        notes.clear()
        if (!selectedTask.isNullOrBlank()) {
            // TODO async
            notes.addAll(model.getNotes(selectedTask).map(Note::content))
            if (notes.isNotEmpty()) selectedNoteProperty.set(notes[0])
        }
    }
}
