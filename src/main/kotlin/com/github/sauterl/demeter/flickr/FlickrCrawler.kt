package com.github.sauterl.demeter.flickr

import com.github.sauterl.demeter.cineast.AbstractImage
import com.github.sauterl.demeter.cineast.CineastInterface
import com.github.sauterl.demeter.cineast.ImageDownloader
import com.github.sauterl.demeter.utils.DataBase

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
object FlickrCrawler {

  /*
      val flickr = FlickrInterface()
      val photos = flickr.searchPhotos("fantasybasel", 20)
      val container = FlickrExtractionBuilder().createRequest(photos.photo)
      val cineast = CineastInterface("http://localhost:4567").apply { startSession() }
      // remove duplicates
      cineast.extractNew(mapper.writeValueAsString(container))
      */

  fun crawlFor(tag: String, amount: Int = 500) {
    if (running) {
      val flickr = FlickrInterface()
      val photos = flickr.searchPhotos(tag, amount)
      val cineast = CineastInterface("http://localhost:4567")
      val images = photos.photo.map {
        val image = AbstractImage(it.id, it.title, it.getUrl().toExternalForm())
        ImageDownloader.downloadImage(image)

        return@map image
      }
      println("Items recieved from service: ${images.size}")
      val toExtract = images.filter { img -> !DataBase.map().containsKey(img.sha256) }
      println("Items to extract: ${toExtract.size}")
      cineast.extractNew(toExtract, FlickrExtractionBuilder())
      toExtract.forEach {
        DataBase.map()[it.sha256] = it.path.path
        println("Added ${it.name} (${it.sha256}")
      }

    }

  }

  private var running = true

  fun close() {
    DataBase.close()
    running = false
  }
}
