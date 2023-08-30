package com.example.hellokotlin.blog.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class WordCount(
    @Id val word: String,
    var cnt: Int = 0
)