(ns fx.Main
  (javafx.embed.swing.JFXPanel.)
  (:import [javafx.application Application]
           [javafx.event ActionEvent EventHandler]
           [javafx.scene Scene]
           [javafx.scene.layout BorderPane HBox StackPane]
           [javafx.stage Stage StageStyle Modality]
           [javafx.scene.control Button Label TextArea ToggleButton]
           [javafx.scene.image Image ImageView]
           )
  (:gen-class :extends javafx.application.Application)
  )

(def Aboutbtn (Button. "About"))
(def logBtn (ToggleButton. "show Log"))
(.setSelected logBtn true)
(def logPanel (TextArea.))
(.setEditable logPanel false)

(defn logHandler []
  (proxy [EventHandler] []
    (handle [event]
      (if (.isSelected logBtn)
        (.setVisible logPanel true)
        (.setVisible logPanel false))
      )))

(defn aboutHandler [this stage]
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
        (.initOwner stage)
        (.setScene scene)
        (.show))
      )))

(defn log [text] (.appendText logPanel (str text "\n")))

(defn -start [this stage]
  (def toolBar (HBox.))
  (def pane (BorderPane.))

  (.setOnAction Aboutbtn (aboutHandler this stage))
  (.setOnAction logBtn (logHandler))

  (doto (.getChildren toolBar)
    (.add logBtn)
    (.add Aboutbtn)
    )

  (doto pane
    (.setTop toolBar)
    (.setBottom logPanel)
    )

  (def scene (Scene. pane 800 600))
  (doto stage (.setScene scene)
    (.setTitle "HAWKMOTH")
    (.show))
  (log "start success!")
  )

(defn -stop [app]
  (println "Exiting now"))

(Application/launch fx.Main (into-array String []))