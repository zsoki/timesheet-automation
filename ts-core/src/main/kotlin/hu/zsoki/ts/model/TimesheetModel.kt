package hu.zsoki.ts.model

import hu.zsoki.ts.data.*
import hu.zsoki.ts.data.ProjectsTable
import hu.zsoki.ts.data.TasksTable
import org.jetbrains.exposed.sql.transactions.transaction

class TimesheetModel {

    fun getProjects(): List<String> {
        return transaction { ProjectEntity.all().map(ProjectEntity::projectName).toList() }
    }

    fun getTasks(projectName: String): List<String> {
        return transaction {
            ProjectEntity.find { ProjectsTable.projectName eq projectName }.first().tasks.map { it.taskName }
        }
    }

    fun getNotes(taskName: String): List<String> {
        return transaction {
            TaskEntity.find { TasksTable.taskName eq taskName }.first().records.map { it.note }
        }
    }

}
