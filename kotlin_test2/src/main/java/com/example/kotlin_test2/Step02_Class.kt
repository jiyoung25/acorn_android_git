package com.example.kotlin_test2

//클래스 정의하기
class MyCar

class YourCar{
    //멤버 함수
    fun drive(){
        println("달려요!")
    }
}

class AirPlane constructor(){
    //생성자?
    init{
        println("AirPlane 클래스의 init!")
    }
}

//Constructor 예약어 생략 가능
class AirPlane2 constructor(){
    //생성자?
    init{
        println("AirPlane2 클래스의 init!")
    }
}

//인자로 전달받을 것이 없으면 ()괄호는 생략이 가능하다.
class AirPlane3 constructor(){
    //생성자?
    init{
        println("AirPlane3 클래스의 init!")
    }
}

fun  main(){
    //클래스를 이용해서 객체를 생성 (( 자바에서는 -> new MyCar))
    var c1 = MyCar() // new 예약어 필요 없음
    YourCar().drive();

    AirPlane()
}
