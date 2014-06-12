(ns
  ^{:author cWX205128}
  framework.worker.worker1
  (:use [framework.messageBox])
  )

(defn work [name]
  (loop [step 0]
    (recur
      (do
        (println "thread name: " name "stpe: " step)
        (println "message box: " @box)
        (println "message: " (getMessage name))
        (Thread/sleep 500)
        (+ step 1)))))
