(ns
  ^{:author cWX205128}
  framework.managerUI.UIMain
  (javafx.embed.swing.JFXPanel.)
  (:import [javax.swing JFrame SwingUtilities]
   [javafx.embed.swing JFXPanel]
   [javafx.scene.layout BorderPane StackPane HBox VBox]
   [javafx.event ActionEvent EventHandler]
   [javafx.stage Stage StageStyle Modality]
   [javafx.application Platform]
   [javafx.scene Scene]
   [javafx.scene.control Button Label TextArea ToggleButton TextField]
   [javafx.scene.image Image ImageView]
   [javafx.concurrent Service Task])
  (:use [framework.messageCore])
  (:use [framework.managedResoreces])
  (:use [framework.messageBox])
  (:require [framework.managerUI.UIManager :as manager])
  (:use [framework.managerUI.managerCommon])
  )

(def Frame (JFrame. "Hawkmoth"))
(def fxPanel (JFXPanel.))

(defn aboutHandler [this window]
  (proxy
    [EventHandler]
    []
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
        (.show)))))

(SwingUtilities/invokeLater
  (proxy
    [Runnable]
    []
    (run []
      (doto Frame
        (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
        (.add fxPanel)
        (.setSize 500 200)
        (.setVisible true))
      (Platform/runLater
        (proxy
          [Runnable]
          []
          (run []
            (def pan (BorderPane.))
            (def scene (Scene. pan))
            (def Aboutbtn (Button. "about"))
            (def top (HBox.))
            (doto (.getChildren top)
              (.add Aboutbtn))
            (loop [worker @rigistedThread]
              (if
                (nil? (first worker))
                nil
                (recur
                  (do
                    (def button (ToggleButton. (.getName (first worker))))
                    (swap! buttons concat [button])
                    (case (.getName (first worker))
                      "manager"
                      (.setOnAction
                        button
                        (manager/managerHandler button (.getWindow scene)))
                      ""
                      )
                    (doto (.getChildren top)
                      (.add button)
                      ) (rest worker)))))
            (.setTop pan top)
            (.setOnAction Aboutbtn (aboutHandler this (.getWindow scene)))
            (.setScene fxPanel scene)))))))
