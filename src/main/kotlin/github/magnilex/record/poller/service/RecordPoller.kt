package github.magnilex.record.poller.service

import github.magnilex.record.poller.service.reporter.Reporter

class RecordPoller(private val loader: Loader, private val reporter: Reporter, private val environmentVariableReader: EnvironmentVariableReader) {
  fun pollAndReport() {
    val endpoints = environmentVariableReader.getEnvironmentVariable("endpoints").fromCommaSeparated()
    reporter.report(loader.loadAll(endpoints))
  }
}