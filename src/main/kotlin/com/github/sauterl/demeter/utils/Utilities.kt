package com.github.sauterl.demeter.utils

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import mu.KLogger

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
fun KLogger.traceTriple(request: Request, response: Response, result: Result<String, FuelError>) {
  trace { "Request: $request" }
  trace { "Response: $response" }
  trace { "Result: $result" }
}