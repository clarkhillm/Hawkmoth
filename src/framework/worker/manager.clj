(ns
  ^{:author cWX205128}
  framework.worker.manager
  (:use [framework.managedResoreces])
  (:use [framework.messageBox]))

(defn work [name]
  (while true
    (Thread/sleep 100)
    (let [m (getMessage name)]
      (if-not (nil? m)
        (do
          (def mm (load-string m))
          (sendMessage (:from mm)
            (str
              {:from name
               :message (case (:message mm)
                          "hello" "hello I an manager thread."
                          "watch threads" (str [@rigistedThread])
                          "unknow message.")})))
        (.put ((keyword name) @blockingQueneMap) "")
        ))))
