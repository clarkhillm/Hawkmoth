(System/setProperty "gnu.io.rxtx.SerialPorts" "/dev/ttyACM0")
(ns
  ^{:author gavin}
  led.led
  (:use :reload-all clodiuno.core)
  (:use :reload-all clodiuno.firmata)
  )

(def board (arduino :firmata "/dev/ttyACM0"))

(pin-mode board 12 OUTPUT)

(doseq [_ (range 5)]
  (digital-write board 12 HIGH)
  (Thread/sleep 1000)
  (digital-write board 12 LOW)
  (Thread/sleep 1000))

(close board)