(ns
  ^{:author cWX205128}
  framework.worker.worker2
  (:use [framework.properties])
  )

(defn work [name]

  (loop [step 0]
    (recur
      (do

        (+ step 1)))))
