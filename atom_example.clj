;; atom_example.clj
;; This example demonstrates Clojure's Atoms.
;; Atoms provide a way to manage shared, synchronous, uncoordinated, atomic state.
;; They are a simpler way to manage state than STM (refs) when you don't need
;; coordinated updates across multiple identities.

(println "--- Clojure Atoms ---")

;; --- Example 1: Basic Atom Usage ---
(println "
--- Atom 1: Basic Operations ---")
;; Create an atom with an initial state of 0
(def counter-atom (atom 0))
(println "Initial atom state (counter):" @counter-atom)

;; Read an atom's value using deref/@
(println "Current value:" @counter-atom)

;; Update an atom's value using reset!
;; reset! directly sets the atom's value.
(reset! counter-atom 10)
(println "State after reset! to 10:" @counter-atom)

;; Update an atom's value using swap!
;; swap! takes a function and applies it to the current value of the atom.
;; The update is atomic.
(swap! counter-atom + 5) ; equivalent to (atom (+ @atom 5))
(println "State after swap! + 5:" @counter-atom)

(swap! counter-atom (fn [current-value] (* current-value 2)))
(println "State after swap! * 2:" @counter-atom)

;; --- Example 2: Atomicity with swap! ---
(println "
--- Atom 2: Illustrating Atomicity (Conceptual) ---")
;; While true parallelism is hard to demonstrate in a simple script
;; without actual threads, swap! ensures that the function it applies
;; sees the current value and the update is atomic. If multiple threads
;; were calling swap! concurrently, each operation would be correctly
;; applied without race conditions.

(def safe-counter (atom 0))

;; Simulate multiple "threads" trying to increment the counter.
;; In a real multi-threaded scenario, these would run concurrently.
(dotimes [n 5]
  (swap! safe-counter inc))
(println "Safe counter after 5 increments using swap! (simulated):" @safe-counter) ; Expected: 5

;; If we tried to do this without atomicity (e.g., with a plain var and non-atomic ops),
;; we could get race conditions in a truly concurrent setting.
;; (let [current @safe-counter] (Thread/sleep 10) (def safe-counter (+ current 1))) ; Don't do this for shared mutable state!

;; --- Example 3: Using swap! with arguments ---
(println "
--- Atom 3: swap! with arguments ---")
(def user-atom (atom {:name "Alice" :age 30}))
(println "Initial user data:" @user-atom)

(defn update-age [user new-age]
  (assoc user :age new-age))

;; The first argument to swap! is the atom.
;; The second is the function to apply.
;; Subsequent arguments are passed as arguments to that function *after* the atom's current value.
(swap! user-atom update-age 31)
(println "User data after updating age:" @user-atom)

(swap! user-atom assoc :city "New York") ; assoc takes map, key, val
(println "User data after adding city:" @user-atom)


;; --- Example 4: Compare-and-Set (CAS) with compare-and-set! ---
(println "
--- Atom 4: Compare-and-Set ---")
(def cas-atom (atom "initial"))
(println "Initial CAS atom value:" @cas-atom)

;; compare-and-set! atomically sets the value of an atom to a new value
;; if and only if the current value of the atom is equal to an expected value.
;; It returns true if successful, false otherwise.

(let [current-val @cas-atom]
  (println (str "Attempting to set to 'updated' if current is '" current-val "'"))
  (if (compare-and-set! cas-atom current-val "updated")
    (println "CAS successful. New value:" @cas-atom)
    (println "CAS failed. Value remains:" @cas-atom)))

(println (str "Attempting to set to 'another-update' if current is 'something-else'"))
(if (compare-and-set! cas-atom "something-else" "another-update")
  (println "CAS successful. New value:" @cas-atom)
  (println "CAS failed. Value remains:" @cas-atom " (this is expected)"))


(println "
Demonstration complete. Atoms are suitable for managing simple, independent, shared mutable state.")

;; To run this example (assuming you have Clojure installed):
;; 1. Save this file as atom_example.clj
;; 2. Open a terminal and navigate to the directory where you saved the file.
;; 3. Run: clj -M atom_example.clj
