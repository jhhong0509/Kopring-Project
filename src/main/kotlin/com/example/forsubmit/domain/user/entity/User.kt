package com.example.forsubmit.domain.user.entity

import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class User(
    email: String,

    name: String,

    password: String

) : BaseUser(email, name) {

    @NotNull
    var password = password
        protected set

}