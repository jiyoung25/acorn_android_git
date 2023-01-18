package com.example.kotlin_test2

//추상 클래스
abstract class Weapon{
    fun move():Weapon{
        println("이동합니다.")
        return this
    }
    abstract fun attack()
}

class MyWeaon: Weapon(){
    override fun attack() {
        println("무언가를 공격해요")
    }
}

fun main(){
    val w1=MyWeaon()
    w1.move().attack()
    //weapon의 return type이 weapon이므로 가능한 것
    
    println("---------")
    /*
        with(참조값){

        }
        참조값을 가지고 참조값과 함께 여러가지 작업을 { }안에서 한다.
     */

    with(w1){
        move()
        attack()
    }
    
    //weaon 추상클래스를 상속받은 익명의 클래스
    //익명 클래스를 이용해서 추상클래스(Weapon) type의 참조값 얻어내기
    val w2 = object:Weapon(){
        override fun attack() {
            println("공중 공격을 해요!")
        }
    }
    w2.move().attack()

    //다형성 확인
    
    
    
    w2.move()
    w2.attack()

}