package gay.asoji.themonitorbehindme.data

import kotlinx.serialization.Serializable

@Serializable
data class TheMonitorBehindMeBotConfig(
    val discord: DiscordConfig
) {
    @Serializable
    data class DiscordConfig(
        val botToken: String,
    )
}