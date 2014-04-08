(ns
  ^{:author gavin}
  MPU.Test
  (:use :reload-all clodiuno.core)
  (:use :reload-all clodiuno.firmata)
  )

(System/setProperty "gnu.io.rxtx.SerialPorts" "/dev/ttyACM0")

(def board (arduino :firmata "/dev/ttyACM0"))

(i2c-ini board)
(def address 0x68)



(close board)