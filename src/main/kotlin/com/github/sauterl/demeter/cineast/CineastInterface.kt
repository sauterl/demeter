package com.github.sauterl.demeter.cineast

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
class CineastInterface(val url:String){

    companion object {
        const val API_ACCESS = "/api/v1/"
        const val SESSION = "session/"
        const val START = "start"
        const val END = "end"
        const val EXTRACT_NEW = SESSION+"extract/new"
        const val EXTRACT_END = SESSION+"extract/end"
        const val USER_NAME = "user"
        const val USER_PW = "user"
    }

    data class User(val username:String = USER_NAME, val password: String = USER_PW)
    data class CredentialsContainer(val credentials:User = User())

    data class Session(val validUntil:Long, val type:String, val sessionId:String)

    private fun getTheUrl():String{
        return url+ API_ACCESS
    }

    lateinit var session:Session

    private val mapper = jacksonObjectMapper()

    fun startSession(){
        val (request, response, result) = Fuel.post(getTheUrl()+  SESSION+START).body(mapper.writeValueAsString(CredentialsContainer())).responseString()
        session = mapper.readValue<Session>(result.get() )

    }

    fun endSession(user:String){
        // TODO check if sesison still valid
        val (request, response, result) = Fuel.post(getTheUrl()+ API_ACCESS+ SESSION+END+"/${session.sessionId}").body(user).responseString()
    }

    fun extractNew(images:List<AbstractImage>,builder:ExtractionBuilder){
        val container = builder.build(images)
        val (request, response, result) = Fuel.post(getTheUrl()+ EXTRACT_NEW).body(mapper.writeValueAsString(container)).responseString()
        // TODO process result to inform caller about it
    }

    fun extractEnd(content:String){
        val (request, response, result) = Fuel.post(getTheUrl()+ API_ACCESS+ EXTRACT_END).body(content).responseString()
    }
}
