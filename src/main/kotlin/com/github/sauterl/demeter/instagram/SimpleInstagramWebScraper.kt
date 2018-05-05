package com.github.sauterl.demeter.instagram

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Connection
import org.jsoup.Jsoup

/**
 * Scrapes the public API of instagram for media associated with a certain tag.
 * @author loris.sauter
 */
object SimpleInstagramWebScraper {




  fun test() {
    // TODO Redo: https://medium.com/@h4t0n/instagram-data-scraping-550c5f2fb6f1
    val repsonse = Jsoup.connect("https://www.instagram.com/explore/tags/fantasybasel/?__a=1").method(Connection.Method.GET).execute()
    val doc = repsonse.parse()
    val scripts = doc.body().getElementsByTag("script")
    var body = ""
    var found = false
    scripts.forEach {
      println("Processing: $it") // DEBUG
      if (!found && it.data().startsWith("window._sharedData")) {
        body = it.data()
        println("Found it!: ${body.substring(0..50)}") // DEBUG
        found = true
      }
    }
    if (found) {
      val start = body.indexOf("{")
      val end = body.lastIndexOf("}")
      val jsonTxt = body.substring(start..end)
      println(jsonTxt) // DEBUG
      val mapper = jacksonObjectMapper()
      val json = mapper.readTree(jsonTxt)
      val entryData = json["entry_data"]
      val tagPage = entryData["TagPage"]
      println("$tagPage")
      val firstPage = tagPage[0]
      println("$firstPage")
      val graphql = firstPage["graphql"]
      println("$graphql")
      val hashtag = graphql["hashtag"]
      val hashtag2media = hashtag["edge_hashtag_to_media"]
      val pageInfo = hashtag2media["page_info"]
      val endCursor = pageInfo["end_cursor"]
      println("EndCursor: $endCursor")
      println(">>>>>>> https://www.instagram.com/explore/tags/fantasybasel/?max_id=${endCursor.asText()}")
      val edges = hashtag2media["edges"]
      println("$edges")
      println("${edges.size()}")
      val nodeList: MutableList<InstagramWebNode> = mutableListOf()
      for (i in 0..(edges.size()-1)) {
        val it = edges[i]
        val nodeJson = it["node"]
        println("$nodeJson")
        val nodeTxt = mapper.writeValueAsString(nodeJson)
        val out: InstagramWebNode = mapper.readValue(nodeTxt)
        nodeList.add(out)
      }
      nodeList.forEach { t: InstagramWebNode? ->
        run {
          println("ID: ${t?.id}\n\tcaption: ${t?.captionText}\n\turl: ${t?.display_url}\n\tsource: ${t?.sourceUrl}")
        }
      }
    }
  }
}