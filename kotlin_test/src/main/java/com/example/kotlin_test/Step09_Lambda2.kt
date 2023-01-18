package com.example.kotlin_test

//인터페이스 정의하기
interface Drill {
    fun hole()
}

fun main(){
    //java: public void a(){}
    //kotlin: fun a():Unit{} OR fun a(){} //type에 Unit을 써도 되고, type을 생략해도 void와 같은 효과
    //코틀린에서 Unit은 원시type이라고 지칭하고 java의 void와 비슷한 역할을 한다.
    //그럼 생략해도 되는걸 왜 굳이 Unit type을 return type으로 명명하는가?
    fun a():Unit{
        println("a함수 호출됨")
    }

    a()

    val isRun:Boolean=true
    var myName:String?=null
    myName="김구라"

    //함수를 만들어서 변수에 담고 싶다면,
    //type의 추론이 가능하지만 명시적으로 type을 표시하자고 한다면 어떻게 해야할까?
    /*
        ()->Unit은
        1. 함수에 전달되는 인자는 없으며
        2. 아무 값도 리턴하지 않는
        3. 함수 type을 의미한다.
     */
    val b:()->Unit = fun(){
        println("b함수 호출됨")
    }

    b()

    
    //fun()생략 가능
    val c:()->Unit={
        println("c함수 호출됨")
    }

    /*
        (String)->String
        1. String type인자를 하나 받아서
        2. String type을 리턴해주는
        3. 함수 타입
     */
    //함수의 type은 (String)->String타입이다.
    val d:(String)->String = fun(name:String):String{
        return "내 이름은 ${name}"
    }

    //위를 아래와 같이 줄일수 있다.
    val e:(String)->String =  { name -> "내이름은 ${name}" }

    println(e("김구라"))

    fun useFunc(f:()->Unit){
        //인자로 전달 받은 함수 호출하기
        f()
    }



    fun useDrill(d:Drill){
        d.hole()
    }

    fun main(){
        //원래 모양
        useFunc(fun(){
            println("익명함수가 호출됨! 1")
        })

        // fun() 생략
        useFunc({
            println("익명 함수가 호출됨! 2")
        })

        //위를 좀 더 간단히 한 최종 모양
        useFunc{
            println("익명 함수 호출됨! 3")
        }

        useDrill(object:Drill{
            override fun hole() {
                println("구멍을 뚫어요")
            }

        })

    }
    


}