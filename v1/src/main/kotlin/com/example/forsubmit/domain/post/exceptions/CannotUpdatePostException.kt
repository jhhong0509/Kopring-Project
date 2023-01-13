package com.example.forsubmit.domain.post.exceptions

import com.example.forsubmit.global.exception.GlobalException

class CannotUpdatePostException private constructor() : GlobalException(PostErrorCode.CANNOT_UPDATE_POST) {
    companion object {
        @JvmField
        val EXCEPTION = CannotUpdatePostException()
    }
}