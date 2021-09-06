package hu.zsoki.ts.config

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import com.uchuhimo.konf.source.yaml
import com.uchuhimo.konf.source.yaml.toYaml
import java.io.File

object TsAppConfig {

    private object TsAppConfigSpec : ConfigSpec() {
        val IE_DRIVER_PATH by optional("")
        val TIMESHEET_URL by optional("")
    }

    private val konfObject = Config { addSpec(TsAppConfigSpec) }
        .from.yaml.file("config.yaml", true)
        .from.env()
        .from.systemProperties()

    fun overWriteConfig() {
        konfObject.overWriteConfig()
    }

    var ieDriverPath by konfObject.property(TsAppConfigSpec.IE_DRIVER_PATH)
    var timesheetUrl by konfObject.property(TsAppConfigSpec.TIMESHEET_URL)

}

private fun Config.overWriteConfig() {
    val file = File("config.yaml")
    file.createNewFile()
    toYaml.toFile(file)
}
