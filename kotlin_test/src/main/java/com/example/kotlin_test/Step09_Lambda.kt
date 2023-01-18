package com.example.kotlin_test

/*
    Lambda expression(람다 표현식)
    - 익명 함수를 람다라고 한다.
 */

fun main(){
    //f1이라는 이름의 함수 만들기
    fun f1(){
        println("f1 함수 호출됨")
    }

    //만든 f1() 함수 호출하기
    f1()

    //마치 자바스크립트처럼 함수 안에 함수를 만들고 호출하는 형태가 가능하다.
    
    //이름이 없는 함수를 만들어서 바로 호출 가능
    //함수를 만들어서 바로 호출()하는 형태
    (fun(){
        println("익명 함수가 호출됨")
    })() 
    
    //이름이 없는 함수를 만들어서 변수에 담기
    val f2 = fun(){
        println("f2함수가 호출됨!")
    }

    f2()
}