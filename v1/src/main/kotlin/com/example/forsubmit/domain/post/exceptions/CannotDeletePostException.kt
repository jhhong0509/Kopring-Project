package com.example.forsubmit.domain.post.exceptions

import com.example.forsubmit.global.exception.GlobalException

class CannotDeletePostException private constructor() : GlobalException(PostErrorCode.CANNOT_DELETE_POST) {
    companion object {
        @JvmField
        val EXCEPTION = CannotDeletePostException()
    }
}