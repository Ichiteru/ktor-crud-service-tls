package com.km.routes.common

import kotlinx.serialization.Serializable

@Serializable
data class RouteResponse<T>(
  val body: T? = null,
  val errorMessage: String? = null
) {

  companion object {

    fun <T> ok(body: T) = RouteResponse(body = body)
  }

}
