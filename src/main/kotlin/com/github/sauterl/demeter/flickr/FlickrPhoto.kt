package com.github.sauterl.demeter.flickr

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
        val isfamily: Number)
