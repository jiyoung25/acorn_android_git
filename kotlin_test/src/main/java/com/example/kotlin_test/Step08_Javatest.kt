package com.example.kotlin_test

import com.example.kotlin_test.java.Member
import com.example.kotlin_test.java.MemberDto


fun main(){
    //코틀린에서 java class도 자유롭게 import해서 사용할 수 있다.
    val mem1 = Member()
    mem1.num=1
    mem1.name="김구라"
    mem1.addr="노량진"
    mem1.showInfo()

    val mem2 = MemberDto()
    //내부적으로 java의 setter메소드가 호출된다.
    mem2.num = 2
    mem2.name = "해골"
    mem2.addr = "행신동"

    //내부적으로 java의 getter메소드가 호출된다.
    val a = mem1.num
    val b = mem1.name
    val c = mem1.addr
}