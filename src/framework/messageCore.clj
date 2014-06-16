(ns
  ^{:author cWX205128}
  framework.messageCore
  (:import [java.io File])
  (:use [framework.properties])
  (:use [framework.messageBox])
  (:use [framework.managedResoreces])
  )

(def workerSeq (atom ()))
(let [worker_path (File. (:worker_path properties))]
  (do
    (def worker_seq (seq (.list worker_path)))
    (loop [x worker_seq]
      (if
        (nil? (first x))
        nil
        (recur
          (do
            (def keyOfWorker (first (.split (first x) "[.]")))
            (swap! workerSeq concat [keyOfWorker])
            (eval
              (read-string
                (str "(require '[framework.worker." keyOfWorker " :as " keyOfWorker "])")))
            (rest x)))))))

(loop [x @workerSeq]
  (if (nil? (first x))
    nil
    (recur
      (do
        (def rThread
          (eval
            (read-string
              (str "(Thread. (proxy [Runnable][] (run [] ("
                (first x) "/work \""
                (first x) "\"))) \"" (first x) "\")"))))
        (rigistThread rThread)
        (initBlockQueneMap (first x))
        (box_init (first x))
        (rest x)))))

(loop [key (keys @blockingQueneMap)]
  (if (nil? (first key))
    nil
    (recur
      (do
        (.put ((first key) @blockingQueneMap) (String.))
        (rest key)))))

(add-watch
  box
  "watch1"
  (fn [key identity old-val new-val]
    (println new-val)
    (loop [key (keys new-val)]
      (if
        (nil? (first key))
        nil
        (recur
          (do
            (if-not
              (empty?
                ((first key) @box))
              (if-not (nil? ((first key) @blockingQueneMap))
                (.clear ((first key) @blockingQueneMap)))
              )
            (rest key)))))))

(.start (first (filter (fn [x] (= "manager" (.getName x))) @rigistedThread)))

;(.start (Thread. (proxy [Runnable] [] (run [] (while true (println @box) (Thread/sleep 500))))))

