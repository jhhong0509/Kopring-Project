package com.example.forsubmit.domain.room.entity

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface RoomRepository : CrudRepository<Room, Long> {
    fun findAllBy(pageable: Pageable): Page<Room>
}