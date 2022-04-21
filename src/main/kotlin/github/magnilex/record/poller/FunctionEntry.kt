package github.magnilex.record.poller

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import github.magnilex.record.poller.service.EnvironmentVariableReader
import github.magnilex.record.poller.service.Loader
import github.magnilex.record.poller.service.RecordPoller
import github.magnilex.record.poller.service.reporter.spreadsheet.DefaultSpreadSheetClientSupplier
import github.magnilex.record.poller.service.reporter.spreadsheet.SpreadsheetReporter
import java.net.HttpURLConnection

class FunctionEntry : HttpFunction {

  override fun service(request: HttpRequest, response: HttpResponse) {
    pollAndReport()
    response.setStatusCode(HttpURLConnection.HTTP_OK)
  }

  fun pollAndReport() {
    RecordPoller(
            Loader(),
            SpreadsheetReporter(EnvironmentVariableReader(), DefaultSpreadSheetClientSupplier()),
            EnvironmentVariableReader()).pollAndReport()
  }

}