package hu.zsoki.ts.model

import hu.zsoki.ts.data.*
import hu.zsoki.ts.data.ProjectsTable
import hu.zsoki.ts.data.TasksTable
import hu.zsoki.ts.domain.LoggedHour
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalTime

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

    fun getLoggedHoursOrEmpty(selectedDate: LocalDate): List<LoggedHour> {
        return transaction {
            WorkdayEntity.find { WorkdaysTable.date eq selectedDate }
                .with(WorkdayEntity::loggedHours, LoggedHourEntity::record, RecordEntity::task, TaskEntity::project)
                .firstOrNull()
                ?.loggedHours
                ?.map(LoggedHour::fromEntity)
                ?: listOf()
        }
    }

    fun saveLoggedHour(
        projectName: String?,
        taskName: String?,
        note: String?,
        selectedDate: LocalDate?,
        from: LocalTime?,
        to: LocalTime?
    ): LoggedHour {
        requireNotNull(projectName) { "Project is null, cannot insert logged hour." }
        requireNotNull(taskName) { "Task name is null, cannot insert logged hour." }
        requireNotNull(note) { "Note is null, cannot insert logged hour." }
        requireNotNull(selectedDate) { "Selected date is null, cannot insert logged hour" }

        return transaction {
            val project = ProjectEntity.find { ProjectsTable.projectName eq projectName }.first()
            val task = TaskEntity.find { TasksTable.taskName eq taskName }.first()

            val record = RecordEntity.find { RecordsTable.note eq note }
                .with(RecordEntity::task, TaskEntity::project)
                .firstOrNull { it.note == note && it.task == task && it.task.project == project }
                ?: RecordEntity.new {
                    this.task = task
                    this.note = note
                }

            val workDay = WorkdayEntity.find { WorkdaysTable.date eq selectedDate }
                .firstOrNull()
                ?: WorkdayEntity.new {
                    this.date = selectedDate
                }

            LoggedHourEntity.new {
                this.from = selectedDate.atTime(from)
                this.to = selectedDate.atTime(to)
                this.record = record
                this.workDay = workDay
            }.let(LoggedHour::fromEntity)
        }
    }

}
