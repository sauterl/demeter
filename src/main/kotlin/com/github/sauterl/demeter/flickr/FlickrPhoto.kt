package com.github.sauterl.demeter.flickr

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.net.URL

/**
 * POJO for a <i>photo</i> in flickr-speech
 *
 * Description of extras:
 *
 *
 * @author loris.sauter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class FlickrPhoto(
        val id: String,
        val owner: String,
        val secret: String,
        val server: String,
        val farm: Number,
        val title: String,
        val ispublic: Number,
        val isfriend: Number,
        val isfamily: Number,
        val description: FlickrDescription?,
        /**
         * In unix timestamp
         */
        val dateupload: String?,
        /**
         * In MySQL date format
         */
        val datetaken: String?,
        /**
         * ee https://www.flickr.com/services/api/misc.dates.html
         * 0	Y-m-d H:i:s
         * 4	Y-m
         * 6	Y
         * 8	Circa..
         */
        val datetakengranularity: String?,
        val ownername: String?,
        val tags: String?,
        val latitude: Number?,
        val longitude: Number?
        ) {

    data class FlickrDescription(
            val _content: String
    )

    fun getUrl(): URL {
        return URL("https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg")
    }

    fun getTagList(): List<String> {
        return tags?.split(" ") ?: emptyList()
    }
}
