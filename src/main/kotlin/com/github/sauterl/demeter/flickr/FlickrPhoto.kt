package com.github.sauterl.demeter.flickr

import java.net.URL

/**
 * POJO for a <i>photo</i> in flickr-speech
 *
 * @author loris.sauter
 */
data class FlickrPhoto(
        val id: String,
        val owner: String,
        val secret: String,
        val server: String,
        val farm: Number,
        val title: String,
        val ispublic: Number,
        val isfriend: Number,
        val isfamily: Number){

    fun getUrl(): URL {
       return URL("https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg")
    }
}
