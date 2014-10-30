(ns ttt-server.index-response-spec
  (:use [speclj.core]
  			[ttt-server.index-response]))

(describe "game response test"
	(with test-request (java.util.HashMap.))

	(before
		(doto @test-request
			(.put "Method" "GET")
			(.put "URI" "/")
			(.put "Protocol" "HTTP/1.1")))

	(it "returns a byte array as a response"
		(should= "redirecting to game!"
						(String. (.getResponse (new-index-response-handler) @test-request))))
	(it "has a content type"
		(should= "text/plain"
						(.getContentType (new-index-response-handler))))
	(it "has a redirect status code"
		(should= 307
						(.getStatus (new-index-response-handler))))
	(it "has a location header"
		(should= "Location"
						(.getHeader (new-index-response-handler))))
	(it "has a redirect location value"
		(should= "http://localhost:5000/game"
						(.getHeaderValue (new-index-response-handler))))
)