(ns ttt-server.index-response)

(defn new-index-response-handler []
	(reify
		com.jayden.server.ResponseWithHeader

		(getResponse [this request]
			(.getBytes "redirecting to game!"))

		(getContentType [this] "text/plain")

		(getStatus [this] 307)

		(getHeader [this] "Location")

		(getHeaderValue [this] "http://localhost:5000/game")))