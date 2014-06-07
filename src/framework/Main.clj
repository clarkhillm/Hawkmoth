(ns
  ^{:author gavin}
  framework.Main)

(def manager
  (Thread.
    (proxy [Runnable] []
      (run []
        (while true
          (do
            (println ".")
            (Thread/sleep 500)
            ))
        ))))

(.start manager)