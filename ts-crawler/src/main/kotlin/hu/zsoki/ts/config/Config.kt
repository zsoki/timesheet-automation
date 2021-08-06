package hu.zsoki.ts.config

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import com.uchuhimo.konf.source.yaml
import com.uchuhimo.konf.source.yaml.toYaml
import java.io.File

object TsAppConfigSpec : ConfigSpec() {
    val ieDriverPath by optional("")
    val timesheetUrl by optional("")
}

val tsAppConfig = Config { addSpec(TsAppConfigSpec) }
    .from.yaml.file("config.yaml", true)
    .from.env()
    .from.systemProperties()

fun Config.overWriteConfig() {
    val file = File("config.yaml")
    file.createNewFile()
    toYaml.toFile(file)
}
