package github.magnilex.record.poller.service

import com.google.api.client.http.GenericUrl
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.IdTokenCredentials
import com.google.auth.oauth2.IdTokenProvider
import github.magnilex.record.poller.model.Record
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader

class Loader {
  fun loadAll(endpoints: List<String>): List<Record> {
    return endpoints.flatMap { load(it) }
  }

  private fun load(endpoint: String): List<Record> {
    println("Calling endpoint '${endpoint}'")
    val request = NetHttpTransport()
            .createRequestFactory(HttpCredentialsAdapter(getCredentials(endpoint)))
            .buildGetRequest(GenericUrl(endpoint))
    val response = request.setReadTimeout(500_000).execute().content.bufferedReader().use(BufferedReader::readText)
    return Json.decodeFromString(response)
  }

  fun getCredentials(endpoint: String): IdTokenCredentials {
    val credentials = GoogleCredentials.getApplicationDefault()
    require(credentials is IdTokenProvider) { "Credentials are not an instance of IdTokenProvider." }
    return IdTokenCredentials.newBuilder()
            .setIdTokenProvider(credentials as IdTokenProvider)
            .setTargetAudience(endpoint)
            .build()
  }

}
