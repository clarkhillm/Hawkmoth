(ns
  ^{:author cWX205128}
  framework.test.ThreadTest
  (:import [java.util.concurrent BlockingQueue ArrayBlockingQueue]))

(def blockQuene (ArrayBlockingQueue. 1))
(.put blockQuene "x")
(def t (Thread. (proxy [Runnable] [] (run [] (while true (println (.getState t))(.put blockQuene ""))))))

(println (.getState t))
(.start t)
(Thread/sleep 100)
(println (.getState t))
(.clear blockQuene)
(Thread/sleep 100)
(println (.getState t))
(Thread/sleep 100)
(.put blockQuene "x")
(println (.getState t))
