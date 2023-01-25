package com.example.kotlin_test

fun main(){
    val isRun = true
    if(isRun){
        "달려요"
    } else{
        "달리지 안하요"
    }

    //아래와 같은 try-catch문이 가능하다.
    var str = "1000"
    var str2 = "천"

    var result2 = try{
        //예외가 발생하지 않은 경우 대입될 예정인 값
        Integer.parseInt(str2)
    } catch(nfe:java.lang.NumberFormatException){
        //예외가 발생하는 경우 대입될 값
        0
    }
    println("result2:${result2}")
}