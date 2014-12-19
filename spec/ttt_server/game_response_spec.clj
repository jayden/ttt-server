(ns ttt-server.game-response-spec
  (:use [speclj.core]
  		[ttt-server.game-response]))
	(with test-request (java.util.HashMap.))
	(with post-request (java.util.HashMap.))
	(with test-board ["x" nil nil
					  nil nil nil
					  nil nil nil])
	(before
		(doto @post-request
			(.put "Method" "POST")
			(.put "URI" "/game")
			(.put "Protocol" "HTTP/1.1")
			(.put "Body" "marked-space0=x&marked-space4=o&move=Move")))

	(it "has a content type"
		(should= "text/html"
			(.getContentType (new-game-response-handler))))
	
	(it "has an OK status code"
		(should= 200
			(.getStatus (new-game-response-handler))))

	(it "splits the body of the post response"
		(should= {"marked-space0" "x", "marked-space4" "o", "move" "Move"}
			(parse-body @post-request)))

	(it "gets current board with previous moves"
		(should= ["x" nil nil nil "o" nil nil nil nil]
			(get-current-board @post-request)))

	(it "gets updated board with new user input move"
		(should= ["x" nil nil nil "o" nil nil nil nil]
			(get-updated-board @post-request))
		(let [updated-request (doto @post-request (.put "Body" "marked-space0=x&marked-space4=o&empty-space=1&move=Move"))]
			(should= ["x" "x" nil nil "o" nil nil nil nil]
				(get-updated-board updated-request))))

	(it "lets the computer move"
		(should= ["x" nil nil nil "o" nil nil nil nil]
			(make-move @test-board)))