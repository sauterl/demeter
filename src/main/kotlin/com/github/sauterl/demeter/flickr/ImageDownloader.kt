package com.github.sauterl.demeter.flickr

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import org.apache.commons.codec.digest.DigestUtils
import java.io.File

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
object ImageDownloader{

    fun downloadImage(photo: FlickrPhoto): File {
        val (request, response, result) = photo.getUrl().toString().httpGet().responseString()
        val sha = DigestUtils.sha256Hex(response.data)
        File("temp/").mkdirs()
        val file = File("temp/$sha.jpg")
        if(!file.exists()){
            file.writeBytes(response.data)
        }
        return file
    }


}
