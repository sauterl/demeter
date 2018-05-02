package com.github.sauterl.demeter.flickr

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.github.sauterl.demeter.cineast.CineastInterface
import com.github.sauterl.demeter.cineast.ImageDownloader


/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
class SimpleFlickrCrawler {

    private val flickrSoapUrl = "https://api.flickr.com/services/soap/"

    fun testMore() {
        /*
        val flickr = FlickrInterface()
        val photos = flickr.searchPhotos("fantasybasel", 20)
        photos.photo.forEach {
            println(ImageDownloader.downloadImage(it).path)
        }*/
    }

    fun testMorer() {
        /*
        val flickr = FlickrInterface()
        val photos = flickr.searchPhotos("fantasybasel", 20)
        val container = FlickrExtractionBuilder().createRequest(photos.photo)
        val cineast = CineastInterface("http://localhost:4567").apply { startSession() }
        // remove duplicates
        cineast.extractNew(mapper.writeValueAsString(container))
        */
    }

    fun test() {
        val key = FLICKR_PUBLIC
        var postData = createPhotosByTagRequest("fantasybasel")
        println("===")

        val (request, response, result) = Fuel.post(flickrSoapUrl).body(postData).responseString()


        println("Request: $request\n---\nResponse: $response\n---\nResult: $result")

        println("Stripped: " + extractJson(result))

        val photos = deserializePhotosResult(extractJson(result))

        println("Number of photos: ${photos.photo.size}")

        photos.photo.forEach { println(it.getUrl()) }
    }

    fun extractJson(result: Result<String, FuelError>): String {
        val start = result.get().indexOf('(')
        val end = result.get().lastIndexOf(')')
        return result.get().substring((start + 1)..end)
    }

    fun createPhotosByTagRequest(tag: String): String {
        return "<s:Envelope\n" +
                "\txmlns:s=\"http://www.w3.org/2003/05/soap-envelope\"\n" +
                "\txmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\"\n" +
                "\txmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"\n" +
                ">\n" +
                "\t<s:Body>\n" +
                "\t\t<x:FlickrRequest xmlns:x=\"urn:flickr\">\n" +
                "\t\t\t<method>flickr.photos.search</method>\n" +
                "\t\t\t<api_key>$FLICKR_PUBLIC</api_key>\n" +
                "\t\t\t<tags>$tag</tags>\n" +
                "\t\t\t<format>json</format>\n" +
                "\t\t\t<per_page>500</per_page>" +
                "\t\t</x:FlickrRequest>\n" +
                "\t</s:Body>\n" +
                "</s:Envelope>"
    }

    private val mapper = jacksonObjectMapper()

    fun deserializePhotosResult(json: String): FlickrPhotosResult {
        return mapper.readValue<FlickrPhotosResultContainer>(json).photos
    }
}


