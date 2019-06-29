package com.example.groupchat

data class Message (
    val text: String,
    val user: String,
    val createdAt: Long
)