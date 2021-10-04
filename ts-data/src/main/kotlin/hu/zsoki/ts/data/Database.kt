package hu.zsoki.ts.data

import hu.zsoki.ts.config.TsAppConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

object TsDatabase {
    fun init() {
        Database.connect(url = TsAppConfig.jdbcDatabaseUrl, driver = TsAppConfig.driverClass)
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        transaction {
            SchemaUtils.create(ProjectsTable, TasksTable, RecordsTable, WorkdaysTable, LoggedHoursTable)
        }
    }
}