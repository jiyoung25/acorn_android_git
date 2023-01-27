package com.example.step03_extends

open class Book{

}
// Book 클래스를 상속받은 MyBook 클래스
class MyBook:Book(){

}

abstract class Test{
    abstract fun go()
}

class Bird{
    companion object{
        fun sing(){
            println("노래해요!")
        }
    }
}

class MyData{
    //문자열을 담을 필드
    lateinit var name:String

    //인자로 전달받은 문자열을 필드에 저장하는 메소드
    fun putName(name:String){
        this.name=name
    }
}

fun main() {
    //Bird 클래스의 sing() 메소드를 호출하려면 여기를 어떻게 코딩해야 하는가?
    Bird.sing()


    var str:MutableMap<String, Any> = mutableMapOf()
    //str["id"]="kim"
    //str["pwd"]="1234"
    //str["address"]="seoul"

    str.put("id", "kim")
    str.put("pwd", "1234")
    str.put("address", "seoul")
    println(str)

    var a:()->Unit = { }
    val b : (Int) -> String = { num:Int -> "bye" }

    val nums:List<Int> = listOf<Int>(1,2,3,4,5,6,7,8,9,10)

    val result:List<Int> = nums.filter { it%2 == 0 }

    println(result)

    var c:String? = null

    var result2 = c ?: "hi"

    println(result2)
    val  msgs = listOf<String>("eee", "ddd", "ccc", "bbb", "aaa")

    for(i in msgs.size-1 downTo 0){
        val tmp = msgs[i]
        println(tmp)
    }

}

