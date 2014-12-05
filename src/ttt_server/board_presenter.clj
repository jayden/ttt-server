(ns ttt-server.board-presenter
	(:use [clojure_tictac.board]
				[clojure_tictac.console]))

(defn get-space [position board]
	(let [space (get board position)]
		(if (= space nil)
			position
			(get board position))))

(defn get-html-board [board]
	(str "<html><form name=\"board\" action=\"/game\" method=\"post\">"
		 "<table>"	
		 "<tr><td>"(get-space 0 board)"</td><td>"(get-space 1 board)"</td><td>"(get-space 2 board)"</td></tr>"
		 "<tr><td>"(get-space 3 board)"</td><td>"(get-space 4 board)"</td><td>"(get-space 5 board)"</td></tr>"
		 "<tr><td>"(get-space 6 board)"</td><td>"(get-space 7 board)"</td><td>"(get-space 8 board)"</td></tr>"
		 "</table>"
		 "<input type=\"text\" name=\"move-selection\" value=\"\">"
		 "<input type=\"submit\" value=\"Move\" name=\"move\" /></form></html>"))

(defn get-html-space [position board]
	(str "<input type=\"text\" name=" position " value=" (get-space position board) " style=\"height: 15px;width: 15px;margin: -1px\" />"))