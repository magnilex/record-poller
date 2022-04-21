package github.magnilex.record.poller.model

import io.kotest.matchers.collections.shouldContainExactly
import org.junit.jupiter.api.Test

internal class RecordTest {

  @Test
  fun `All field values should be returned in correct order`() {
    record.fieldsAsList() shouldContainExactly listOf(record.artist, record.title, record.price, record.id, record.type)
  }

}