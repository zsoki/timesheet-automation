package hu.zsoki.ts.data

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.`java-time`.date
import org.jetbrains.exposed.sql.`java-time`.datetime

internal object ProjectsTable : IntIdTable() {
    val projectName = varchar("project_name", 128)
}

internal object TasksTable : IntIdTable() {
    val taskName = varchar("task_name", 128)
    val project = reference("project_id", ProjectsTable, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
}

internal object RecordsTable : IntIdTable () {
    val note = varchar("note", 256).default("")
    val task = reference("task_id", TasksTable, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)
}

internal object WorkdaysTable : IntIdTable() {
    val date = date("date")
}

internal object LoggedHoursTable : IntIdTable() {
    val record = reference("record_id", RecordsTable, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)
    val workDay = reference("workday_id", WorkdaysTable, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val from = datetime("from")
    val to = datetime("to")
    val submitted = bool("submitted").default(false)
}