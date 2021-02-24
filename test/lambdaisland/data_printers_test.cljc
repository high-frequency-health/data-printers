(ns lambdaisland.data-printers-test
  (:require [lambdaisland.data-printers :as dp]
            [clojure.pprint :as pprint]
            [clojure.test :refer [deftest testing is]]))

(deftype MyType [x])

(defn to-edn [obj]
  {:x (.-x obj)})

(dp/register-print MyType 'my/type to-edn)
(dp/register-pprint MyType 'my/type to-edn)

(def obj (->MyType 1))

(deftest regular-print
  (is (= "#my/type {:x 1}"
         (pr-str obj)))

  (is (= #?(:clj "#my/type #=(clojure.lang.PersistentArrayMap/create {:x 1})"
            :cljs "#my/type {:x 1}")
         (binding [*print-dup* true]
           (pr-str obj)))))

(deftest pretty-print
  (is (= "#my/type {:x 1}\n"
         (with-out-str
           (pprint/pprint obj)))))
