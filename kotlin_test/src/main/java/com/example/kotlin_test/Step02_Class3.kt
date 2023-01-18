package com.example.kotlin_test

class Cat constructor() {
    //init 블럭은 primary생성자의 일부이다.
    init {
        println("야옹이가 생겼어요.")
    }
    //field
    var name:String?=null
    //primary 생성자 외에 추가로 생성자 정의하기
    //:this()는 대표 생산자를 호출하는 표현식이다.
    //다른 생성자에서 반드시 대표 생성자를 호출해야 한다,
    constructor(name:String):this(){
        println("야옹이의 이름은:${name}")
    }


}

fun main(){
    Cat()
    Cat("Tomcat")
    //kotlin에서는 모든 데이터가 참조 데이터 타입이다.
    //물음표 중요!
    var num:Int?=null
    num=1

    var isRun:Boolean?=null //null값을 허용하는 Boolean type
    isRun = true

}