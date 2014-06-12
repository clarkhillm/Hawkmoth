(ns
  ^{:author cWX205128}
  framework.launch
  (:use [framework.messageCore])
  (:use [framework.messageBox])
  (:use [framework.managedResoreces])
  )

(sendMessage "worker1" "0")
(sendMessage "worker1" "1")

(.start (first @rigistedThread))
