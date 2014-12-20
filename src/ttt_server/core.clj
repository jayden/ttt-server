(ns ttt-server.core
	(:gen-class)
	(:use [ttt-server.game-response :as game]
		  [ttt-server.index-response :as idx])
	(:import com.jayden.server.Server))

(defn add-routes [server] 
	(.addRoute server "/" (idx/new-index-response-handler))
	(.addRoute server "/game" (game/new-game-response-handler)))

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
