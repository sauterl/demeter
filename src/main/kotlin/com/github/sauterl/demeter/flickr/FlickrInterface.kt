package com.github.sauterl.demeter.flickr

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
class FlickrInterface {

    val flickrSoapUrl = "https://api.flickr.com/services/soap/"

    fun searchPhotos(tag: String): FlickrPhotosResult {
        val req = envelopeOpen +
                bodyOpen +
                requestOpen +
                createMethod("flickr.photos.search") +
                createParam("tags", tag) +
                createParam("per_page", "500") +
                requestClose +
                bodyClose +
                envelopeClose

        val (request, response, result) = Fuel.post(flickrSoapUrl).body(req).responseString()


        println("Request: $request\n---\nResponse: $response\n---\nResult: $result")

        println("Stripped: " + extractJson(result))

        return deserializePhotosResult(extractJson(result))
    }

    fun getInfo(photo:FlickrPhoto){
        
    }

    private val mapper = jacksonObjectMapper()

    fun deserializePhotosResult(json: String): FlickrPhotosResult {
        return mapper.readValue<FlickrPhotosResultContainer>(json).photos
    }

    fun extractJson(result: Result<String, FuelError>): String {
        val start = result.get().indexOf('(')
        val end = result.get().lastIndexOf(')')
        return result.get().substring((start + 1)..end)
    }

    val formatJson = "<format>json</format>"
    val apiKey = "<api_key>$FLICKR_PUBLIC</api_key>"

    val envelopeOpen = "<s:Envelope\n" +
            "\txmlns:s=\"http://www.w3.org/2003/05/soap-envelope\"\n" +
            "\txmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\"\n" +
            "\txmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"\n" +
            ">"
    val envelopeClose = "</s:envelope>"
    val bodyOpen = "<s:Body>"
    val bodyClose = "</s:Body>"
    val requestOpen = "<x:FlickrRequest xmlns:x=\"urn:flickr\">"
    val requestClose = "</x:FlickrRequest>"

    fun createParam(name: String, value: String): String {
        return "<$name>$value</$name>"
    }

    fun createMethod(method: String): String {
        return "<method>$method</method>"
    }


}