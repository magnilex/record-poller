package github.magnilex.record.poller.service

import github.magnilex.record.poller.model.record
import github.magnilex.record.poller.service.EnvironmentVariableReader.EnvironmentVariable
import github.magnilex.record.poller.service.reporter.Reporter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

internal class RecordPollerTest {

  private val endpoints = listOf("endpoint1", "endpoint2")
  private lateinit var recordPoller: RecordPoller

  @Mock
  private lateinit var loader: Loader
  @Mock
  private lateinit var reporter: Reporter
  @Mock
  private lateinit var environmentVariableReader: EnvironmentVariableReader

  @BeforeEach
  internal fun setUp() {
    MockitoAnnotations.openMocks(this)
    recordPoller = RecordPoller(loader, reporter, environmentVariableReader)
    `when`(environmentVariableReader.getEnvironmentVariable("endpoints")).thenReturn(EnvironmentVariable(endpoints.joinToString()))
  }

  @Test
  fun `All endpoints should load records and report them`() {
    `when`(loader.loadAll(endpoints)).thenReturn(listOf(record))

    recordPoller.pollAndReport()

    verify(reporter).report(listOf(record))
  }

}