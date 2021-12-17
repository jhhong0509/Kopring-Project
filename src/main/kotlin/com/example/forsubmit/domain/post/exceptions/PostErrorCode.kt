package com.example.forsubmit.domain.post.exceptions

import com.example.forsubmit.global.exception.property.ExceptionProperty

enum class PostErrorCode(
    override val status: Int,
    override val errorMessage: String,
    override val koreanMessage: String
) : ExceptionProperty {
    POST_NOT_FOUND(404, "Post Not Found", "게시글을 찾을 수 없습니다."),
    CANNOT_DELETE_POST(403, "Invalid Access To Remove Post", "게시글을 삭제할 권한이 없습니다."),
    CANNOT_UPDATE_POST(403, "Invalid Access To Update Post", "게시글을 수정할 권한이 없습니다.")
}