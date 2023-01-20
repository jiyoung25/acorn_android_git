package com.example.kotlin_test

class MachinGun{
    //총알 발동을 시도하는 매소드
    fun fire():MachinGun{
        println("헹~")
        return this
    }
}

class MyGun{
    fun fire(){
        println("빵야~")
    }
}

class YourWeapon{
    var name:String?=null
    fun prepare(){
        println("무기가 작동을 준비해요.")
    }
    fun move(){
        println("무기를 움직여요.")
    }
    fun use(){
        println("무기를 사용해요.")
    }
}

fun main(){
    val g1=MachinGun()
    //자기 자신을 return한 클래스이므로 계속하여 .을 찍고 사용할 수 있다.
    g1.fire().fire().fire()

    val g2 = MyGun()
    //특정 참조값을 블럭 안에서 여러번 사용할 수 있다. (return type이 자기자신이 아니어도 가능)
    with(g2){
        fire()
        fire()
        fire()
    }

    val w1 = YourWeapon()
    //w1이라는 참조값을 가지고 여러 작업을 할 때 쓸 수 있다.
    with(w1){
        name="잠수함"
        prepare()
        move()
        use()
    }
    
    //val w2 = YourWeapon().name="전투기"말도 안되는 코드
    val w2:Unit = YourWeapon().prepare() //prepare은 아무것도 return해주지 않는 method이므로 w2는 Unit type이다.

    //w3 변수를 선언하면서 YourWeapon 객체의 ㅣ생성과 동시에 작업을 적용을 하고 v3 참조값이 변하는것이다.
    val w3 = YourWeapon().apply{
        name="전투기"
        prepare()
        move()
    }

    w3.use()
}

