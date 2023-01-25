package com.example.kotlin_test

import java.io.BufferedReader
import java.io.FileReader
import java.io.InputStream

/*
    Kotlin에서 입출력은 java의 class를 직접 import해서 사용해야 한다.
 */

fun main(){
    //키보드에 연결된 InputSteam
    var kbd: InputStream = System.`in`
    //c:acorn202210 myForder|memo.txt 파일에ㅔ서 문자열을 읽어들이려면?
    //in java => fileReader.new FileReader(File)

    //FileReader
    val fr = FileReader("c:/acorn202210/myFolder/memo.txt")
    //BufferedReader
    val br = BufferedReader(fr)
    val line = br.readLine()
    println(line)
    while(true){
        //한줄씩 읽어들이고
        val line = br.readLine()
        //만일 더이상 읽을 문자열이 없다면 반복문 탈출
        if(line == null) break
        //읽은 문자열 출력
        println(line)
    }
}
