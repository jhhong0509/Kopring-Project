package com.example.forsubmit.domain.post.entity

import com.example.forsubmit.domain.post.payload.response.PostPageResponse

interface CustomPostRepository {
    fun postPageable(lastId: Long?): PostPageResponse
}