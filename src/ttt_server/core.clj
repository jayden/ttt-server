(ns ttt-server.core
	(:gen-class)
	(:use [ttt-server.game-response]
				[ttt-server.index-response])
	(:import com.jayden.server.Server))
	
(defn add-routes [server] 
	(.addRoute server "/" (new-index-response-handler))
	(.addRoute server "/game" (new-game-response-handler)))

(defn get-routes [server]
	(.getRoutes server))

(defn start [server]
	(add-routes server)
	(.start server))

(defn stop [server]
	(doto
		server
		(.stopServer)))

(defn -main [& args]
	(start (Server. 5000)))