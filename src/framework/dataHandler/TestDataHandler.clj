(ns
  ^{:author cWX205128}
  framework.dataHandler.TestDataHandler
  (:import [java.io PipedWriter BufferedReader PipedReader BufferedWriter]))

(def testWriter (PipedWriter.))

(defn work [^PipedWriter writer]
  (def reader (BufferedReader. (PipedReader. writer)))
  (while true
    (println (.readLine reader))
    (Thread/sleep 500)))

(.start (Thread. (proxy [Runnable] [] (run [] (work testWriter)))))

(.start
  (Thread.
    (proxy [Runnable] []
      (run []
        (def writer (BufferedWriter. testWriter))
        (loop [step 0]
          (recur
            (do
              (def data (str "test " step))
              (doto writer
                (.write data 0 (count data))
                (.newLine))
              (+ step 1))))))))
