(ns
  ^{:author cWX205128}
  framework.messageBox
  (:use framework.managedResoreces)
  (:import [java.util HashMap ArrayDeque]))

(def box (HashMap.))

(defn box_init [threadName] (.put box threadName (ArrayDeque.)))

(defn sendMessage [threadName message]
  (if (nil? (.get box threadName)) (box_init threadName))
  (if (string? message) (.add (.get box threadName) message)) (blocking_clear watcherName))

(defn getMessage [threadName]
  (if (nil? (.get box threadName)) (box_init threadName))
  (if-not (.isEmpty (.get box threadName))
    (do
      (def value (.removeFirst (.get box threadName)))
      (blocking_clear watcherName)
      value) nil))

(def messageAttachment (HashMap.))

(defn getAttachment [key]
  (def attachment (.get messageAttachment key)) (.remove key) attachment)

(defn setAttachment [attachment]
  (.put messageAttachment (.hashCode attachment) attachment) (.hashCode attachment))
