package com.example.forsubmit.domain.room.entity

import com.example.forsubmit.JpaConfig
import com.example.forsubmit.domain.user.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import spock.lang.Specification

import java.time.LocalDateTime

@Import(JpaConfig)
@DataJpaTest
class RoomTest extends Specification {

    @Autowired
    private TestEntityManager entityManager

    def "Save Success Test"() {
        setup:
        def unsaved = new User(0, "email@dsm.hs.kr", "name", "passowrd", new ArrayList(), new ArrayList(), new ArrayList())
        def user = entityManager.persist(unsaved)

        when:
        def unsavedRoom = new Room(0, "title", "content", LocalDateTime.now(), user)
        def room = entityManager.persist(unsavedRoom)

        then:
        room.createdDate != null
        room.id != 0
        room.title != null
        room.content != null
        room.user != null
    }
}
