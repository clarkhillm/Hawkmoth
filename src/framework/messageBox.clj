(ns
  ^{:author cWX205128}
  framework.messageBox
  (:import [java.util HashMap ArrayDeque]))

(def box (HashMap.))

(defn box_init [threadName] (.put box threadName (ArrayDeque.)))

(defn sendMessage [threadName message]
  (if (nil? (.get box threadName)) (box_init threadName))
  (if (string? message) (.add (.get box threadName) message)))

(defn getMessage [threadName]
  (if (nil? (.get box threadName)) (box_init threadName))
  (if-not (.isEmpty (.get box threadName)) (.removeFirst (.get box threadName)) nil))