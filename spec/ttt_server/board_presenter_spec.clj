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

	(it "gets html representation of board"
		(should= "<html><form name=\"board\" action=\"/game\" method=\"post\"><table><tr><td>x</td><td>1</td><td>2</td></tr><tr><td>3</td><td>o</td><td>5</td></tr><tr><td>6</td><td>7</td><td>8</td></tr></table><input type=\"text\" name=\"move-selection\" value=\"\"><input type=\"submit\" value=\"Move\" name=\"move\" /></form></html>"
			(get-html-board @test-board)))

	(it "gets html representation with text boxes"
		(should= (str "<input type=\"text\" name=" 0 " value=" @x " style=\"height: 15px;width: 15px;margin: -1px\" />")
			(get-html-space 0 @test-board))))
