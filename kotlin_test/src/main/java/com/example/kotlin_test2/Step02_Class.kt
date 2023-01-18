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


/*
    class Person{
        public String name;
        public Person(String name){
            this.name=name;
        }
    }
    위의 자바 코드를 코틀린으로 바꾸면 아래가 된다.
 */
class Person constructor(name:String){ // 클래스명 옆에 선언하는 생성자를 primary 생성자 (대표 생성자)라고 한다.
    //필드 생성
    var name:String

    init{
        this.name="name"
    }
}

//위의 클래스를 조금 간단하게 선언하면 아래와 같다.
class Person2(var name:String) //var또는 val을 생성자의 인자에 선언하면 전달받은 값이 자동으로 같은 이름의 필드가 만들어지고 값이 들어간다.

fun  main(){
    //클래스를 이용해서 객체를 생성 (( 자바에서는 -> new MyCar))
    var c1 = MyCar() // new 예약어 필요 없음
    YourCar().drive();

    AirPlane()
    val p1 = Person("김구라")
    println(p1.name);

    val p2 = Person2("해골")
    println(p2.name);
}
