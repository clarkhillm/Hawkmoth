(ns fx.FirmDataTester
  (javafx.embed.swing.JFXPanel.)
  (:use :reload-all clodiuno.core)
  (:use :reload-all clodiuno.firmata)
  (:import [javafx.application Application]
           [javafx.scene Scene]
           [javafx.scene.layout VBox]
           [javafx.scene.control Button ToggleButton Slider]
           [javafx.event EventHandler]
           [javafx.beans.value ChangeListener])

  (:gen-class :extends javafx.application.Application)
  )

(System/setProperty "gnu.io.rxtx.SerialPorts" "/dev/ttyACM0")
(def board (arduino :firmata "/dev/ttyACM0"))

(def Aboutbtn (ToggleButton. "About"))
(defn logHandler []
  (proxy [EventHandler] []
    (handle [event]
      (if (.isSelected Aboutbtn)
        (do (pin-mode board 9 OUTPUT) (digital-write board 9 HIGH))
        (do (digital-write board 9 LOW) (pin-mode board 9 PWM))
        )
      )))
(.setOnAction Aboutbtn (logHandler))

(def slider (Slider. 0 100 0))
(.. slider
  (valueProperty)
  (addListener
    (proxy [ChangeListener] []
      (changed [ov n o]
        (analog-write board 9 (.intValue n))
        ))))

(defn -start [app stage]
  (def box (VBox.))
  (doto (.getChildren box)
    (.add Aboutbtn)
    (.add slider)
    )
  (def scene (Scene. box 800 600))
  (doto stage (.setScene scene)
    (.setTitle "TEST")
    (.show))
  )
(defn -stop [app]
  (close board)
  (println "Exiting now")
  )
(Application/launch fx.FirmDataTester (into-array String []))