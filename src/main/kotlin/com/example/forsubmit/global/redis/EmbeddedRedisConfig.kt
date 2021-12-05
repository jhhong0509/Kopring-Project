package com.example.forsubmit.global.redis

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.annotation.PreDestroy


@Profile("local", "test")
@Configuration
class EmbeddedRedisConfig(
    private val redisProperty: RedisProperty,
) {
    private var redisServer: RedisServer? = null

    init {
        redisServer = RedisServer(redisProperty.port)
        redisServer?.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer?.stop() //Redis 종료
    }

    /**
     * Embedded Redis가 현재 실행중인지 확인
     */
    private fun isRedisRunning(): Boolean {
        return isRunning(executeGrepProcessCommand(redisProperty.port))
    }

    /**
     * 현재 PC/서버에서 사용가능한 포트 조회
     */
    fun findAvailablePort(): Int {
        for (port in 10000..65535) {
            val process = executeGrepProcessCommand(port)
            if (!isRunning(process)) {
                return port
            }
        }
        throw IllegalArgumentException("Not Found Available port: 10000 ~ 65535")
    }

    /**
     * 해당 port를 사용중인 프로세스 확인하는 sh 실행
     */
    private fun executeGrepProcessCommand(port: Int): Process {
        val command = String.format("netstat -nat | grep LISTEN|grep %d", port)
        val shell = arrayOf("/bin/sh", "-c", command)
        return Runtime.getRuntime().exec(shell)
    }

    /**
     * 해당 Process가 현재 실행중인지 확인
     */
    private fun isRunning(process: Process): Boolean {
        var line: String?
        val pidInfo = StringBuilder()
        BufferedReader(InputStreamReader(process.inputStream)).use { input ->
            while (input.readLine().also {
                    line = it
                } != null) {
                pidInfo.append(line)
            }
        }
        return pidInfo.toString() != ""
    }
}