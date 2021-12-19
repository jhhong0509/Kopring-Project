package com.example.forsubmit.domain.user.entity

import com.example.forsubmit.domain.post.entity.Post
import org.hibernate.annotations.NaturalId
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class User(
    @NaturalId
    val email: String,

    name: String,

    password: String

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @NotNull
    var name = name
        protected set

    @NotNull
    var password = password
        protected set

    @OneToMany(mappedBy = "user")
    val posts: List<Post> = mutableListOf()

    fun updateUser(name: String, password: String) {
        this.name = name
        this.password = password
    }
}