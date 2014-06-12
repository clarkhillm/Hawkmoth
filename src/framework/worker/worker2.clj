(ns
  ^{:author cWX205128}
  framework.worker.worker2
  (:use [framework.properties])
  )

(defn work [name]
  (def writer (BufferedWriter. testWriter))
  (loop [step 0]
    (recur
      (do
        (def data (str "test " step))
        (doto writer
          (.write data 0 (count data))
          (.newLine))
        (+ step 1)))))
