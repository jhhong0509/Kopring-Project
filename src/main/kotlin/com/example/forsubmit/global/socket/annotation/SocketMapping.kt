package com.example.forsubmit.global.socket.annotation

import kotlin.reflect.KClass

@Target(allowedTargets = [AnnotationTarget.FUNCTION])
@Retention(AnnotationRetention.RUNTIME)
annotation class SocketMapping(
    val endPoint: String,
    val requestClass: KClass<*>
)
