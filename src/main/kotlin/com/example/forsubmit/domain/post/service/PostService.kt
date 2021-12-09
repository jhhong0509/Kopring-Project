package com.example.forsubmit.domain.post.service

import com.example.forsubmit.domain.post.entity.Post
import com.example.forsubmit.domain.post.entity.PostRepository
import com.example.forsubmit.domain.post.entity.manager.PostRepositoryManager
import com.example.forsubmit.domain.post.exceptions.CannotUpdatePostException
import com.example.forsubmit.domain.post.exceptions.PostNotFoundException
import com.example.forsubmit.domain.post.payload.request.CreatePostRequest
import com.example.forsubmit.domain.post.payload.request.UpdatePostRequest
import com.example.forsubmit.domain.post.payload.response.PostContentResponse
import com.example.forsubmit.domain.post.payload.response.PostListResponse
import com.example.forsubmit.domain.post.payload.response.PostResponse
import com.example.forsubmit.domain.user.facade.UserFacade
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PostService(
    private val postRepositoryManager: PostRepositoryManager,
    private val userFacade: UserFacade
) {
    fun savePost(request: CreatePostRequest) {
        val user = userFacade.findCurrentUser()

        val post = Post(
            user = user,
            title = request.title,
            content = request.content
        )

        postRepositoryManager.save(post)
    }

    @Transactional
    fun updatePost(id: Long, request: UpdatePostRequest) {
        val user = userFacade.findCurrentUser()
        val post = postRepositoryManager.findById(id)

        if (post.user != user) {
            throw CannotUpdatePostException.EXCEPTION
        }

        post.update(
            title = request.title,
            content = request.content
        )
    }

    fun deletePost(id: Long) {
        val user = userFacade.findCurrentUser()
        val post = postRepositoryManager.findById(id)

        if (post.user != user) {
            throw CannotUpdatePostException.EXCEPTION
        }

        postRepositoryManager.delete(post)
    }

    fun getSinglePost(id: Long): PostContentResponse {
        val post = postRepositoryManager.findById(id)
        val user = userFacade.findCurrentUser()

        return PostContentResponse(
            content = post.content,
            title = post.title,
            createdAt = post.createdDate ?: LocalDateTime.now(),
            userEmail = user.email,
            userName = user.name
        )
    }

    fun getPostList(pageable: Pageable): PostListResponse {
        val postPage = postRepositoryManager.findAllPagination(pageable)

        val postList = postPage.content
            .map {PostResponse(
                userName = it.user.name,
                userEmail = it.user.email,
                createdAt = it.createdDate,
                title = it.title
            )}
            .toCollection(mutableListOf())

        return PostListResponse(
            responses = postList,
            hasNextPage = postPage.hasNext()
        )

    }

}