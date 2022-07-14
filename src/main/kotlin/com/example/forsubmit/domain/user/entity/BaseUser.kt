package com.example.forsubmit.domain.user.entity

import com.example.forsubmit.domain.post.entity.Post
import org.hibernate.annotations.NaturalId
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
sealed class BaseUser(
    @NaturalId
    val accountId: String,

    name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @NotNull
    var name = name
        protected set

    @OneToMany(mappedBy = "user")
    val posts: List<Post> = mutableListOf()

    fun updateUserName(name: String) {
        this.name = name
    }
}