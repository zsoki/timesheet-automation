package hu.zsoki.ts

import hu.zsoki.ts.data.*
import hu.zsoki.ts.data.ProjectsTable
import hu.zsoki.ts.data.TasksTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class TimesheetModel {

    fun getProjects(): List<String> {
        return transaction { ProjectEntity.all().map(ProjectEntity::projectName).toList() }
    }

    fun getTasks(projectName: String): List<String> {
        return transaction {
            (ProjectsTable innerJoin TasksTable)
                .slice(ProjectsTable.projectName, TasksTable.taskName)
                .select { ProjectsTable.projectName eq projectName }
                .map { it[TasksTable.taskName] }
        }
    }

    fun getNotes(taskName: String): List<String> {
        return transaction {
            (TasksTable innerJoin RecordsTable)
                .slice(TasksTable.taskName, RecordsTable.note)
                .select { TasksTable.taskName eq taskName }
                .map { it[RecordsTable.note] }
        }
    }

}