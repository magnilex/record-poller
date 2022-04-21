package github.magnilex.record.poller.service.reporter.spreadsheet

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.json.gson.GsonFactory.getDefaultInstance
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes.SPREADSHEETS
import com.google.api.services.sheets.v4.model.ValueRange
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import github.magnilex.record.poller.model.record
import github.magnilex.record.poller.service.EnvironmentVariableReader
import github.magnilex.record.poller.service.EnvironmentVariableReader.EnvironmentVariable
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify

private const val SPREAD_SHEET_ID = "spreadSheetId"

internal class SpreadsheetReporterTest {

  @Mock
  private lateinit var environmentVariableReader: EnvironmentVariableReader
  @Mock
  private lateinit var spreadSheetClientSupplier: SpreadSheetClientSupplier
  private lateinit var client: Sheets

  private lateinit var spreadsheetReporter: SpreadsheetReporter

  @BeforeEach
  internal fun setUp() {
    MockitoAnnotations.openMocks(this)
    spreadsheetReporter = spy(SpreadsheetReporter(environmentVariableReader, spreadSheetClientSupplier))

    `when`(environmentVariableReader.getEnvironmentVariable(SPREAD_SHEET_ID)).thenReturn(EnvironmentVariable(SPREAD_SHEET_ID))

    client = spy(Sheets.Builder(
            mock(HttpTransport::class.java),
            mock(GsonFactory::class.java),
            mock(HttpRequestInitializer::class.java)))
            .setApplicationName("Unit test")
            .build()
    `when`(spreadSheetClientSupplier.getClient()).thenReturn(client)

    doNothing().`when`(spreadsheetReporter).write(any(), any(), any())
  }

  @Test
  fun test() {
    spreadsheetReporter.report(listOf(record))

    val captor = argumentCaptor<ValueRange>()
    verify(spreadsheetReporter).write(eq(client), eq(SPREAD_SHEET_ID), captor.capture())
    captor.allValues shouldHaveSize 1
    captor.firstValue.getValues() shouldHaveSize 1
    captor.firstValue.getValues()[0] shouldContainExactly record.fieldsAsList()
  }

}

/**
 * Can be used to write a list of records directly to a spreadsheet. Requires that "credentials" (a json credential
 * file for a GCP service account) and "spreadSheetId" (the id of a Google spreadsheet, can be found in the url).
 */
fun main() {
  SpreadsheetReporter(EnvironmentVariableReader(), CredentialJsonSpreadSheetClientSupplier()).report(listOf(record));
}

class CredentialJsonSpreadSheetClientSupplier : SpreadSheetClientSupplier {
  override fun getClient(): Sheets {
    val credentials = GoogleCredentials.fromStream(System.getenv("credentials").byteInputStream()).createScoped(listOf(SPREADSHEETS))
    return Sheets.Builder(newTrustedTransport(), getDefaultInstance(), HttpCredentialsAdapter(credentials))
            .setApplicationName("Test reporter")
            .build()
  }

}
