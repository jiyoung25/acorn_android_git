package com.example.kotlin_test

class StarBucks {
    var location:String?=null
    set(value){
        println("location의 setter method가 호출됨")
        field = value+"지점" //여기서 field는 location을 가리킨다.
    }
    get(){
        if(field == null){
           return "지점명 없음"
        } else {
            return field
        }
    }

}

fun main(){
    val s1 = StarBucks()
    s1.location = "강남"
    val s2 = StarBucks()
    s2.location = "역삼"
    val s3 = StarBucks()

    println(s1)

    println(s1.location)
    println(s2.location)
    println(s3.location)
}