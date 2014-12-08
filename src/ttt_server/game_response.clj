(ns ttt-server.game-response
	(:use [ttt-server.board-presenter]
		  [clojure_tictac.players]
		  [clojure_tictac.board]
		  [clojure_tictac.game-setup :only (default-board-size)]))

(defn new-game-response-handler []
	(reify
		com.jayden.server.Response

		(getResponse [this request]
			(.getBytes (get-html-board (make-board 9))))

		(getContentType [this] "text/html")

		(getStatus [this] 200)))

(defn make-move [board marker]
	(best-move board marker))

(defn split-body [body]
	(clojure.string/split body #"&"))

(defn parse-body [request]
	(let [parsed-body (map #(clojure.string/split % #"=") (split-body (.get request "Body")))]
		(into {} parsed-body)))

(defn get-current-board [request]
	(loop [space (dec default-board-size)
			board {}]
		(if (= -1 space)
			board
			(recur
				(dec space)
				(if (= nil (get (parse-body request) (str "marked-space" space)))
					board
					(assoc board space (get (parse-body request) (str "marked-space" space))))))))

(defn get-updated-board [request]
	(let [board (get-current-board request)
		  move (get (parse-body request) "empty-space")]
		(if (= nil move)
			board
			(assoc board (Integer/parseInt move) "x"))))

