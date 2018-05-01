package com.github.sauterl.demeter

import com.github.sauterl.demeter.flickr.SimpleFlickrCrawler
import com.github.sauterl.demeter.twitter.SimpleTwitterCrawler

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
fun main(args: Array<String>){
    println("Started")
    if(args.size >= 1){
        when(args[0].toLowerCase()){
            "flickr" -> SimpleFlickrCrawler().test()
            "twitter" -> SimpleTwitterCrawler().test()
            else->{
                println("No mode specified. Known modes: 'flickr', 'twitter'")
            }
        }
    }else{
        SimpleFlickrCrawler().test()
    }
}