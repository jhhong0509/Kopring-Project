package com.example.forsubmit.domain.post.entity

import com.example.forsubmit.domain.post.entity.QPost.post
import com.example.forsubmit.domain.post.payload.response.PostPageResponse
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class CustomPostRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CustomPostRepository {

    companion object {
        private const val PAGE_LIMIT = 10
    }

    override fun postPageable(lastId: Long?): PostPageResponse {

        val result = jpaQueryFactory
            .select(post)
            .from(post)
            .where(lastIdQuery(lastId))
            .orderBy(post.id.desc())
            .limit((PAGE_LIMIT + 1).toLong())
            .fetch()

        var nextId: Long? = null

        if (result.lastIndex == PAGE_LIMIT + 1) {
            nextId = result.removeLast().id
        }


        return PostPageResponse(
            result,
            nextId
        )
    }

    private fun lastIdQuery(lastId: Long?): BooleanExpression? {
        return if (lastId == null) {
            null
        } else post.id.lt(lastId)
    }

}