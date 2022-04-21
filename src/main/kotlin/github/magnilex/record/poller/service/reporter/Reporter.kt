package github.magnilex.record.poller.service.reporter

import github.magnilex.record.poller.model.Record

interface Reporter {
  fun report(records: List<Record>)
}
