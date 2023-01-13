package com.example.forsubmit.domain.post.payload.response

import com.example.forsubmit.domain.post.entity.Post

class PostPageResponse(
    val posts: List<Post>,
    val nextPostId: Long?
)