package com.github.sauterl.demeter.cineast

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.sauterl.demeter.api.ConcreteImage
import com.github.sauterl.demeter.config.Configuration
import com.github.sauterl.demeter.utils.traceTriple
import mu.KotlinLogging
import java.io.File

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */

class CineastInterface(val url: String = Configuration.Cineast.host) {

  private val logger = KotlinLogging.logger {}

  companion object {
    const val API_ACCESS = "/api/v1/"
    const val SESSION = "session/"
    const val START = "start"
    const val END = "end"
    const val EXTRACT_NEW = SESSION + "extract/new"
    const val EXTRACT_END = SESSION + "extract/end"
  }

  data class User(val username: String = Configuration.Cineast.user, val password: String = Configuration.Cineast.pw)
  data class CredentialsContainer(val credentials: User = User())

  data class Session(val validUntil: Long, val type: String, val sessionId: String)

  private fun getTheUrl(): String {
    return url + API_ACCESS
  }

  lateinit var session: Session

  private val mapper = jacksonObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)

  fun startSession() {
    val (request, response, result) = Fuel.post(getTheUrl() + SESSION + START).body(mapper.writeValueAsString(CredentialsContainer())).responseString()
    session = mapper.readValue<Session>(result.get())
    logger.traceTriple(request, response, result)
  }

  fun endSession(user: String) {
    // TODO check if sesison still valid
    val (request, response, result) = Fuel.post(getTheUrl() + API_ACCESS + SESSION + END + "/${session.sessionId}").body(user).responseString()
    logger.traceTriple(request, response, result)
  }

  fun extractNew(images: List<AbstractImage>, builder: ExtractionBuilder) {
    if (images.isEmpty()) {
      return
    }
    val container = builder.build(images)
    val (request, response, result) = Fuel.post(getTheUrl() + EXTRACT_NEW).body(mapper.writeValueAsString(container)).responseString()
    logger.traceTriple(request,response,result)
    // TODO process result to inform caller about it
  }

  fun <T> extractNew(items: List<ConcreteImage<T>>, extractor: (img: ConcreteImage<T>) -> List<Item.Companion.MetaData>) {
    val container = ExtractionContainer(items.map {
      val metaData = mutableListOf<Item.Companion.MetaData>()
      metaData.add(Item.Companion.MetaData("source", it.rep.sourceUrl))
      metaData.add(Item.Companion.MetaData("id", it.rep.id))
      val metas = extractor(it)
      metas.forEach {
        if (!metaData.contains(it)) {
          metaData.add(it)
        }
      }
      return@map Item(Item.Companion.Object(it.rep.name, path = File(it.rep.path).name), metaData, it.rep.path)
    })
    val (request, response, result) = Fuel.post(getTheUrl() + EXTRACT_NEW).body(mapper.writeValueAsString(container)).responseString()
    logger.traceTriple(request,response,result)
  }

  fun extractEnd(content: String = "{}") {
    val (request, response, result) = Fuel.post(getTheUrl() + EXTRACT_END).body(content).responseString()
    logger.traceTriple(request,response,result)
  }


}


