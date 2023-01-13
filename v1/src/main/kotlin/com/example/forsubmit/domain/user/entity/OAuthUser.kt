package com.example.forsubmit.domain.user.entity

import javax.persistence.Entity

@Entity
class OAuthUser(

    accountId: String,

    name: String

) : BaseUser(accountId, name)