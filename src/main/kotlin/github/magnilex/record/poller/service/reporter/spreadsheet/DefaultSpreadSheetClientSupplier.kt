package github.magnilex.record.poller.service.reporter.spreadsheet

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import java.util.function.Supplier

class DefaultSpreadSheetClientSupplier : Supplier<Sheets> {
  override fun get(): Sheets {
    return Sheets.Builder(
      GoogleNetHttpTransport.newTrustedTransport(),
      GsonFactory.getDefaultInstance(),
      HttpCredentialsAdapter(GoogleCredentials.getApplicationDefault())
    ).build()
  }
}