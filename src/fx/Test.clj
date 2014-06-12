(ns
  ^{:author cWX205128}
  fx.Test
  (javafx.embed.swing.JFXPanel.)
  (:import [javax.swing JFrame SwingUtilities]
           [javafx.embed.swing JFXPanel]
           [javafx.scene.layout BorderPane StackPane HBox]
           [javafx.event ActionEvent EventHandler]
           [javafx.stage Stage StageStyle Modality]
           [javafx.application Platform]
           [javafx.scene Scene]
           [javafx.scene.control Button Label TextArea ToggleButton]
           [javafx.scene.image Image ImageView])
  (:use [framework.messageCore])
  (:use [framework.managedResoreces])
  )

(defn aboutHandler [this window]
  (proxy [EventHandler] []
    (handle [event]

      (def dialog (Stage.))
      (def stack (StackPane.))

      (def image (Image. (.getResourceAsStream (.getClass this) "/00.jpg")))
      (def imageLabel (Label.))
      (.setGraphic imageLabel (ImageView. image))

      (doto (.getChildren stack)
        (.add imageLabel)
        (.add (Label. "Hello World!")))

      (def scene (Scene. stack 350 350))
      (doto dialog
        (.setTitle "About")
        (.initStyle (StageStyle/UTILITY))
        (.initModality (Modality/WINDOW_MODAL))
        (.initOwner window)
        (.setScene scene)
        (.show))
      )))

(def Frame (JFrame. "Hawkmoth"))
(def fxPanel (JFXPanel.))
(SwingUtilities/invokeLater
  (proxy [Runnable] []
    (run []
      (doto Frame
        (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
        (.add fxPanel)
        (.setSize 800 600)
        (.setVisible true))
      (Platform/runLater
        (proxy [Runnable] []
          (run [] (def pan (BorderPane.))
            (def Aboutbtn (Button. "about"))
            (def top (HBox.))
            (doto (.getChildren top)
              (.add Aboutbtn)
              )
            (loop [worker @rigistedThread]
              (if (nil? (first worker)) nil
                (recur
                  (do
                    (doto (.getChildren top)
                      (.add (Button. (.getName (first worker))))
                      )
                    (rest worker)))
                )
              )
            (.setTop pan top)
            (def scene (Scene. pan))
            (.setOnAction Aboutbtn (aboutHandler this (.getWindow scene)))
            (.setScene fxPanel scene))))
      )))
