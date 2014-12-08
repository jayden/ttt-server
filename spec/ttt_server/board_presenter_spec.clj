(ns ttt-server.board-presenter-spec
  (:use [speclj.core]
  			[ttt-server.board-presenter]))

(describe "board-presenter"
	(with x "x")
	(with o "o")
	(with test-board [@x nil nil
					  nil @o nil
					  nil nil nil])

  	(with empty-board [nil nil nil
                      nil nil nil
                      nil nil nil])

	(it "gets the spaces of the board"
		(should= @x
				(get-space 0 @test-board))
		(should= @o
				(get-space 4 @test-board))
		(should= 8
				(get-space 8 @test-board)))

	(context "html representation of spaces"
		(it "shows x's in correct spaces"
			(should= (str "x<input type=\"hidden\" value=\"x\" name=\"marked-space\"" 0 "/>")
				(get-html-space 0 @test-board)))
		(it "shows o's in correct spaces"
			(should= (str "o<input type=\"hidden\" value=\"o\" name=\"marked-space\"" 4 "/>")
				(get-html-space 4 @test-board)))
		(it "shows radio buttons for empty spaces"
			(should= (str "<input type=\"radio\" value=\"1\" name=\"empty-space\"/>")
				(get-html-space 1 @test-board))))

	(it "shows html representation of board"
		(should= 
			(str "<html><body><center><form name=\"board\" action=\"/game\" method=\"post\">"
			 "<table>"	
			 "<tr><td>"(get-html-space 0 @test-board)"</td><td>"(get-html-space 1 @test-board)"</td><td>"(get-html-space 2 @test-board)"</td></tr>"
			 "<tr><td>"(get-html-space 3 @test-board)"</td><td>"(get-html-space 4 @test-board)"</td><td>"(get-html-space 5 @test-board)"</td></tr>"
			 "<tr><td>"(get-html-space 6 @test-board)"</td><td>"(get-html-space 7 @test-board)"</td><td>"(get-html-space 8 @test-board)"</td></tr>"
			 "</table>"
			 "<input type=\"submit\" value=\"Move\" name=\"move\" /></form></center></body></html>")
			(get-html-board @test-board))))
