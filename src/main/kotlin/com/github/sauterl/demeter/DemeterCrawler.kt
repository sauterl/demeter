package com.github.sauterl.demeter

import com.github.sauterl.demeter.api.Crawler
import com.github.sauterl.demeter.cineast.Item
import com.github.sauterl.demeter.config.Configuration
import com.github.sauterl.demeter.flickr.FlickrImageProvider
import com.github.sauterl.demeter.flickr.FlickrPhoto

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
object DemeterCrawler {

  fun crawlFlickr(){

    val flickrCrawler = Crawler<FlickrPhoto>(FlickrImageProvider()){
      val list : MutableList<Item.Companion.MetaData> = mutableListOf()

      list.add(Item.Companion.MetaData("sourceUrl",it.img.getUrl().toExternalForm()))
      list.add(Item.Companion.MetaData("ownerName", it.img.ownername ?:"n/a"))
      list.add(Item.Companion.MetaData("ownerId", it.img.owner))
      list.add(Item.Companion.MetaData("datetaken", it.img.datetaken?:"n/a"))
      list.add(Item.Companion.MetaData("uploaded", it.img.dateupload ?:"n/a"))
      list.add(Item.Companion.MetaData("tags",it.img.tagList()?:"n/a"))

      return@Crawler list.toList()
    }
    val query = Configuration.Flickr.query
    println("Crawling flickr for $query")
    flickrCrawler.crawlFor(query)
  }

}