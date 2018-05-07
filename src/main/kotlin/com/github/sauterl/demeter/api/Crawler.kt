package com.github.sauterl.demeter.api

import com.github.sauterl.demeter.cineast.CineastInterface
import com.github.sauterl.demeter.cineast.ImageDownloader
import com.github.sauterl.demeter.cineast.Item
import com.github.sauterl.demeter.utils.DataBase
import mu.KotlinLogging

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
class Crawler<T>(val name:String, val provider: ImageProvider<T>, val extractor: (img: ConcreteImage<T>) -> List<Item.Companion.MetaData>) {
  //val extrator: (img: ConcreteImage<T>) -> List<Item.Companion.MetaData>

  private val logger = KotlinLogging.logger("com.github.sauterl.demeter.$name")

  private val cineast = CineastInterface()


  fun crawlFor(query: String) {
    logger.info { "Crawling for query=$query" }
    var images = provider.serve(query)
    logger.info{"Found ${images.size} social media items"}
    images = images.filter { img -> !DataBase.set.contains(img.rep.sourceUrl) }
    logger.info { "Found ${images.size} new items (not already downloaded" }
    var counter = 0
    images.forEach {
      ImageDownloader.downloadImage(it.rep)
      DataBase.set.add(it.rep.sourceUrl)
      logger.info("Added ${it.rep.sourceUrl} to the database of seen urls")
      logger.info{"Downloaded ${counter++} / ${images.size}"}
    }
    val toExtract = images.filter { img -> !DataBase.map.containsKey(img.rep.sha256) }
    logger.info { "Will extract ${toExtract.size} of ${images.size} retrieved items." }
    cineast.extractNew(toExtract, extractor)
    logger.info { "Extraction request sent." }
    toExtract.forEach {
      DataBase.map[it.rep.sha256] = it.rep.sourceUrl
      logger.info{"Added ${it.rep.name} to the database as ${it.rep.sha256}"}
    }
  }

}
