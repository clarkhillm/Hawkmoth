(ns
  ^{:author cWX205128}
  fx.Test
  (javafx.embed.swing.JFXPanel.)
  (:import [javax.swing JFrame SwingUtilities]
           [javafx.embed.swing JFXPanel]
           [javafx.scene.layout BorderPane StackPane HBox VBox]
           [javafx.event ActionEvent EventHandler]
           [javafx.stage Stage StageStyle Modality]
           [javafx.application Platform]
           [javafx.scene Scene]
           [javafx.scene.control Button Label TextArea ToggleButton TextField]
           [javafx.scene.image Image ImageView])
  (:use [framework.messageCore])
  (:use [framework.managedResoreces])
  (:use [framework.messageBox])
  )

(def Frame (JFrame. "Hawkmoth"))
(def fxPanel (JFXPanel.))
(def currentStage (atom ()))
(def buttons (atom ()))
(defn getWindow [title] (first (filter (fn [x] (= title (.getTitle x))) @currentStage)))
(defn getButton [text] (first (filter (fn [x] (= text (.getText x))) @buttons)))

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
        (.show)))))

(defn createManagerWindow [button window]
  (def threadName "managerReceiver")
  (def dialog (Stage.))
  (def stack (StackPane.))
  (def vbox (VBox.))
  (def hbox (HBox.))
  (def text (TextField.))
  (def t (TextArea.))
  (.setEditable t false)
  (.setPrefWidth text 300)
  (def sendButton (Button. "send"))
  (.setOnAction sendButton
    (proxy [EventHandler] []
      (handle [event]
        (sendMessage "manager" (str {:from threadName :message (.getText text)}))
        (.clear text)
        (.setDisable sendButton true)

        )))
  (doto (.getChildren hbox)
    (.add text) (.add sendButton))

  (doto (.getChildren vbox)
    (.add t)
    (.add hbox))
  (doto (.getChildren stack)
    (.add vbox))
  (def scene (Scene. stack 400 (* 400 0.618)))
  (doto dialog
    (.setTitle "manager")
    (.initStyle (StageStyle/UTILITY))
    (.initModality (Modality/WINDOW_MODAL))
    (.initOwner window)
    (.setScene scene)
    (.setOnHidden
      (proxy [EventHandler] []
        (handle [event] (.setSelected (getButton "manager") false))))))

(defn managerHandler [button window]
  (proxy [EventHandler] []
    (handle [event]
      (if (true? (.isSelected button))
        (if (empty? (filter (fn [x] (= "manager" (.getTitle x))) @currentStage))
          (do
            (swap! currentStage concat [(createManagerWindow button window)])
            (.show (getWindow "manager")))
          (.show (getWindow "manager")))
        (.close (getWindow "manager"))))))

(SwingUtilities/invokeLater
  (proxy [Runnable] []
    (run []
      (doto Frame
        (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
        (.add fxPanel)
        (.setSize 500 200)
        (.setVisible true))
      (Platform/runLater
        (proxy [Runnable] []
          (run []
            (def pan (BorderPane.))
            (def scene (Scene. pan))
            (def Aboutbtn (Button. "about"))
            (def top (HBox.))
            (doto (.getChildren top)
              (.add Aboutbtn))
            (loop [worker @rigistedThread]
              (if (nil? (first worker)) nil
                (recur
                  (do
                    (def button (ToggleButton. (.getName (first worker))))
                    (swap! buttons concat [button])
                    (case (.getName (first worker))
                      "manager" (.setOnAction button (managerHandler button (.getWindow scene)))
                      ""
                      )
                    (doto (.getChildren top)
                      (.add button)
                      ) (rest worker)))))
            (.setTop pan top)
            (.setOnAction Aboutbtn (aboutHandler this (.getWindow scene)))
            (.setScene fxPanel scene)))))))
