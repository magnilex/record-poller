package github.magnilex.record.poller.model

import kotlinx.serialization.Serializable

@Serializable
data class Record(
        val artist: String,
        val title: String,
        val price: Int,
        val id: Int,
        val type: String) {

  fun fieldsAsList(): List<Any> {
    return listOf(artist, title, price, id, type)
  }

}