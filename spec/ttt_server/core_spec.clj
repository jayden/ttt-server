(ns ttt-server.core-spec
  (:use [speclj.core]
  			[ttt-server.core])
  (:import com.jayden.server.Server))

(describe "server core"
	(it "adds routes"
		(should= true
						(let [server (Server. 5000)]
							(add-routes server)
							(.containsKey (get-routes server) "/game"))))
	(it "isn't initially active"
		(let [server (Server. 5000)]
			(should= false (.isActive server))))

	(it "starts and stops the server"
		(let [server (Server. 5000)]
			(start server)
			(Thread/sleep 1000)
			(should= true (.isActive server))
			(stop server)
			(should= false (.isActive server))))
)