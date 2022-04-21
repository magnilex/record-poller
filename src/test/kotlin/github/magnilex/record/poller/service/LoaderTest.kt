package github.magnilex.record.poller.service

import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.okJson
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.google.auth.oauth2.IdTokenCredentials
import github.magnilex.record.poller.model.record
import github.magnilex.record.poller.model.recordAsJson
import io.kotest.matchers.collections.shouldHaveSingleElement
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.kotlin.any

@WireMockTest
class LoaderTest {

  private lateinit var loader: Loader

  @BeforeEach
  internal fun setUp() {
    loader = spy(Loader::class.java)
    doReturn(mock(IdTokenCredentials::class.java)).`when`(loader).getCredentials(any())
  }
  
  @Test
  fun `Load all should load from endpoints`(wiremock: WireMockRuntimeInfo) {
    stubFor(get("/endpoint1").willReturn(okJson(recordAsJson)))
    val endpoints = listOf("http://localhost:${wiremock.httpPort}/endpoint1")

    val loaded = loader.loadAll(endpoints)

    loaded shouldHaveSingleElement record
  }

}