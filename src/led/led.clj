(System/setProperty "gnu.io.rxtx.SerialPorts" "/dev/ttyACM0")
(ns
  ^{:author gavin}
  led.led
  (:use :reload-all clodiuno.core)
  (:use :reload-all clodiuno.firmata)
  )

(def board (arduino :firmata "/dev/ttyACM0"))

(pin-mode board 9 OUTPUT)

(loop [x (range 5)]
  (if (= (first x) nil) nil
    (recur
      (do
        (digital-write board 9 HIGH)
        (Thread/sleep 1000)
        (digital-write board 9 LOW)
        (Thread/sleep 1000)
        (rest x)))
    ))

(close board)