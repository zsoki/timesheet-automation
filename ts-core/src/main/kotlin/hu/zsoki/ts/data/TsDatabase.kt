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

            val initialized = ProjectEntity.all().count() > 0

            if (!initialized) {
                (1..5).forEach { projectIndex ->
                    val exampleProject = ProjectEntity.new {
                        projectName = "Project $projectIndex"
                    }
                    (1..5).forEach { taskIndex ->
                        val exampleTask = TaskEntity.new {
                            taskName = "Project $projectIndex Task $taskIndex"
                            project = exampleProject
                        }
                        (1..5).forEach { recordIndex ->
                            RecordEntity.new {
                                note = "Project $projectIndex Task $taskIndex Note $recordIndex"
                                task = exampleTask
                            }
                        }
                    }
                }
            }
        }
    }
}