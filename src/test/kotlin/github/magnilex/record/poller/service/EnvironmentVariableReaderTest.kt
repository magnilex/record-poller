package github.magnilex.record.poller.service

import github.magnilex.record.poller.service.EnvironmentVariableReader.EnvironmentVariable
import io.kotest.matchers.collections.shouldContainExactly
import org.junit.jupiter.api.Test

internal class EnvironmentVariableReaderTest {

  private val variable1: String = "variable1"
  private val variable2: String = "variable2"

  @Test
  fun `Comma separated variables should be returned as separate variables`() {
    val variables = EnvironmentVariable("${variable1},${variable2}").fromCommaSeparated()

    variables shouldContainExactly listOf(variable1, variable2)
  }

}

