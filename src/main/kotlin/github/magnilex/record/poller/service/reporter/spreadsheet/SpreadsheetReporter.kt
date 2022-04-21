package github.magnilex.record.poller.service.reporter.spreadsheet

import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import github.magnilex.record.poller.model.Record
import github.magnilex.record.poller.service.EnvironmentVariableReader
import github.magnilex.record.poller.service.reporter.Reporter

class SpreadsheetReporter(
        private val environmentVariableReader: EnvironmentVariableReader,
        private val spreadSheetClientSupplier: SpreadSheetClientSupplier) : Reporter {
  override fun report(records: List<Record>) {
    write(records, spreadSheetClientSupplier.getClient())
  }

  private fun write(records: List<Record>, client: Sheets) {
    val spreadsheetId = environmentVariableReader.getEnvironmentVariable("spreadSheetId").variable
    println("Writing ${records.size} records to spreadsheet $spreadsheetId")
    write(client, spreadsheetId, ValueRange().setValues(records.map { it.fieldsAsList() }))
  }

  fun write(client: Sheets, spreadsheetId: String, payload: ValueRange) {
    client.spreadsheets().values().update(spreadsheetId, "A1", payload).setValueInputOption("RAW").execute()
  }
}
