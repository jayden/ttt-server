(ns ttt-server.game-response
	(:use [ttt-server.board-presenter :as presenter]
		  [clojure_tictac.players :as players :only (best-move)]
		  [clojure_tictac.board :as board]
		  [clojure_tictac.ttt-rules :as rules :only (game-over? x o)]
		  [clojure_tictac.game-setup :as setup :only (default-board-size)]))

(defn split-body [body]
	(clojure.string/split body #"&"))

(defn parse-body [request]
	(if (= 0 (count (.get request "Body")))
		(hash-map)
		(let [parsed-body (map #(clojure.string/split % #"=") (split-body (.get request "Body")))]
			(into {} parsed-body))))

(defn get-current-board [request]
	(loop [space (dec setup/default-board-size)
			board (board/make-board setup/default-board-size)]
		(if (= -1 space)
			board
			(recur
				(dec space)
				(if (= nil (get (parse-body request) (str "marked-space" space)))
					board
					(assoc board space (get (parse-body request) (str "marked-space" space))))))))

(defn current-player [board]
	(if (even? (count (filter #(not= nil %) board)))
		rules/x
		rules/o))

(defn get-updated-board [request]
	(let [board (get-current-board request)
		  move (get (parse-body request) "empty-space")]
		(if (= nil move)
			board
			(assoc board (Integer/parseInt move) (current-player board)))))


(defn make-move [board]
	(if (and (not= true (rules/game-over? board)) (= rules/o (current-player board)))
		(assoc board (players/best-move board rules/o) rules/o)
		board))

(defn new-game-response-handler []
	(reify
		com.jayden.server.Response

		(getResponse [this request]
			(.getBytes (get-html-board (make-move (get-updated-board request)))))

		(getContentType [this] "text/html")

		(getStatus [this] 200)))