(ns
  ^{:author l00150585}
  threeD.ThreeD
  (javafx.embed.swing.JFXPanel.)
  (:import [javax.swing JFrame SwingUtilities]
           [javafx.embed.swing JFXPanel]
           [javafx.application Platform]
           [javafx.scene.layout BorderPane HBox VBox]
           [javafx.scene Scene PerspectiveCamera Group SubScene]
           [javafx.scene.control Button Slider Label]
           [javafx.beans.binding NumberBinding]
           [javafx.beans.value ChangeListener]
           [javafx.scene.shape Cylinder DrawMode Box]
           [javafx.scene.paint PhongMaterial Color]
           [javafx.scene.transform Translate Rotate]))

(def Frame (JFrame. "ThreeD"))
(def fxPanel (JFXPanel.))

(SwingUtilities/invokeLater
  (proxy
    [Runnable]
    []
    (run []
      (doto Frame
        (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
        (.add fxPanel)
        (.setSize 800 600)
        (.setVisible true))

      (Platform/runLater
        (proxy
          [Runnable]
          []
          (run []
            (def camera_rotateX_init 0)
            (def camera_rotateY_init 0)
            (def camera_rotateZ_init 0)

            (def camera_translateX_init 0)
            (def camera_translateY_init 0)
            (def camera_translateZ_init -15)

            (def pan (BorderPane.))
            (def scene (Scene. pan))

            (def disk (Cylinder. 1 0.2))
            (doto disk
              (.setMaterial (PhongMaterial. (Color/DARKRED)))
              (.setDrawMode (DrawMode/FILL))
              )

            (def camera (PerspectiveCamera. true))

            (def camera_translate
              (Translate. camera_translateX_init camera_translateY_init camera_translateZ_init))

            (def camera_roate_X (Rotate. camera_rotateX_init (Rotate/X_AXIS)))
            (def camera_roate_Y (Rotate. camera_rotateY_init (Rotate/Y_AXIS)))
            (def camera_roate_Z (Rotate. camera_rotateZ_init (Rotate/Z_AXIS)))

            (.addAll (.getTransforms camera)
              [camera_roate_X camera_roate_Y camera_roate_Z camera_translate])

            (def root (Group.))
            (doto (.getChildren root)
              (.add disk)
              )
            (def sub (SubScene. root 500 500))
            (doto sub
              (.setFill (Color/CADETBLUE))
              (.setCamera camera)
              )

            (def labelZ (Label. (str camera_translateZ_init)))
            (def sliderZ (Slider.))
            (doto sliderZ
              (.setMin -50)
              (.setMax 20)
              (.setValue camera_translateZ_init)
              )
            (.addListener (.valueProperty sliderZ)
              (proxy [ChangeListener] []
                (changed [v,o,n]
                  (.setText labelZ (str n))
                  (.setZ camera_translate n)
                  )))

            (def labelY (Label. (str camera_translateY_init)))
            (def sliderY (Slider.))
            (doto sliderY
              (.setMin -15)
              (.setMax 15)
              (.setValue camera_translateY_init)
              )
            (.addListener (.valueProperty sliderY)
              (proxy [ChangeListener] []
                (changed [v,o,n]
                  (.setText labelY (str n))
                  (.setY camera_translate n)
                  )))

            (def labelX (Label. (str camera_translateX_init)))
            (def sliderX (Slider.))
            (doto sliderX
              (.setMin -15)
              (.setMax 15)
              (.setValue camera_translateX_init)
              )
            (.addListener (.valueProperty sliderX)
              (proxy [ChangeListener] []
                (changed [v,o,n]
                  (.setText labelX (str n))
                  (.setX camera_translate n)
                  )))


            (def labelRX (Label. (str camera_rotateX_init)))
            (def sliderRX (Slider.))
            (doto sliderRX
              (.setMin -180)
              (.setMax 180)
              (.setValue camera_rotateX_init)
              )
            (.addListener (.valueProperty sliderRX)
              (proxy [ChangeListener] []
                (changed [v,o,n]
                  (.setText labelRX (str n))
                  (.setAngle camera_roate_X n)
                  )))

            (def labelRY (Label. (str camera_rotateY_init)))
            (def sliderRY (Slider.))
            (doto sliderRY
              (.setMin -180)
              (.setMax 180)
              (.setValue camera_rotateY_init)
              )
            (.addListener (.valueProperty sliderRY)
              (proxy [ChangeListener] []
                (changed [v,o,n]
                  (.setText labelRY (str n))
                  (.setAngle camera_roate_Y n)
                  )))

            (def labelRZ (Label. (str camera_rotateZ_init)))
            (def sliderRZ (Slider.))
            (doto sliderRZ
              (.setMin -180)
              (.setMax 180)
              (.setValue camera_rotateZ_init)
              )
            (.addListener (.valueProperty sliderRZ)
              (proxy [ChangeListener] []
                (changed [v,o,n]
                  (.setText labelRZ (str n))
                  (.setAngle camera_roate_Z n)
                  )))


            (def left (VBox.))
            (doto (.getChildren left)

              (.add (Label. "TranslateX:"))
              (.add sliderX)
              (.add labelX)

              (.add (Label. "TranslateY:"))
              (.add sliderY)
              (.add labelY)

              (.add (Label. "TranslateZ:"))
              (.add sliderZ)
              (.add labelZ)

              (.add (Label. "RotateX:"))
              (.add sliderRX)
              (.add labelRX)

              (.add (Label. "RotateY:"))
              (.add sliderRY)
              (.add labelRY)

              (.add (Label. "RotateZ:"))
              (.add sliderRZ)
              (.add labelRZ)

              )

            (.setLeft pan left)
            (.setCenter pan sub)
            (.setScene fxPanel scene)
            )))
      )))
