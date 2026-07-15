(ns association.facts-test
  (:require [clojure.test :refer [deftest is]]
            [association.facts :as facts]))

(deftest a4a-has-spec-basis
  (let [sb (facts/spec-basis "a4a")]
    (is (= 2 (count sb)))
    (is (every? #(= "5110" (:association-rule/isic %)) sb))
    (is (every? #(= "USA" (:association-rule/country %)) sb))))

(deftest unknown-association-has-no-spec-basis
  (is (nil? (facts/spec-basis "iata")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["a4a" "iata"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["iata"] (:missing-associations c)))))

(deftest by-topic-filters
  (is (= ["a4a.spec-2000"]
         (mapv :association-rule/id (facts/by-topic "a4a" :interoperability))))
  (is (empty? (facts/by-topic "a4a" :labor)))
  (is (empty? (facts/by-topic "iata" :governance))))
