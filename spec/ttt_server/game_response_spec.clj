(ns ttt-server.game-response-spec
  (:use [speclj.core]
  		[ttt-server.game-response]))

(describe "game response test"
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
	(it "updates board by choosing move"
		(should= 4
			(make-move @test-board "o")))

	(it "splits the body of the post response"
		(should= {"marked-space0" "x", "marked-space4" "o", "move" "Move"}
			(parse-body @post-request)))

	(it "gets current board with previous moves"
		(should= {0 "x", 4 "o"}
			(get-current-board @post-request)))

	(it "gets updated board with new user input move"
		(should= {0 "x", 4 "o"}
			(get-updated-board @post-request))
		(let [updated-request (doto @post-request (.put "Body" "marked-space0=x&marked-space4=o&empty-space=1&move=Move"))]
			(should= {0 "x", 1 "x", 4 "o"}
				(get-updated-board updated-request)))))
