package com.github.sauterl.demeter

import com.github.sauterl.demeter.flickr.FlickrCrawler
import com.github.sauterl.demeter.flickr.SimpleFlickrCrawler
import com.github.sauterl.demeter.twitter.test

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
fun main(args: Array<String>){
    println("Started")
    if(args.size >= 1){
        when(args[0].toLowerCase()){
            "flickr" -> {
                FlickrCrawler.crawlFor("fantasybasel", 20)
                FlickrCrawler.close()
            }
            "twitter" -> {
                //SimpleTwitterCrawler().test()
                test()
            }
            else->{
                println("No mode specified. Known modes: 'flickr', 'twitter'")
            }
        }
    }else{
        SimpleFlickrCrawler().test()
    }
}
