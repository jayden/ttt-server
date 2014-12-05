(ns ttt-server.game-response
	(:use [ttt-server.board-presenter]
		  [clojure_tictac.players]
		  [clojure_tictac.board]))

(defn new-game-response-handler []
	(reify
		com.jayden.server.Response

		(getResponse [this request]
			(if (.get request )
			(.getBytes (get-html-board (make-board 9)))))

		(getContentType [this] "text/html")

		(getStatus [this] 200)))

(defn make-move [board marker]
	(best-move board marker))

(defn get-body [request]
	(.get request "Body"))