package com.example.forsubmit.domain.user.entity

import javax.persistence.Entity

@Entity
class OAuthUser(
    email: String,

    name: String

) : BaseUser(email, name)