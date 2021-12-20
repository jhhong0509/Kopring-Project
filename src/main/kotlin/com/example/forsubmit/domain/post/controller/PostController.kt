package com.example.forsubmit.domain.post.controller

import com.example.forsubmit.domain.post.payload.request.CreatePostRequest
import com.example.forsubmit.domain.post.payload.request.UpdatePostRequest
import com.example.forsubmit.domain.post.payload.response.DeletePostResponse
import com.example.forsubmit.domain.post.payload.response.PostContentResponse
import com.example.forsubmit.domain.post.payload.response.PostListResponse
import com.example.forsubmit.domain.post.payload.response.SavePostResponse
import com.example.forsubmit.domain.post.service.PostService
import com.example.forsubmit.global.payload.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post")
class PostController(
    private val postService: PostService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun savePost(@RequestBody @Validated request: CreatePostRequest): BaseResponse<SavePostResponse> {
        return postService.savePost(request)
    }

    @PatchMapping("/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody @Validated request: UpdatePostRequest): BaseResponse<Unit> {
        return postService.updatePost(id, request)
    }

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long): BaseResponse<DeletePostResponse> {
        return postService.deletePost(id)
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): BaseResponse<PostContentResponse> {
        return postService.getSinglePost(id)
    }

    @GetMapping
    fun getPostList(@RequestParam lastId: Long): BaseResponse<PostListResponse> {
        return postService.getPostList(lastId)
    }
}