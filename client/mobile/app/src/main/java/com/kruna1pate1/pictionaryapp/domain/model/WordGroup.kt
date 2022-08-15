package com.kruna1pate1.pictionaryapp.domain.model

data class WordGroup (
    val wordArray: List<String>,
    val selectedWord: String = "",
    val hint: String = ""
)
