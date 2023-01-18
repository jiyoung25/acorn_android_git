package com.example.kotlin_test2

/*
    함수명: sum
    함수에 전달하는 인자의 갯수: 2개
    인자의 type: Int
    함수의 리턴 type: Int
 */
fun sum(a:Int, b:Int):Int{
    return a+b
}

//run했을 때 실행의 흐름이 시작되는 함수(main method)
fun main(){
    println(sum(10, 20))
    //변수를 만들 때 사용하는 예약어 val vs var
    val a=10 //val은 readonly(읽기 전용, 값 수정 불가)
    var b=10 //var은 read, write(값 수정 가능)
    var aaas:String="10"
    var aaa=10

    //선언만 하고 값을 나중에 넣고 싶으면
    val c:Int
    c=20 //val은 값이 한번 결정되면 수정이 불가하다 final예약어와 같은 역할이다.

    //var로 변수를 만들면
    var d:Int
    //언제든지 수정이 가능하다
    d=20
    d=30

    //값이 대입되면서 String type으로 myName이 만들어진다.
    val myName="김구라"
    //String type으로 선언된 yourName에 값 대입하기
    val yourName:String = "해골"
    //String type ourName으로 변수를 만들고
    val ourName:String
    //값을 나중에 대입하기
    ourName = "원숭이"
}


