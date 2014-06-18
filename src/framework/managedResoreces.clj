(ns
  ^{:author cWX205128}
  framework.managedResoreces
  (:import [java.util.concurrent ArrayBlockingQueue]))

(def rigistedThread (atom ()))
(defn rigistThread [thread] (swap! rigistedThread concat [thread]))

(def blockingQueneMap (atom {}))
(defn initBlockQueneMap [threadName]
  (swap! blockingQueneMap assoc (keyword threadName) (ArrayBlockingQueue. 1)))
(defn blocking_put [threadName] (.put ((keyword threadName) @blockingQueneMap) ""))
(defn blocking_clear [threadName] (.clear ((keyword threadName) @blockingQueneMap)))

(def watcherName "messageWatcher")
