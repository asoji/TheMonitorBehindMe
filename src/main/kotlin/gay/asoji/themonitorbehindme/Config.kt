package gay.asoji.themonitorbehindme

import com.akuleshov7.ktoml.file.TomlFileReader
import gay.asoji.themonitorbehindme.data.TheMonitorBehindMeBotConfig
import kotlinx.serialization.serializer
import kotlin.system.exitProcess

object Config {
    private val configPath = System.getProperty("themonitorbehindme_config", "config.toml")

    fun loadConfig(): TheMonitorBehindMeBotConfig {
        return try {
            logger.info { "Loading the monitor behind me config..." }
            TomlFileReader.decodeFromFile(serializer(), configPath)
        } catch (e: Exception) {
            logger.error {
                "Oops, An exception happened!"
                e
            }
            exitProcess(1)
        }
    }
}