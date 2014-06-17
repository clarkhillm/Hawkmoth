(ns
  ^{:author cWX205128}
  framework.managerUI.managerCommon)

(def currentStage (atom ()))
(def buttons (atom ()))
(defn getWindow [title] (first (filter (fn [x] (= title (.getTitle x))) @currentStage)))
(defn getButton [text] (first (filter (fn [x] (= text (.getText x))) @buttons)))
