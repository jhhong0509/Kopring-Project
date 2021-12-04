package com.example.forsubmit.global.socket

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.example.forsubmit.global.socket.annotation.SocketController
import com.example.forsubmit.global.socket.annotation.SocketMapping
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.getBean
import org.springframework.beans.factory.getBeansWithAnnotation
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions


@Component
class SocketIOAddHandlerSupporter(
    private val beanFactory: ConfigurableListableBeanFactory
) {
    fun addListeners(socketIOServer: SocketIOServer) {
        val classes = beanFactory.getBeansWithAnnotation<SocketController>().values
            .map { it::class }
            .toList()

        for (cls in classes) {
            val methods: List<KFunction<*>> = findSocketMappingAnnotatedMethods(cls)
            addSocketServerEventListener(cls, methods, socketIOServer)
        }
    }

    private fun addSocketServerEventListener(
        controller: KClass<*>,
        functions: List<KFunction<*>>,
        socketIOServer: SocketIOServer
    ) {
        for (function in functions) {
            val socketMapping = function.findAnnotation<SocketMapping>()
            socketMapping?.let {
                val endpoint: String = socketMapping.endPoint
                val dtoClass: KClass<*> = socketMapping.requestClass
                socketIOServer.addEventListener(endpoint, dtoClass::class.java) { client: SocketIOClient, data, _ ->
                    val args = mutableListOf<Any>()
                    for (params in function.parameters) {                        // Controller 메소드의 파라미터들
                        when (params) {
                            is SocketIOServer -> args.add(socketIOServer)        // SocketIOServer 면 주입
                            is SocketIOClient -> args.add(client)
                            else -> args.add(data)
                        }
                    }
                    function.call(beanFactory.getBean(controller), args.toTypedArray())
                }
            }
        }
    }

    private fun findSocketMappingAnnotatedMethods(cls: KClass<*>): List<KFunction<*>> {
        return cls.functions
            .filter { it.findAnnotation<SocketMapping>() != null }
            .toCollection(mutableListOf())
    }
}
