package hu.zsoki.ts.ui.timesheet

import javafx.scene.control.TableCell
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimesheetRecordFormattedDateTimeCell : TableCell<TimesheetRecordVO, LocalDateTime>() {
    override fun updateItem(item: LocalDateTime?, empty: Boolean) {
        super.updateItem(item, empty)
        text = if (empty) null else item?.format(DateTimeFormatter.ofPattern("H:mm"))
    }
}
