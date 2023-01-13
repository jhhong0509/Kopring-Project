package com.example.forsubmit.domain.post.entity

import com.example.forsubmit.domain.user.entity.BaseUser
import com.example.forsubmit.domain.user.entity.User
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Post(
    title: String,

    content: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: BaseUser
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @NotNull
    var title = title
        protected set

    @NotNull
    var content = content
        protected set

    @CreatedDate
    @NotNull
    var createdDate: LocalDateTime? = null
        protected set

    fun update(title: String, content: String) {
        this.title = title
        this.content = content
    }
}