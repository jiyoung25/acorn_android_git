package com.example.step03_extends

fun test(a:Int, b:Boolean) : String{
    return "10"
}

fun main() {

    var test:String?=null
    test = "ㄴㄴ"
    println(test)
    test = null
    println(test)

    var test2:String = "aa"
    println(test2)
    test2= null.toString()
    println(test2)

}

class Person constructor(num:Int, title:String, content:String){
    var num:Int
    var title:String
    var content:String

    init{
        this.num=num
        this.title="title"
        this.content="content"
    }
}

data class Post(var num:Int, var title:String, var content:String)