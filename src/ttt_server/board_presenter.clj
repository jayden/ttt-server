(ns ttt-server.board-presenter
	(:use [clojure_tictac.ttt-rules :as rules :only (game-over? game-winner winner? x o)]))

(defn get-space [position board]
	(let [space (get board position)]
		(if (= space nil)
			position
			(get board position))))

(defn get-html-space [position board]
	(cond
		(= rules/x (get-space position board))
			(str "x<input type=\"hidden\" value=\"x\" name=\"marked-space" position "\"/>")
		(= rules/o (get-space position board))
			(str "o<input type=\"hidden\" value=\"o\" name=\"marked-space" position "\"/>")
		:else
			(if (rules/game-over? board)
				"&nbsp;"
				(str "<input type=\"radio\" id=\"radio" position "\" value=\"" position "\" name=\"empty-space\"/>"
					"<label for=\"radio" position "\">&nbsp;</label>"))))

(defn get-game-conclusion [board]
	(cond 
		(rules/winner? board)
			(str "Winner is " (clojure.string/upper-case (rules/game-winner board)) "!")
		(rules/game-over? board)
			"It's a tie!"
		:else
			""))

(defn get-css-layout []
	(str 
		"<style>
		h1, h3 {
			font-family: verdana;
		}
		td {
			width: 100px;
			height: 100px;
			font-size: 40px;
			font-family: verdana;
			text-align: center;
		}

		table, th, td {
			table-layout: fixed;
			border: 1px solid black;
		}
		
		input[type=submit] {
			width: 100px;
			height: 30px;
			-webkit-border-radius: 5px;
			border-radius: 5px;
		}

		input[type=button] {
			width: 100px;
			height: 30px;
			-webkit-border-radius: 5px;
			border-radius: 5px;
		}

		input[type=radio] {
		    display:none;
		}
 
		input[type=radio] + label {
    		display:inline-block;
    		width: 100px;
    		height: 100px;
    		background-color: #e7e7e7;
    		border-color: #ddd;
		}

		input[type=hidden] {
    		display:inline-block;
    		background-color: #ffffff;
    		border-color: #ddd;
		}

		input[type=radio]:checked + label { 
   			background-image: none;
    		background-color:#858585;
		}
		</style>"))

(defn get-page-header []
	(str "Welcome to Tic-Tac-Toe!"))

(defn get-html-board [board]
	(str "<!DOCTYPE html>"
		 "<html><head><title>Jayden's Tic-Tac-Toe!</title>" (get-css-layout) "</head>"
		 "<body><center><h1>" (get-page-header) "</h1><form name=\"board\" action=\"/game\" method=\"post\">"
		 "<table>"	
		 "<tr><td>"(get-html-space 0 board)"</td><td>"(get-html-space 1 board)"</td><td>"(get-html-space 2 board)"</td></tr>"
		 "<tr><td>"(get-html-space 3 board)"</td><td>"(get-html-space 4 board)"</td><td>"(get-html-space 5 board)"</td></tr>"
		 "<tr><td>"(get-html-space 6 board)"</td><td>"(get-html-space 7 board)"</td><td>"(get-html-space 8 board)"</td></tr>"
		 "</table>"
		 "<br></br><input type=\"submit\" value=\"Move\" name=\"move\" />"
		 "<h3>" (get-game-conclusion board) "</h3>"
		 "</form></center></body></html>"))
