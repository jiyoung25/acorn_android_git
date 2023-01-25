package com.example.kotlin_test

fun main(){
    var str = "1000"
    var str2 = "천"

    try {
        val result = Integer.parseInt(str)
        println("result+${result}")

        val result2 = Integer.parseInt(str2)
        println("result2: ${result2}")
    } catch(nfe:java.lang.NumberFormatException){
        nfe.printStackTrace()
    }
    print("메인 함수가 정상종료됩니다,")
}