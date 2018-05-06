package com.github.sauterl.demeter.flickr

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.github.sauterl.demeter.utils.traceTriple
import mu.KotlinLogging

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
class FlickrInterface {

  private val logger = KotlinLogging.logger {}

  val flickrSoapUrl = "https://api.flickr.com/services/soap/"

  fun searchPhotos(tag: String, amount: Int = 500): FlickrPhotosResult {
    val req = envelopeOpen +
        bodyOpen +
        requestOpen +
        createMethod("flickr.photos.search") +
        formatJson +
        apiKey +
        createParam("tags", tag) +
        createParam("per_page", "$amount") +
        createParam("extras", "description," +
            "license," +
            "date_upload," +
            "date_taken," +
            "owner_name," +
            "original_format," +
            "geo,tags," +
            "machine_tags," +
            "url_o," +
            "url_m") +
        requestClose +
        bodyClose +
        envelopeClose

    val (request, response, result) = Fuel.post(flickrSoapUrl).body(req).responseString()
    logger.traceTriple(request, response, result)
    /*
    Howto
    createParam("bla"){
        createParam("bla"){
            "blub"
        }
    }*/

    return deserializePhotosResult(extractJson(result))
  }

  private val mapper = jacksonObjectMapper()

  private fun deserializePhotosResult(json: String): FlickrPhotosResult {
    return mapper.readValue<FlickrPhotosResultContainer>(json).photos
  }

  private fun extractJson(result: Result<String, FuelError>): String {
    val start = result.get().indexOf('(')
    val end = result.get().lastIndexOf(')')
    return result.get().substring((start + 1)..(end - 1))
  }

  private val formatJson = "<format>json</format>"
  private val apiKey = "<api_key>$FLICKR_PUBLIC</api_key>"

  private val envelopeOpen = "<s:Envelope\n" +
      "\txmlns:s=\"http://www.w3.org/2003/05/soap-envelope\"\n" +
      "\txmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\"\n" +
      "\txmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"\n" +
      ">"
  private val envelopeClose = "</s:envelope>"
  private val bodyOpen = "<s:Body>"
  private val bodyClose = "</s:Body>"
  private val requestOpen = "<x:FlickrRequest xmlns:x=\"urn:flickr\">"
  private val requestClose = "</x:FlickrRequest>"

  private fun createParam(name: String, value: String): String {
    return "<$name>$value</$name>"
  }

  private fun createMethod(method: String): String {
    return "<method>$method</method>"
  }

  /*fun createParam(name: String, value: () -> String): String = "<$name>$value</$name>"*/


}
