package com.github.sauterl.demeter.flickr

import com.github.sauterl.demeter.api.ConcreteImage
import com.github.sauterl.demeter.api.ImageProvider
import com.github.sauterl.demeter.cineast.AbstractImage
import com.github.sauterl.demeter.config.Configuration

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
class FlickrImageProvider : ImageProvider<FlickrPhoto> {
  override fun serve(tag: String): List<ConcreteImage<FlickrPhoto>> {
    val flickr = FlickrInterface()
    val res = flickr.searchPhotos(Configuration.Flickr.query, Configuration.Flickr.amount.toInt())
    return res.photo.map{
      val rep = AbstractImage(it.id, it.title, it.getUrl().toExternalForm())
      return@map ConcreteImage(rep, it)
    }
  }
}