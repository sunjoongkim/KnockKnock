package com.example.myapplication.relboard


data class RelModel(
    val title: String = "",
    val content: String = "",
    val uid: String = "",
    val time: String = ""
) {
    constructor() : this("", "", "", "")
}