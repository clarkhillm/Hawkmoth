(ns
  ^{:author cWX205128}
  framework.worker.manager
  (:use [framework.managedResoreces])
  (:use [framework.messageBox]))

(defn work [name]
  (while true
    (let [m (getMessage name)]
      (if-not (nil? m)
        (do
          (def mm (load-string m))
          (sendMessage (:from mm)
            (str
              {:from name
               :message (case (:message mm)
                          "hello" "hello I an manager thread."
                          "threads"
                          (case (:command mm)
                            "watch" (prn-str
                                 (map
                                   (fn [x]
                                     {:name (.getName x),:state (.name (.getState x))})
                                   @rigistedThread))
                            "start"
                            "need command"
                            )
                          "unknow message")})))
        (blocking_put name)))))
