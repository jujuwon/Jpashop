package com.example.hellokotlin.blog.service

import com.example.hellokotlin.blog.dto.BlogDto
import com.example.hellokotlin.blog.entity.WordCount
import com.example.hellokotlin.blog.repository.WordRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class BlogService(
    val wordRepository: WordRepository
) {

    @Value("\${api-key}")
    lateinit var apiKey: String

    fun searchKakao(blogDto: BlogDto): String? {
//        val msgList = mutableListOf<ExceptionMsg>()
//
//        if (blogDto.query.trim().isEmpty()) {
//            msgList.add(ExceptionMsg.EMPTY_QUERY)
//        }
//
//        if (blogDto.sort.trim() !in arrayOf("accuracy", "recency")) {
//            msgList.add(ExceptionMsg.NOT_IN_SORT)
//        }
//
//        when {
//            blogDto.page < 1 -> msgList.add(ExceptionMsg.LESS_THAN_MIN)
//            blogDto.page > 50 -> msgList.add(ExceptionMsg.MORE_THAN_MAX)
//        }
//
//        if (msgList.isNotEmpty()) {
//            val message = msgList.joinToString { it.msg }
//            throw InvalidInputException(message)
//        }

        val webClient = WebClient
            .builder()
            .baseUrl("https://dapi.kakao.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

        val response = webClient
            .get()
            .uri {
                it.path("/v2/search/blog")
                    .queryParam("query", blogDto.query)
                    .queryParam("sort", blogDto.sort)
                    .queryParam("page", blogDto.page)
                    .queryParam("size", blogDto.size)
                    .build()
            }
            .header("Authorization", "KakaoAK $apiKey")
            .retrieve()
            .bodyToMono<String>()

        val result = response.block()

        val lowQuery: String = blogDto.query.lowercase()
        val word: WordCount = wordRepository.findById(lowQuery).orElse(WordCount(lowQuery))
        word.cnt++

        wordRepository.save(word)

        return result
    }

    fun searchWordRank(): List<WordCount> = wordRepository.findTop10ByOrderByCntDesc()
}

private enum class ExceptionMsg(val msg: String) {
    EMPTY_QUERY("query parameter required"),
    NOT_IN_SORT("sort parameter one of accuracy and recency"),
    LESS_THAN_MIN("page is less than min"),
    MORE_THAN_MAX("page is more than max")
}