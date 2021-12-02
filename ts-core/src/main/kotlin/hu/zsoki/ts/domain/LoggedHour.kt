package hu.zsoki.ts.domain

import hu.zsoki.ts.data.LoggedHourEntity
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

data class LoggedHour(val projectName: String, val taskName: String, val note: String, val from: LocalDateTime, val to: LocalDateTime) {
    companion object {
        fun fromEntity(entity: LoggedHourEntity): LoggedHour {
            return transaction { LoggedHour(entity.record.task.project.projectName, entity.record.task.taskName, entity.record.note, entity.from, entity.to) }
        }
    }
}
