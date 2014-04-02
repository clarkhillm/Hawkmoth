(ns
  ^{:author root}
  led.fade
  (:use :reload-all clodiuno.core)
  (:use :reload-all clodiuno.firmata))

(System/setProperty "gnu.io.rxtx.SerialPorts" "/dev/ttyACM0")

(def board (arduino :firmata "/dev/ttyACM0"))

(pin-mode board 9 PWM)

(loop [x (range 100)]
  (if (= (first x) nil) nil
    (recur
      (do
        (analog-write board 9 (first x))
        (Thread/sleep 100)
        (rest x)))))

(Thread/sleep 1000)

(loop [x (reverse (range 100))]
  (if (= (first x) nil) nil
    (recur
      (do
        (analog-write board 9 (first x))
        (Thread/sleep 100)
        (rest x)))))

(close board)