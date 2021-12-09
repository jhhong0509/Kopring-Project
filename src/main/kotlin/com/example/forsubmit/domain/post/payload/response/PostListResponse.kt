package com.example.forsubmit.domain.post.payload.response

class PostListResponse(
    val responses: List<PostResponse>,
    val hasNextPage: Boolean
)