package com.github.sauterl.demeter.cineast

import com.github.kittinunf.fuel.httpGet
import org.apache.commons.codec.digest.DigestUtils
import java.io.File

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
object ImageDownloader {

  fun downloadImage(image: AbstractImage): File {
    val (request, response, result) = image.sourceUrl.httpGet().responseString()
    val sha = DigestUtils.sha256Hex(response.data)
    image.sha256 = sha
    File("temp/").mkdirs() // TODO More sophisticated location
    val file = File("temp/$sha.jpg")
    if (!file.exists()) {
      file.writeBytes(response.data)
    }
    image.path = file.toURI()
    return file
  }


}
