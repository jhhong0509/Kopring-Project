package com.example.forsubmit.domain.post.exceptions

import com.example.forsubmit.global.exception.GlobalException

class PostNotFoundException private constructor() : GlobalException(PostErrorCode.POST_NOT_FOUND) {
    companion object {
        @JvmField
        val EXCEPTION = PostNotFoundException()
    }
}