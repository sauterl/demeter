package com.github.sauterl.demeter.flickr

import com.github.sauterl.demeter.cineast.AbstractImage
import com.github.sauterl.demeter.cineast.CineastInterface
import com.github.sauterl.demeter.cineast.ImageDownloader
import org.mapdb.DBMaker
import org.mapdb.Serializer

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
object FlickrCrawler {

    private val db = DBMaker.fileDB("flickr.db").fileMmapEnable().make()
    private val map = db.hashMap("map", Serializer.STRING, Serializer.STRING).createOrOpen()

    /*
        val flickr = FlickrInterface()
        val photos = flickr.searchPhotos("fantasybasel", 20)
        val container = FlickrExtractionBuilder().createRequest(photos.photo)
        val cineast = CineastInterface("http://localhost:4567").apply { startSession() }
        // remove duplicates
        cineast.extractNew(mapper.writeValueAsString(container))
        */

    fun crawlFor(tag: String, amount: Int = 500) {
        val flickr = FlickrInterface()
        val photos = flickr.searchPhotos(tag, amount)
        val cineast = CineastInterface("http://localhost:4567")
        val images = photos.photo.map {
            val image = AbstractImage(it.id, it.title, it.getUrl().toExternalForm())
            ImageDownloader.downloadImage(image)
            return@map image
        }
        val toExtract = images.filter { img -> !map.containsKey(img.sha256) }
        cineast.extractNew(toExtract, FlickrExtractionBuilder())
        toExtract.forEach {
            map.put(it.sha256, it.path.path)
            println("Added ${it.name} (${it.sha256}")
        }
    }
}
