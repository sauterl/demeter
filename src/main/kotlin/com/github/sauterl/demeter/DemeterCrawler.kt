package com.github.sauterl.demeter

import com.github.sauterl.demeter.api.Crawler
import com.github.sauterl.demeter.cineast.Item
import com.github.sauterl.demeter.config.Configuration
import com.github.sauterl.demeter.flickr.FlickrImageProvider
import com.github.sauterl.demeter.instagram.InstagramImageProvider
import com.github.sauterl.demeter.twitter.TwitterImageProvider

/**
 *
 * CONVENTION: cineast-metadata: source = source of the image, sourceURL = source of the social media item (e.g. the tweet / the flickr post / the instagram post
 *
 * @author loris.sauter
 */
object DemeterCrawler {

  fun crawlFlickr(){

    val flickrCrawler = Crawler(FlickrImageProvider()){
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

  fun crawlTwitter(){
    val twitterCrawler = Crawler(TwitterImageProvider()){
      val media = it.img.media
      val list : MutableList<Item.Companion.MetaData> = mutableListOf()

      list.add(Item.Companion.MetaData("sourceUrl", media.url.toExternalForm()))
      list.add(Item.Companion.MetaData("ownerId",it.img.tweet.user.id_str))
      list.add(Item.Companion.MetaData("ownerName",it.img.tweet.user.name))
      list.add(Item.Companion.MetaData("ownerDisplay",it.img.tweet.user.screen_name))
      list.add(Item.Companion.MetaData("tweet",it.img.tweet.full_text))
      list.add(Item.Companion.MetaData("created_at",it.img.tweet.created_at))

      return@Crawler list.toList()

    }
    val query = Configuration.Twitter.query
    println("Crawling twitter for $query")
    twitterCrawler.crawlFor(query)
  }

  fun crawlInstagram(){
    val instaGrawler = Crawler(InstagramImageProvider()){
      val list : MutableList<Item.Companion.MetaData> = mutableListOf()

      list.add(Item.Companion.MetaData("sourceUrl",it.img.sourceUrl))
      list.add(Item.Companion.MetaData("ownerId",it.img.ownerId))
      list.add(Item.Companion.MetaData("taken_at","$it.img.taken_at_timestamp"))
      list.add(Item.Companion.MetaData("caption", it.img.captionText ?:"n/a"))


      return@Crawler list.toList()
    }
    val query = Configuration.Instagram.query
    println("Crawling instagram for $query")
    instaGrawler.crawlFor(query)
  }
}