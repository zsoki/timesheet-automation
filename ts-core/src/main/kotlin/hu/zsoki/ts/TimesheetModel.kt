package hu.zsoki.ts

import hu.zsoki.ts.domain.Note
import hu.zsoki.ts.domain.Project
import hu.zsoki.ts.domain.Task

class TimesheetModel {

    fun getProjects(): List<Project> {
        // TODO get from DB
        return listOf(Project("Projekt 1"), Project("Projekt 2"), Project("Onboarding"))
    }

    fun getTasks(projectName: String): List<Task> {
        //TODO get from DB
        return when (projectName) {
            "Projekt 1" -> listOf(Task("Task 1"), Task("Task 2"))
            "Projekt 2" -> listOf(Task("Task 3"), Task("Task 4"), Task("Task 5"))
            "Onboarding" -> listOf(Task("Documentation"), Task("Environment setup"), Task("Training"))
            else -> throw error("Unknown project!")
        }
    }

    fun getNotes(taskName: String): List<Note> {
        // TODO get from DB
        return when (taskName) {
            "Documentation" -> listOf(Note("Creating onboarding doc"), Note("Creating definition of done"))
            "Environment setup" -> listOf(Note("Docker setup"), Note("IDE Setup"), Note("Install scripts"))
            "Training" -> listOf(Note("Spring in action"), Note("The Clean Coder"))
            else -> listOf()
        }
    }

}