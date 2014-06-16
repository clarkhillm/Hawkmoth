(ns
  ^{:author cWX205128}
  framework.messageBox)

(def box (agent {}))

(defn box_init [threadName] (send box assoc (keyword threadName) ()))

(defn sendMessage [threadName message]
  (let [messageQueue ((keyword threadName) @box)]
    (if (string? message)
      (send box assoc (keyword threadName) (concat messageQueue [message])))))

(defn getMessage [threadName]
  (let [messageQueue ((keyword threadName) @box)]
    (if-not (empty? (first messageQueue))
      (send box assoc (keyword threadName) (rest messageQueue)))
    (first messageQueue)))
