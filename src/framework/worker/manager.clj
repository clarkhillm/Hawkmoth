(ns
  ^{:author cWX205128}
  framework.worker.manager
  (:use [framework.managedResoreces])
  (:use [framework.messageBox]))

(defn work [name]
  (while true
    (Thread/sleep 10)
    (let [m (getMessage name)]
      (if-not (nil? m)
        (do
          (println "manager_message: " m)
          (def mm (load-string m))
          (case (:message mm)
            "hello"
            (sendMessage (:from mm) (str {:from name :message "hello I an manager thread."}))
            "watch box"
            (sendMessage (:from mm) (str {:from name :message @box}))
            ""
            ))))))
