package com.example.forsubmit.domain.post.entity

import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>, CustomPostRepository {
}