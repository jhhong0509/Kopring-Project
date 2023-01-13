package com.example.forsubmit.domain.user.entity

import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class User(
    accountId: String,

    name: String,

    password: String

) : BaseUser(accountId, name) {

    @NotNull
    var password = password
        protected set

}