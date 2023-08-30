package com.example.hellokotlin.blog.repository

import com.example.hellokotlin.blog.entity.WordCount
import org.springframework.data.repository.CrudRepository

interface WordRepository : CrudRepository<WordCount, String> {
    fun findTop10ByOrderByCntDesc() : List<WordCount>
}