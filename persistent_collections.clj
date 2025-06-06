;; persistent_collections.clj
;; This example demonstrates Clojure's persistent and immutable data structures.
;; When you perform an operation that appears to "modify" a collection,
;; Clojure actually returns a new collection instance, leaving the original unchanged.
;; This is fundamental to Clojure's approach to concurrency and predictable state management.

(println "--- Clojure Persistent Data Structures ---")

;; --- Vectors (Ordered Collections) ---
(println "\n--- Vectors ---")
(def original-vector [1 2 3])
(println "Original vector:" original-vector)

;; 'conj' (conjoin) adds elements. For vectors, it adds to the end.
(def new-vector (conj original-vector 4 5))
(println "Vector after conj [4 5]:" new-vector)
(println "Original vector (still unchanged):" original-vector)

;; --- Maps (Key-Value Pairs) ---
(println "\n--- Maps ---")
(def original-map {:a 1 :b 2})
(println "Original map:" original-map)

;; 'assoc' (associate) adds or updates key-value pairs.
(def map-with-new-key (assoc original-map :c 3))
(println "Map after assoc [:c 3]:" map-with-new-key)
(def map-with-updated-key (assoc original-map :a 100))
(println "Map after assoc [:a 100] (updating existing key):" map-with-updated-key)
(println "Original map (still unchanged):" original-map)

;; 'dissoc' (dissociate) removes keys from a map.
(def map-without-key (dissoc original-map :b))
(println "Map after dissoc [:b]:" map-without-key)
(println "Original map (still unchanged):" original-map)

;; --- Sets (Unordered Collections of Unique Values) ---
(println "\n--- Sets ---")
(def original-set #{:apple :banana :orange})
(println "Original set:" original-set)

;; 'conj' adds elements to a set.
(def new-set (conj original-set :grape :banana)) ;; :banana is already there, won't be duplicated
(println "Set after conj [:grape :banana]:" new-set)
(println "Original set (still unchanged):" original-set)

;; 'disj' (disjoin) removes elements from a set.
(def set-without-element (disj original-set :apple :pear)) ;; :pear is not there, no error
(println "Set after disj [:apple :pear]:" set-without-element)
(println "Original set (still unchanged):" original-set)

(println "\nDemonstration complete. Notice how original collections are never modified.")

;; To run this example (assuming you have Clojure installed):
;; 1. Save this file as persistent_collections.clj
;; 2. Open a terminal and navigate to the directory where you saved the file.
;; 3. Run: clj -M persistent_collections.clj
