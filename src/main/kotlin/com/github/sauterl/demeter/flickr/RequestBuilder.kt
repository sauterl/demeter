package com.github.sauterl.demeter.flickr

import com.github.sauterl.demeter.cineast.ExtractionContainer
import com.github.sauterl.demeter.cineast.Item
import com.github.sauterl.demeter.cineast.MetaData
import com.github.sauterl.demeter.cineast.Object

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
class RequestBuilder{

    fun createRequest(photos:Array<FlickrPhoto>): ExtractionContainer {
        val items = photos.map {
            val metaData = mutableListOf<MetaData>()
            metaData.add(MetaData("Web", "source", it.getUrl().toString()))
            metaData.add(MetaData("Web", "id",it.id))
            val f =ImageDownloader.downloadImage(it)
            val item = Item(com.github.sauterl.demeter.cineast.Object(it.title, Object.MEDIA_TYPE_IMAGE),metaData.toTypedArray(),f.toURI())
            println("Processed ${it.title} and stored at ${f.path}")
            return@map item
        }
        return ExtractionContainer(items)
    }
}
