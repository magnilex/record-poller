package github.magnilex.record.poller.service

class EnvironmentVariableReader {
  fun getEnvironmentVariable(name: String): EnvironmentVariable {
    return EnvironmentVariable(System.getenv(name))
  }

  class EnvironmentVariable(val variable: String) {
    fun fromCommaSeparated(): List<String> {
      return variable.split(",").map { it.trim() }
    }
  }
}

