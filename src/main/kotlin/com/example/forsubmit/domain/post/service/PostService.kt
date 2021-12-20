package com.example.forsubmit.domain.post.service

import com.example.forsubmit.domain.post.entity.Post
import com.example.forsubmit.domain.post.entity.PostRepository
import com.example.forsubmit.domain.post.exceptions.CannotUpdatePostException
import com.example.forsubmit.domain.post.exceptions.PostNotFoundException
import com.example.forsubmit.domain.post.payload.request.CreatePostRequest
import com.example.forsubmit.domain.post.payload.request.UpdatePostRequest
import com.example.forsubmit.domain.post.payload.response.*
import com.example.forsubmit.domain.user.facade.UserFacade
import com.example.forsubmit.global.payload.BaseResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userFacade: UserFacade
) {
    companion object {
        private const val SAVE_SUCCESS_MESSAGE = "Post Save Success"
        private const val SAVE_SUCCESS_MESSAGE_KOR = "게시글 저장에 성공했습니다."

        private const val UPDATE_SUCCESS_MESSAGE = "Post Update Success"
        private const val UPDATE_SUCCESS_MESSAGE_KOR = "게시글 수정에 성공했습니다."

        private const val DELETE_SUCCESS_MESSAGE = "Post Delete Success"
        private const val DELETE_SUCCESS_MESSAGE_KOR = "게시글 삭제에 성공했습니다."

        private const val GET_SINGLE_SUCCESS_MESSAGE = "Get A Post Success"
        private const val GET_SINGLE_SUCCESS_MESSAGE_KOR = "하나의 게시글을 가져오는데에 성공했습니다."

        private const val GET_MULTIPLE_SUCCESS_MESSAGE = "Get Posts Success"
        private const val GET_MULTIPLE_SUCCESS_MESSAGE_KOR = "여러개의 게시글을 가져오는데에 성공했습니다."
    }

    fun savePost(request: CreatePostRequest): BaseResponse<SavePostResponse> {
        val user = userFacade.findCurrentUser()

        val post = Post(
            user = user,
            title = request.title,
            content = request.content
        )

        val savedPost = postRepository.save(post)

        val responseContent = SavePostResponse(savedPost.id)

        return BaseResponse(
            status = 201,
            message = SAVE_SUCCESS_MESSAGE,
            koreanMessage = SAVE_SUCCESS_MESSAGE_KOR,
            content = responseContent
        )
    }

    @Transactional
    fun updatePost(id: Long, request: UpdatePostRequest): BaseResponse<Unit> {
        val user = userFacade.findCurrentUser()
        val post = findPostById(id)

        if (post.user != user) {
            throw CannotUpdatePostException.EXCEPTION
        }

        post.update(
            title = request.title,
            content = request.content
        )

        return BaseResponse(
            status = 200,
            message = UPDATE_SUCCESS_MESSAGE,
            koreanMessage = UPDATE_SUCCESS_MESSAGE_KOR,
            content = Unit
        )
    }

    fun deletePost(id: Long): BaseResponse<DeletePostResponse> {
        val user = userFacade.findCurrentUser()
        val post = findPostById(id)

        if (post.user != user) {
            throw CannotUpdatePostException.EXCEPTION
        }

        postRepository.delete(post)

        val responseContent = DeletePostResponse(post.id)

        return BaseResponse(
            status = 200,
            message = DELETE_SUCCESS_MESSAGE,
            koreanMessage = DELETE_SUCCESS_MESSAGE_KOR,
            content = responseContent
        )
    }

    fun getSinglePost(id: Long): BaseResponse<PostContentResponse> {
        val post = findPostById(id)
        val user = userFacade.findCurrentUser()

        val responseContent = PostContentResponse(
            content = post.content,
            title = post.title,
            createdAt = post.createdDate!!,
            userEmail = user.email,
            userName = user.name
        )

        return BaseResponse(
            status = 200,
            message = GET_SINGLE_SUCCESS_MESSAGE,
            koreanMessage = GET_SINGLE_SUCCESS_MESSAGE_KOR,
            content = responseContent
        )
    }

    fun getPostList(lastId: Long): BaseResponse<PostListResponse> {
        val postPage = postRepository.postPageable(lastId)

        val postList = postPage.posts
            .map {
                PostResponse(
                    id = it.id,
                    userName = it.user.name,
                    userEmail = it.user.email,
                    createdAt = it.createdDate,
                    title = it.title
                )
            }
            .toList()

        val responseContent = PostListResponse(
            responses = postList,
            nextId = postPage.nextPostId
        )

        return BaseResponse(
            status = 200,
            message = GET_MULTIPLE_SUCCESS_MESSAGE,
            koreanMessage = GET_MULTIPLE_SUCCESS_MESSAGE_KOR,
            content = responseContent
        )

    }

    private fun findPostById(id: Long): Post {
        return postRepository.findByIdOrNull(id) ?: throw PostNotFoundException.EXCEPTION
    }

}