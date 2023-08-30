package com.example.hellokotlin.blog.controller

import com.example.hellokotlin.blog.dto.BlogDto
import com.example.hellokotlin.blog.entity.WordCount
import com.example.hellokotlin.blog.service.BlogService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/blog")
class BlogController(
    val blogService: BlogService
) {
    @GetMapping
    fun search(@RequestBody @Valid blogDto: BlogDto): String? {
        return blogService.searchKakao(blogDto)
    }

    @GetMapping("/rank")
    fun searchWordRank(): List<WordCount> = blogService.searchWordRank()
}