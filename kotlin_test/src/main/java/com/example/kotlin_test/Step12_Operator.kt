package com.example.kotlin_test

/*
    - 비교 연산자
     ==와 ===의 구분
     `==`는 값이 같은지 비교하는 것
     `===`는 참조값이 같은지 비교하는 것
     
     `!=`는 값이 다른지 비교
     `!==`는 참조값이 다른지 비교

     - 삼항 연산자가 없다.
       대신 대체할 수 있는 문법이 있다.
       
     - Elvis 연산자
      `?:`
 */
fun main(){
    /*
        names와 names2는 참조값은 다르지만 안에 저장된 값은 같다.
        list같은 경우 순서가 중요한 데이터이기 때문에 순서도 같아야 ==이 true로 나온다.
        ex) names2가 "kim", "park", "lee"라면 names==names2는 false가 나온다.
     */
    val names = listOf("kim", "lee", "park")
    val names2 = listOf("kim", "lee", "park")

    println("names === names2: ${names === names2}")
    println("names == names2: ${names == names2}")


    //String type은 java처럼 참조값이 같다. 이유도 자바와 같음
    val a="kim"
    val b="kim"

    println("a === b: ${a === b}")
    println("a == b: ${a == b}")

    val isRun = true
    //var result = isRun? "달려요":"달리지 않아요."
    //코틀린엔 삼항연산자가 없이 때문에 위는 작동하지 않는다.
    var result = if(isRun)"달려요" else "달리지 않아요."
    //대신 위처럼 할 수 있다.

    var result2 = if(isRun){
        println("어쩌구")
        println("저쩌구 ...")
        "이걸 남기자 ... 달려요"
    } else {
        "달리지 않아요."
    }

    //null이 가능한 변수가 있다고 가정하자
    var myName:String?=null
    if(myName == null){
        myName = "기본 이름"
        println("이름:" +myName)
    } else{
        "다 이름"
    }

    //`?:` 연산자의 좌측에 있는 값이 null이면 : 연산자의 우측에 있는 값이 남는다.
    //이 값을 알 수
    var result3 = myName ?:"기본이름"
    println("이름: ${myName ?: "기본이름"}")

}