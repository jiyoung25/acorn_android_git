package com.example.kotlin_test

fun main(){
    var names = listOf("김구라", "해골", "원숭이", "주뎅이", "덩어리")
    for(i in 0..4){
        //val tmp = names[i]
        val tmp = names.get(i)
        println(tmp)
    }
    
    println("------------")
    //java의 확장 for문처럼 item만 순서대로 참조할 수 있다.
    for(item in names){
        println(item)
    }

    println("------------")
    for(i in 0 until names.size){
        val tmp = names[i]
        println(tmp)
    }

    println("------------")
    for(num in 0 .. 10 step 2){
        println(num)
    }

    println("------------")
    for(num in 10 downTo 0){
        println(num)
    }


    println("------------")
    for(num in 10 downTo 0 step 2){
        println(num)
    }


    println("------------")
    //names배열의 아이템을 역순으로 참조하려면?
    for(i in names.size-1 downTo 0){
        val tmp = names[i]
        println(tmp)
    }


    println("------------")
    names.forEach{
        println(it)
    }
}