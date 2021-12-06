package com.example.forsubmit.domain.post.entity.manager

import com.example.forsubmit.domain.post.entity.Post
import com.example.forsubmit.domain.post.entity.PostRepository
import com.example.forsubmit.domain.post.exceptions.PostNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class PostRepositoryManager(
    private val postRepository: PostRepository
) {
    fun save(post: Post): Post {
        return postRepository.save(post)
    }

    fun findById(id: Long): Post {
        return postRepository.findByIdOrNull(id) ?: throw PostNotFoundException.EXCEPTION
    }

}