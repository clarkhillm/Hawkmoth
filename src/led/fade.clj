(ns
  ^{:author root}
  led.fade
  (:use :reload-all clodiuno.core)
  (:use :reload-all clodiuno.firmata))

(System/setProperty "gnu.io.rxtx.SerialPorts" "/dev/ttyACM0")

(def board (arduino :firmata "/dev/ttyACM0"))

(pin-mode board 9 OUTPUT)

(loop [x (range 255)]
  (if (= (first x) nil) nil
    (recur
      (do
        (analog-write (first x) 9 PWM)
        (Thread/sleep 1000)
        (rest x)))))
(close board)