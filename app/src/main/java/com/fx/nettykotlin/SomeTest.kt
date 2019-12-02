package com.fx.nettykotlin


fun main() {
//    print(p("aaa").toString())
    whentest(4)
}

fun p(str :String):Int?{

    return null;
}

fun whentest(str: Int):String{
    when(str){
        0,5-> {
            print("0-5")
            return "666"
        }

        else ->{
            print("else")
            return "else"
        }
    }
}