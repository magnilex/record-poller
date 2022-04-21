package github.magnilex.record.poller

import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.HttpURLConnection

class FunctionEntryTest {

  private lateinit var functionEntry: FunctionEntry

  @BeforeEach
  internal fun setUp() {
    functionEntry = spy(FunctionEntry::class.java)
    doNothing().`when`(functionEntry).pollAndReport()
  }

  @Test
  fun `Calling function entry should poll and report and return OK`() {
    val response: HttpResponse = spy(TestHttpResponse::class.java)

    functionEntry.service(mock(HttpRequest::class.java), response)

    verify(functionEntry).pollAndReport()
    verify(response).setStatusCode(HttpURLConnection.HTTP_OK)
  }

  private abstract class TestHttpResponse : HttpResponse {
    override fun getWriter(): BufferedWriter {
      return BufferedWriter(OutputStreamWriter(System.out))
    }
  }

}