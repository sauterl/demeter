package com.github.sauterl.demeter.api

import com.github.sauterl.demeter.cineast.*
import com.github.sauterl.demeter.utils.DataBase

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
class Crawler<T>(val retriever:AbstractImageRetriever<T>, val extractor: (img: ConcreteImage<T>) -> List<Item.Companion.MetaData>){
//val extrator: (img: ConcreteImage<T>) -> List<Item.Companion.MetaData>
  val cineast = CineastInterface()


  fun crawlFor(tag: String){
    println("Crawling for $tag")
    val images = retriever.retrieve(tag)
    images.forEach {
      ImageDownloader.downloadImage(it.rep)
    }
    println("Retrieved ${images.size} images")
    val toExtract = images.filter{img -> !DataBase.map.containsKey(img.rep.sha256)}
    println("Unseen items: ${toExtract.size}")
    cineast.extractNew(toExtract, extractor)
    println("Sent extraction request")
    toExtract.forEach {
      DataBase.map[it.rep.sha256] = it.rep.path.path
      println("Added ${it.rep.name} with sha ${it.rep.sha256}")
    }
  }
}
