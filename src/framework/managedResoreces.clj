(ns
  ^{:author cWX205128}
  framework.managedResoreces)

(def rigistedThread (atom ()))
(defn rigistThread [thread] (swap! rigistedThread concat [thread]))
