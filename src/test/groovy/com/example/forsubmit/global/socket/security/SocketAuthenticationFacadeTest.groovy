package com.example.forsubmit.global.socket.security

import com.corundumstudio.socketio.SocketIOClient
import com.example.forsubmit.domain.user.entity.User
import spock.lang.Specification

class SocketAuthenticationFacadeTest extends Specification {

    def socketIOClient = GroovyMock(SocketIOClient)
    def socketAuthenticationFacade = new SocketAuthenticationFacadeImpl()

    def "SocketAuthenticationFacade Test"() {
        given:
        def user = new User("accountId", "name", "password")
        1 * socketIOClient.get(SecurityProperties.USER_KEY) >> user

        when:
        def userFromSocket = socketAuthenticationFacade.getUser(socketIOClient)

        then:
        user == userFromSocket
    }
}
