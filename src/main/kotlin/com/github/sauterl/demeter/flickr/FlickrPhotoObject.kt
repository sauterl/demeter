package com.github.sauterl.demeter.flickr

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class FlickrPhotoObject(
    val id: String,
    val secret: String,
    val server: String,
    val farm: Number,
    val dateuploaded: String,
    val license: Number,
    val originalsecret: String,
    val originalformat: String,
    val owner: FlickrOwner,
    val title: FlickrTitle,
    val dates: FlickrDate,
    val views: String,
    val tags: FlickrTagContainer,
    val urls: FlickrUrlContainer,
    val media: String
    // TODO There are more fields, but these are probably the most interesting ones

) {
  data class FlickrOwner(
      val nsid: String,
      val username: String,
      val location: String,
      val iconserver: String,
      val iconfarm: Number,
      val path_alias: String
  )

  data class FlickrTitle(
      val _content: String
  )

  data class FlickrDate(
      val posted: String,
      val taken: String
  )

  data class FlickrTag(
      val id: String,
      val author: String,
      val authorname: String,
      val raw: String,
      val _content: String,
      val machine_tag: Number
  )

  data class FlickrTagContainer(
      val tag: Array<FlickrTag>
  )

  data class FlickrUrl(
      val type: String,
      val _content: String
  ) {
    fun getTrueUrl(): String {
      return _content.replace("\\", "")
    }
  }

  data class FlickrUrlContainer(
      val url: FlickrUrl
  )
}
