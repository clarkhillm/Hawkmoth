(ns
  ^{:author cWX205128}
  framework.worker.worker2)

(defn work [name]
  (while true
    (println "#")
    (Thread/sleep 500)
    ))
