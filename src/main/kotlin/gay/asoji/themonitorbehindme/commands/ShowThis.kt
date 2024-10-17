package gay.asoji.themonitorbehindme.commands

import com.google.gson.JsonObject
import gay.asoji.themonitorbehindme.isUrl
import gay.asoji.themonitorbehindme.ws
import okhttp3.OkHttpClient
import okhttp3.Request
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi



class ShowThis : Scaffold {
    @OptIn(ExperimentalEncodingApi::class)
    @SlashCommand("showthis", "Show a link content on Aubrey's monitor")
    suspend fun showthis(ctx: SlashContext, toaubrey: String) {
        if (!toaubrey.isUrl()) {
            println("This is not a valid URL")
            return
        }

        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url(toaubrey)
            .get()
            .build()
        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            println("Response failed")
            return
        }
        val body = response.body ?: return
        if (body.contentLength() > 52_428_800) {
            println("Bigger than 50MB, will not attempt to show")
            return
        }
        val contentType = body.contentType()
        if (contentType == null) {
            println("what the spaghetti is this content type, oh you dont exist")
            return
        }
        val bytes = body.bytes()
        val base64 = Base64.encode(bytes)

        val fucker = JsonObject()
        fucker.addProperty("type", contentType.toString())
        fucker.addProperty("content", base64)

        for (connections in ws.connections) {
            connections.send(fucker.toString())
        }

        ctx.interaction.reply("wow it worked").queue()
    }
}