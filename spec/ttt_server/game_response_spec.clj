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
		(doto @test-request
			(.put "Method" "GET")
			(.put "URI" "/game")
			(.put "Protocol" "HTTP/1.1")))

	(before
		(doto @post-request
			(.put "Method" "POST")
			(.put "URI" "/game")
			(.put "Protocol" "HTTP/1.1")
			(.put "Body" "move-selection=1&move=Move")))

	(it "has a content type"
		(should= "text/html"
						(.getContentType (new-game-response-handler))))
	(it "has an OK status code"
		(should= 200
						(.getStatus (new-game-response-handler))))
	(it "updates board by choosing move"
		(should= 4
			(make-move @test-board "o")))
	(it "gets the body of post response"
		(should= "move-selection=1&move=Move"
			(String. (get-body @post-request)))))
