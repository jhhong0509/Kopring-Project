package com.example.forsubmit.domain.chatroom.entity

import com.example.forsubmit.JpaConfig
import com.example.forsubmit.domain.chatroom.entity.chatroom.ChatRoom
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import spock.lang.Specification

@Import(JpaConfig)
@DataJpaTest
class RoomTest extends Specification {

    @Autowired
    private TestEntityManager entityManager

    def "Save Success Test"() {
        given:
        def unsavedRoom = new ChatRoom(0, "name", new ArrayList(), new ArrayList())

        when:
        def room = entityManager.persist(unsavedRoom)

        then:
        room.id != 0
        room.name != null
        room.chatRoomMember.isEmpty()
        room.chat.isEmpty()
    }
}
