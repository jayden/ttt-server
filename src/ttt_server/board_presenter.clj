(ns ttt-server.board-presenter
	(:use [clojure_tictac.ttt-rules :only (game-over? game-winner winner? x o)]))

(defn get-space [position board]
	(let [space (get board position)]
		(if (= space nil)
			position
			(get board position))))

(defn get-html-space [position board]
	(cond
		(= x (get-space position board))
			(str "x<input type=\"hidden\" value=\"x\" name=\"marked-space" position "\"/>")
		(= o (get-space position board))
			(str "o<input type=\"hidden\" value=\"o\" name=\"marked-space" position "\"/>")
		:else
			(if (game-over? board)
				"&nbsp;"
				(str "<input type=\"radio\" value=\"" position "\" name=\"empty-space\"/>"))))

(defn get-game-conclusion [board]
	(cond 
		(winner? board)
			(str "Winner is " (clojure.string/upper-case (game-winner board)) "!")
		(game-over? board)
			"It's a tie!"
		:else
			""))

(defn get-html-board [board]
	(str "<html><body><center><form name=\"board\" action=\"/game\" method=\"post\">"
		 "<table>"	
		 "<tr><td>"(get-html-space 0 board)"</td><td>"(get-html-space 1 board)"</td><td>"(get-html-space 2 board)"</td></tr>"
		 "<tr><td>"(get-html-space 3 board)"</td><td>"(get-html-space 4 board)"</td><td>"(get-html-space 5 board)"</td></tr>"
		 "<tr><td>"(get-html-space 6 board)"</td><td>"(get-html-space 7 board)"</td><td>"(get-html-space 8 board)"</td></tr>"
		 "</table>"
		 "<input type=\"submit\" value=\"Move\" name=\"move\" />"
		 "<h5>" (get-game-conclusion board) "</h5>"
		 "</form></center></body></html>"))
