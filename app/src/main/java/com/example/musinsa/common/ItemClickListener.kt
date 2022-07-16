package com.example.musinsa.common

interface ItemClickListener {

    fun moveToWeb(link: String)

    fun setNextGridPage(position: Int)

    fun shuffleData(position: Int)

    fun setNextStylePage(position: Int)
}