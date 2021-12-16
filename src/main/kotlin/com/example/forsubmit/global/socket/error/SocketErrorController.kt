package com.example.forsubmit.global.socket.error

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.listener.ExceptionListener
import com.example.forsubmit.global.exception.GlobalException
import com.example.forsubmit.global.exception.exceptions.InternalServerError
import com.example.forsubmit.global.payload.BaseResponse
import com.example.forsubmit.global.socket.property.EventProperties
import io.netty.channel.ChannelHandlerContext
import org.springframework.stereotype.Controller

@Controller
class SocketErrorController : ExceptionListener {
    override fun onEventException(e: Exception, args: MutableList<Any>?, client: SocketIOClient) = doError(e, client)

    override fun onDisconnectException(e: Exception, client: SocketIOClient) = doError(e, client)

    override fun onConnectException(e: Exception, client: SocketIOClient) = doError(e, client)

    override fun onPingException(e: Exception, client: SocketIOClient) = doError(e, client)

    override fun exceptionCaught(ctx: ChannelHandlerContext, e: Throwable?): Boolean = false

    private fun doError(throwable: Throwable, client: SocketIOClient) {
        when (throwable) {
            is GlobalException -> sendErrorMessage(throwable, client)
            else -> sendErrorMessage(InternalServerError.EXCEPTION, client)
        }
    }

    private fun sendErrorMessage(e: GlobalException, client: SocketIOClient) {
        val errorResponse = BaseResponse.of(e)
        client.sendEvent(EventProperties.ERROR, errorResponse)
    }
}