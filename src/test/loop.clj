(ns
  ^{:author root}
  test.loop)

(loop [x (range 255)]
  (if (= (first x) nil) nil
    (recur (do
             (println (first x))(Thread/sleep 1000)
             (rest x)))
    ))