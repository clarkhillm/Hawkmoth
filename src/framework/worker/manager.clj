(ns
  ^{:author cWX205128}
  framework.worker.manager
  (:use [framework.managedResoreces])
  )

(defn work [name]
  (while true
    (println "#")
    (Thread/sleep 500)
    ))
