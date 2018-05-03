package com.github.sauterl.demeter.flickr

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
data class FlickrPhotosResult(
    val page: Number,
    val pages: Number,
    val perpage: Number,
    val total: Number,
    val photo: Array<FlickrPhoto>
)
