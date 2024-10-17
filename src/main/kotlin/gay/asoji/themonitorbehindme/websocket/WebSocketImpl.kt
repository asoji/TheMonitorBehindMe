package gay.asoji.themonitorbehindme.websocket

import org.java_websocket.WebSocket
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.handshake.ServerHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Exception
import java.net.InetSocketAddress
import java.net.URI

class WebSocketImpl : WebSocketServer(InetSocketAddress("localhost", 12456)) {
    override fun onOpen(p0: WebSocket?, p1: ClientHandshake?) {

    }

    override fun onClose(p0: WebSocket?, p1: Int, p2: String?, p3: Boolean) {

    }

    override fun onMessage(p0: WebSocket?, p1: String?) {

    }

    override fun onError(p0: WebSocket?, p1: Exception?) {

    }

    override fun onStart() {

    }


}