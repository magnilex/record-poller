package github.magnilex.record.poller.model

const val recordAsJson =
        """
        [{
            "artist": "Carlberger Ann",
            "title": "Hidden treasures",
            "price": 10,
            "id": 15457,
            "type": "Vinyl LP"
        }]
        """

val record = Record("Carlberger Ann", "Hidden treasures", 10, 15457, "Vinyl LP")