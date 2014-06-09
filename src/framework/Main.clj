(ns
  ^{:author cWX205128}
  framework.Main
  (:import [java.io File])
  (:use [framework.properties])
  (:use [framework.messageBox])
  )

(def workerSeq (atom ()))
(def rigistedThread (atom ()))

(let [worker_path (File. (:worker_path properties))]
  (do
    (def worker_seq (seq (.list worker_path)))
    (loop [x worker_seq]
      (if (nil? (first x)) nil
        (recur
          (do
            (def keyOfWorker (first (.split (first x) "[.]")))
            (swap! workerSeq concat [keyOfWorker])
            (eval
              (read-string
                (str "(require '[framework.worker." keyOfWorker " :as " keyOfWorker "])")))
            (rest x)))))))

(loop [x @workerSeq]
  (if (nil? (first x)) nil
    (recur
      (do
        (def rThread
          (eval
            (read-string
              (str "(Thread. (proxy [Runnable][] (run [] (" (first x) "/work \"" (first x) "\"))) \"" (first x) "\")"))))
        (swap! rigistedThread concat [rThread])
        (send box assoc (first x) nil);init message
        (rest x)))))

(def manager
  "管理线程，目前职责还不是很清楚，大体上他应该监控注册的进程情况"
  (Thread.
    (proxy [Runnable] []
      (run []
        (while true
          (println ".")
          (Thread/sleep 500)
          )
        )))) ;(.start manager)
