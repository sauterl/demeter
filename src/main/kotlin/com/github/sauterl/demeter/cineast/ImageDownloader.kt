package com.github.sauterl.demeter.cineast

import com.github.kittinunf.fuel.httpGet
import com.github.sauterl.demeter.config.Configuration
import mu.KotlinLogging
import org.apache.commons.codec.digest.DigestUtils
import java.io.File

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
object ImageDownloader {

  private val logger = KotlinLogging.logger {  }

  fun downloadImage(image: AbstractImage, extension :String = ".jpg"): File {
    val (request, response, _) = image.sourceUrl.httpGet().responseString()
    logger.trace{"Request: $request"}
    logger.trace{"Response Code: ${response.statusCode}. Headers=${response.headers}"}
    val sha = DigestUtils.sha256Hex(response.data)
    image.sha256 = sha
    val imgDir = File(Configuration.General.imgDir)
    imgDir.mkdirs()
    val file = imgDir.resolve("${image.sha256}$extension")
    if (!file.exists()) {
      file.writeBytes(response.data)
      logger.trace { "Wrote ${file.path} to disk" }
    }else{
      logger.trace { "There already was a file ${file.path}" }
    }
    image.path = file.toURI()
    return file
  }


}
