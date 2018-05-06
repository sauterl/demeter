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
class Crawler<T>(val provider: ImageProvider<T>, val extractor: (img: ConcreteImage<T>) -> List<Item.Companion.MetaData>) {
  //val extrator: (img: ConcreteImage<T>) -> List<Item.Companion.MetaData>

  private val logger = KotlinLogging.logger{}

  private val cineast = CineastInterface()


  fun crawlFor(query: String) {
    logger.info { "Crawling for query=$query" }
    val images = provider.serve(query)
    var counter = 0
    images.forEach {
      ImageDownloader.downloadImage(it.rep)
      logger.info{"Downloaded ${counter++} / ${images.size}"}
    }
    val toExtract = images.filter { img -> !DataBase.map.containsKey(img.rep.sha256) }
    logger.info { "Will extract ${toExtract.size} of ${images.size} retrieved items." }
    cineast.extractNew(toExtract, extractor)
    logger.info { "Extraction request sent." }
    toExtract.forEach {
      DataBase.map[it.rep.sha256] = it.rep.path.path
      logger.info{"Added ${it.rep.name} to the database as ${it.rep.sha256}"}
    }
  }

}
