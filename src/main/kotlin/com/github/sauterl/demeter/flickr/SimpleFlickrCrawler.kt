package com.github.sauterl.demeter.flickr

import com.github.kittinunf.fuel.Fuel


/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
class SimpleFlickrCrawler {

    val flickrSoapUrl = "https://api.flickr.com/services/soap/"

    fun test(){
        val key = FLICKR_PUBLIC
        var postData = "<s:Envelope\n" +
                "\txmlns:s=\"http://www.w3.org/2003/05/soap-envelope\"\n" +
                "\txmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\"\n" +
                "\txmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"\n" +
                ">\n" +
                "\t<s:Body>\n" +
                "\t\t<x:FlickrRequest xmlns:x=\"urn:flickr\">\n" +
                "\t\t\t<method>flickr.photos.search</method>\n" +
                "\t\t\t<api_key>$key</api_key>\n" +
                "\t\t\t<tags>fantasybasel</tags>\n" +
                "\t\t\t<format>json</format>\n" +
                "\t\t</x:FlickrRequest>\n" +
                "\t</s:Body>\n" +
                "</s:Envelope>"
        println("Post-Data: $postData")
        println("===")

        val (request, response, result) = Fuel.post(flickrSoapUrl).body(postData).responseString()


        println("Request: $request\n---\nResponse: $response\n---\nResult: $result")
    }

}