package hu.zsoki.ts.ui.timesheet

import hu.zsoki.ts.domain.LoggedHour
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class TimesheetRowViewModel(project: String, task: String, note: String, from: LocalDateTime, to: LocalDateTime) {
    val projectProperty = stringProperty(project)
    var project by projectProperty

    val taskProperty = stringProperty(task)
    var task by taskProperty

    val noteProperty = stringProperty(note)
    var note by noteProperty

    val fromProperty = SimpleObjectProperty(from)
    val from by fromProperty

    val toProperty = SimpleObjectProperty(to)
    val to by toProperty

    val durationProperty = SimpleObjectProperty(LocalTime.ofSecondOfDay(ChronoUnit.SECONDS.between(from, to)))
    val duration by durationProperty

    companion object {
        fun fromDomain(domainObject: LoggedHour): TimesheetRowViewModel {
            return TimesheetRowViewModel(domainObject.projectName, domainObject.taskName, domainObject.note, domainObject.from, domainObject.to)
        }
    }
}