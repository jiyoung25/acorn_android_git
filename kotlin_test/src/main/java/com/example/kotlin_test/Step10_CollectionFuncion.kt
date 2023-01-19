package com.example.kotlin_test

//forEach, filter, map 함수를 배운다!
val nums = listOf<Int>(1,2,3,4,5,6,7,8,9,10)
fun main(){
    
    //함수 안에 함수를 정의하여 forEach문 진행
    //목록에 저장된 모든 아이템을 순회하면서 출력
    nums.forEach(fun(item){
        println(item)
    })

    println("----------------")
    //위를 줄이면
    nums.forEach({
        println(it)
    })

    println("----------------")
    
    //더더더더 줄이면 (최종 형태)
    nums.forEach{
        println(it)
    }

    println("----------------")
    
    //목록에 저장된 모든 아이템을 순회하면서 조건에 맞는 아이템만 남긴 새로운 목록 얻어내기
    nums.filter{
        it>5
    }
    println(nums) //여기까진 배열이 전과 똑같음

    println("----------------")
    val result =     nums.filter{
        it>5 //이 조건이 true인 아이템만 남긴 새로운 목록이 만들어진다.
    }
    println(result)
    println("----------------")

    //목록에 저장된 모든 아이템을 순회하면서 어떤 조작을 가한 새로운 목록 얻어내기
    val result2 = nums.map{
        it * 2
    }
    println(result2)
}