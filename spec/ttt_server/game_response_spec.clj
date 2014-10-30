(ns ttt-server.game-response-spec
  (:use [speclj.core]
  			[ttt-server.game-response]))

(describe "game response test"
	(with test-request (java.util.HashMap.))

	(before
		(doto @test-request
			(.put "Method" "GET")
			(.put "URI" "/game")
			(.put "Protocol" "HTTP/1.1")))

	(it "returns a byte array as a response"
		(should= "Hello world!"
						(String. (.getResponse (new-game-response-handler) @test-request))))
	(it "has a content type"
		(should= "text/plain"
						(.getContentType (new-game-response-handler))))
	(it "has an OK status code"
		(should= 200
						(.getStatus (new-game-response-handler))))
)