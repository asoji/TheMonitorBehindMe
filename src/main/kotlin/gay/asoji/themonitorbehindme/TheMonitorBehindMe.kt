package gay.asoji.themonitorbehindme

import dev.minn.jda.ktx.events.listener
import dev.minn.jda.ktx.jdabuilder.default
import dev.minn.jda.ktx.jdabuilder.intents
import gay.asoji.themonitorbehindme.websocket.WebSocketImpl
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.events.session.ShutdownEvent
import net.dv8tion.jda.api.requests.GatewayIntent
import xyz.artrinix.aviation.Aviation
import xyz.artrinix.aviation.AviationBuilder
import xyz.artrinix.aviation.events.AviationExceptionEvent
import xyz.artrinix.aviation.events.CommandFailedEvent
import xyz.artrinix.aviation.internal.utils.on
import xyz.artrinix.aviation.ratelimit.DefaultRateLimitStrategy

val logger = KotlinLogging.logger { }
val config = Config.loadConfig()
lateinit var jda: JDA
lateinit var aviation: Aviation
val ws = WebSocketImpl()

suspend fun main(args: Array<String>): Unit = runBlocking {
    logger.info { "Starting The Monitor Behind Me" }

    jda = default(config.discord.botToken, enableCoroutines = true) {
        intents += listOf(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
    }

    aviation = AviationBuilder().apply {
            ratelimitProvider = DefaultRateLimitStrategy()
            doTyping = true
            registerDefaultParsers()
        }.build().apply {
            slashCommands.register("gay.asoji.themonitorbehindme.commands")
        }

    listenAviationEvents()

    jda.addEventListener(aviation)

    jda.listener<ReadyEvent> {
        ws.start()
        aviation.syncCommands(jda)
        logger.info { "The Monitor Behind Me sharted!" }
    }

    jda.listener<MessageReceivedEvent> { event ->
        if (event.author.isBot || !event.isFromGuild) return@listener
    }

    jda.listener<ShutdownEvent> {
        logger.info { "Shutting down The Monitor Behind Me..." }
    }
}

private fun listenAviationEvents() {
    aviation.on<AviationExceptionEvent> {
        logger.error { "Oopsies. Aviation threw an error: ${this.error}" }
    }

    aviation.on<CommandFailedEvent> {
        logger.error { "[Command Execution] A command has failed: ${this.error}" }
    }
}
