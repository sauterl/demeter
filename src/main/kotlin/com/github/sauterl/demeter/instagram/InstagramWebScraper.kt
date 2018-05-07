package com.github.sauterl.demeter.instagram

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.github.sauterl.demeter.utils.traceTriple
import mu.KotlinLogging
import java.net.URLEncoder

/**
 * Scrapes the public API of instagram for media associated with a certain tag.
 *
 * Most of the knowledge is from experimenting wiht the public API.
 * The '__a=1' shortcut to directly get a JSON response is from https://medium.com/@h4t0n/instagram-data-scraping-550c5f2fb6f1
 * @author loris.sauter
 */
object InstagramWebScraper {

  private val logger = KotlinLogging.logger {}

  private fun createUrl(tag: String): String {
    return "https://www.instagram.com/explore/tags/${URLEncoder.encode(tag, "UTF-8")}/?__a=1"
  }

  private fun createFollowUpUrl(tag: String, cursor: String): String {
    return createUrl(tag) + "&max_id=$cursor"
  }

  /**
   * Retrieves first page
   */
  private fun retrieveFirstNodesForTag(tag: String): Pair<List<InstagramWebNode>, InstagramWebPageInfo> {
    val (request,response, result) = Fuel.get(createUrl(tag)).responseString()
    logger.trace { "Retrieving first nodes for tag $tag" }
    logger.traceTriple(request, response, result)
    return parseResponse(result)
  }

  private fun retrieveNodesForTag(tag: String, cursor: String): Pair<List<InstagramWebNode>, InstagramWebPageInfo> {
    val (request, response, result) = Fuel.get(createFollowUpUrl(tag, cursor)).responseString()
    logger.trace { "Retrieving further nodes for tag $tag" }
    logger.traceTriple(request, response, result)
    return parseResponse(result)
  }

  private fun parseResponse(result: Result<String, FuelError>): Pair<List<InstagramWebNode>, InstagramWebPageInfo> {
    val mapper = jacksonObjectMapper()
    val root = mapper.readTree(result.get())
    val graphql = root["graphql"]
    val hashtag = graphql["hashtag"]
    val hashtag2media = hashtag["edge_hashtag_to_media"]
    val pageInfo = hashtag2media["page_info"]
    val endCursor = pageInfo["end_cursor"].asText()
    val hasMore = pageInfo["has_next_page"].asBoolean()
    val edges = hashtag2media["edges"]
    val nodeList: MutableList<InstagramWebNode> = mutableListOf()
    for (i in 0..(edges.size() - 1)) {
      val it = edges[i]
      val nodeJson = it["node"]
      val nodeTxt = mapper.writeValueAsString(nodeJson)
      val out: InstagramWebNode = mapper.readValue(nodeTxt)
      nodeList.add(out)
    }
    return Pair(nodeList.toList(), InstagramWebPageInfo(endCursor, hasMore))
  }

  data class InstagramWebPageInfo(
      val cursor: String,
      val hasMore: Boolean
  )

  fun retrieveNodesForTag(tag: String, amount: Int = 100): List<InstagramWebNode> {
    val resultPair = retrieveFirstNodesForTag(tag)
    if (resultPair.first.size >= amount) {
      return resultPair.first.subList(0, amount)
    }
    var outList: MutableList<InstagramWebNode> = mutableListOf()
    resultPair.first.forEach { outList.add(it) }
    var res = resultPair
    while (res.second.hasMore && outList.size <= amount) {
      res = retrieveNodesForTag(tag, res.second.cursor)
      res.first.forEach {
        outList.add(it)
      }
    }
    return outList.subList(0, if(outList.size < amount) outList.size else amount)
  }
}
