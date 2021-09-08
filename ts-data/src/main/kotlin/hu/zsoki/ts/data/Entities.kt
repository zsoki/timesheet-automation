package hu.zsoki.ts.data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

internal class ProjectEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProjectEntity>(ProjectsTable)
    var projectName by ProjectsTable.projectName
    val tasks by TaskEntity referrersOn TasksTable.project
}

internal class TaskEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TaskEntity>(TasksTable)
    var taskName by TasksTable.taskName
    var project by ProjectEntity referencedOn TasksTable.project
    val records by RecordEntity referrersOn RecordsTable.task
}

internal class RecordEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecordEntity>(RecordsTable)
    var note by RecordsTable.note
    var task by TaskEntity referencedOn RecordsTable.task
}

internal class WorkdayEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WorkdayEntity>(WorkdaysTable)
    var date by WorkdaysTable.date
    val loggedHours by LoggedHourEntity referrersOn LoggedHoursTable.workDay
}

internal class LoggedHourEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LoggedHourEntity>(LoggedHoursTable)
    var from by LoggedHoursTable.from
    var to by LoggedHoursTable.to
    var submitted by LoggedHoursTable.submitted
    var workDay by WorkdayEntity referencedOn LoggedHoursTable.workDay
    var record by RecordEntity referencedOn LoggedHoursTable.record
}
