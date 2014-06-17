(ns
  ^{:author cWX205128}
  framework.managerUI.UIManager
  (:use [framework.messageBox])
  (:use [framework.managerUI.managerCommon])
  (:import [javafx.concurrent Service Task]
   [javafx.stage Stage StageStyle Modality]
   [javafx.scene.layout StackPane VBox HBox]
   [javafx.scene.control TextField TextArea Button]
   [javafx.event EventHandler]
   [javafx.scene Scene]))

(defn createManagerWindow [button window]
  (def threadName "managerReceiver")
  (def receivService
    (proxy
      [Service]
      []
      (createTask []
        (proxy
          [Task]
          []
          (call []
            (loop [m nil]
              (if-not (nil? m)
                (load-string m)
                (recur (getMessage threadName)))))))))

  (def dialog (Stage.))
  (def stack (StackPane.))
  (def vbox (VBox.))
  (def hbox (HBox.))
  (def text (TextField.))
  (.setPrefWidth text 300)
  (def t (TextArea.))
  (.setEditable t false)

  (def sendButton (Button. "send"))
  (.setOnAction sendButton
    (proxy
      [EventHandler]
      []
      (handle [event]
        (.restart receivService)
        (sendMessage "manager" (str {:from threadName :message (.getText text)}))
        (.clear text)
        (.setDisable sendButton true))))
  (.setOnSucceeded receivService
    (proxy
      [EventHandler]
      []
      (handle [event]
        (.appendText t (str (:message (.. event (getSource) (getValue))) "\n"))
        (.setDisable sendButton false))))

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
    (.setScene scene)
    (.setOnHidden
      (proxy
        [EventHandler]
        []
        (handle [event] (.setSelected (getButton "manager") false))))))

(defn managerHandler [button window]
  (proxy
    [EventHandler]
    []
    (handle [event]
      (if (true? (.isSelected button))
        (if (empty? (filter (fn [x] (= "manager" (.getTitle x))) @currentStage))
          (do
            (swap! currentStage concat [(createManagerWindow button window)])
            (.show (getWindow "manager")))
          (.show (getWindow "manager")))
        (.close (getWindow "manager"))))))
