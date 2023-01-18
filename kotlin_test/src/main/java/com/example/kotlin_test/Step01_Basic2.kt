package com.example.kotlin_test

fun main(){
    var myName="김구라"
    var yourName="해골"
    //연결 연산자
    var result = "내 이름:"+myName
    var result2="너의 이름:"+yourName

    //javascript backtick을 이용해서 문자열을 만들 때 사용했던 ${} 표현식이 가능하다
    var result3="내 이름:${myName}"
    var result4="너의 이름:${yourName}"

    //읽기 전용 배열
    val names = listOf<String>("kim", "lee", "park") //배열에 초기값을 담기. 배열의 함수 명은 listOf이다.
    println(names[0]) //배열 형태로 사용 가능
    println(names[1])
    println(names.get(2)) //함수 형태로도 사용 가능
    //names[0]="xxx" //수정이 불가능한 배열이다.

    println("------")

    //반복문
    //names.indices는 names의 [0,1,2](방번호)를 가리킨다.
    //그러므로 i는 방 번호의 index가 된다.
    for(i in names.indices){
        println("${i}번 째 item:${names[i]}")
    }

    println("------")

    //확장포문 같은 것
    for(item in names){
        println(item)
    }

    println("------")

    //for문을 javascript처럼 사용
    names.forEach(){
        println(it)
    }

    print("--------")

}