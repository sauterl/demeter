package com.github.sauterl.demeter.utils

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import mu.KLogger
import kotlin.math.min

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
fun KLogger.traceTriple(request: Request, response: Response, result: Result<String, FuelError>) {
  trace { "Request: $request" }
  trace { "Response: $response" }
  trace { "Result: $result" }
}

fun extractTitle(str:String, targetLength:Int=5, def:String):String{
  val words = str.replace("\n", " ").split(" ")
  return if(words.isEmpty()){
    def
  }else{
    val sb = StringBuilder()
    val end = min(words.size, targetLength)
    words.subList(0,end).forEach { sb.append("$it ") }
    sb.toString().trimEnd()
  }
}
/*
fun main(args:Array<String>){
  listOf(
      "This is a sentence",
      "This is a very long sentence with lots of words and random chrachter combinations asdflka asdfjsldfjiew dfiowekjfd",
      "One",
      "Two Words",
      "ööls dsfkksdf öwkefjojfkasdjfoöwe fwf",
      "asdfköas asöde llsd iijowfe",
      "aösdl öljsl ölaskjdföljs dlsjfl",
      "some lines\nwith\n\ttabas and newlines\nasdf well as #tag1#tag2 #tags"
  ).forEach {
    val t = extractTitle("$it")
    println("$it -> $t|")
  }
}
*/
