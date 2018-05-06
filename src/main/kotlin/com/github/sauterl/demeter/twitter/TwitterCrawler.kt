package com.github.sauterl.demeter.twitter

import com.github.sauterl.demeter.cineast.AbstractImage
import com.github.sauterl.demeter.cineast.CineastInterface
import com.github.sauterl.demeter.cineast.ImageDownloader
import com.github.sauterl.demeter.utils.DataBase

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
object TwitterCrawler {


  fun crawlFor(tag: String) {
    val twitter = TwitterInterface()
    val resp = twitter.searchTweets(tag)
    val photoTweets = resp.statuses.filter { it.hasPhoto() }
    val images = photoTweets.map {
      val words = it.full_text.split(" ")
      val sb = StringBuffer()
      words.subList(0, 5).forEach { w -> sb.append("$w ") }
      val media = it.getMedia()?.get(0) // TODO currently only one photo
      val img = AbstractImage(it.id_str, sb.toString().trimEnd(), media?.media_url.toString()) // Due to previous filter, shouldn't be null
      ImageDownloader.downloadImage(img)

      return@map img
    }
    println("Retrieved ${images.size} photo-tweets")
    val toExtract = images.filter { img -> !DataBase.map.containsKey(img.sha256) }
    val cineast = CineastInterface() // Defaults to url from config
    cineast.extractNew(toExtract, TwitterExtractionBuilder())
    cineast.extractEnd()
    toExtract.forEach {
      DataBase.map[it.sha256] = it.path.path
      println("Added ${it.name} (${it.sha256})")
    }
  }

  fun close() {
    DataBase.close()
  }
}
