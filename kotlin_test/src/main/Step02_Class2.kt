//java에서 dto처럼 여러개의 값을 저장할 객체를 생성할 클ㄹ래스를 정의할 때에는 data class를 만들면 된다.

data class Member(var num:Int, var name:String, var addr:String)

fun main(){
    val m1=Member(1, '김구라', addr = '노량진')

    //data class로 생성한 객체의 참조값을 출력해보면 객체에 저장된 내용을 보여준다.
    println(m1)

    //수정 가능한 LIST를 얻어내서 참조값을 member라는 함수에 담기
    val members = mutablelstof<Member>()
    members.add(m1)
    members.add(Member(2, '해골', "행신동"))

    println("----------------------------eeeeeeeeeee")

    members.forEach{
        println(it)
    }
}