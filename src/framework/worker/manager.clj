(ns
  ^{:author cWX205128}
  framework.worker.manager
  (:use [framework.managedResoreces])
  (:use [framework.messageBox])
  )

(defn work [name]
  (while true
    (println @box)
    (Thread/sleep 500)
    (let [m (getMessage name)]
      (if-not (nil? m)
        (do
          (println "manager message: " m)
          (def mm (load-string m))
          (case (:message mm)
            "hello" (sendMessage (:from mm) (str {:from name :message "hello I an manager thread."}))
            ""
            )
          )
        )
      )))
