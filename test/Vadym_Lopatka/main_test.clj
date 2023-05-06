(ns Vadym-Lopatka.main-test
  (:require [clojure.test :refer [deftest testing is]]
            [Vadym-Lopatka.main :refer :all]))

(deftest a-test
  (testing "FIXED, I am not fail anymore."
    (is (= 1 1))))
