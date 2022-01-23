package com.example.forsubmit.domain.user.exceptions

import com.example.forsubmit.global.exception.GlobalException

class AccountIdAlreadyExistsException private constructor() : GlobalException(UserErrorCode.ACCOUNT_ID_ALREADY_EXISTS) {
    companion object {
        @JvmField
        val EXCEPTION = AccountIdAlreadyExistsException()
    }
}