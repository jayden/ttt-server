(ns ttt-server.game-response)

(defn new-response-handler []
	(reify
		com.jayden.server.Response

		(getResponse [this request]
			(.getBytes "Hello world!"))

		(getContentType [this] "text/plain")

		(getStatus [this] 200)))