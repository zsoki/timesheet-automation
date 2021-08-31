package hu.zsoki.ts.ui.timesheet

import tornadofx.*
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class TimesheetRecordVO(project: String, task: String, note: String, val from: LocalTime, val duration: LocalTime) {
    val projectProperty = stringProperty(project)
    var project by projectProperty

    val taskProperty = stringProperty(task)
    var task by taskProperty

    val noteProperty = stringProperty(note)
    var note by noteProperty

    val to: LocalTime = from.plus(duration.minute.toLong(), ChronoUnit.MINUTES)
}