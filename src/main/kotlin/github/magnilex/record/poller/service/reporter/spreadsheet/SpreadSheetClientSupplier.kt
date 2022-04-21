package github.magnilex.record.poller.service.reporter.spreadsheet

import com.google.api.services.sheets.v4.Sheets

interface SpreadSheetClientSupplier {
  fun getClient(): Sheets
}
