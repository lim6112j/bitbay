;; macro_example.clj
;; This example demonstrates Clojure's Macros.
;; Macros are a powerful feature of Lisp-like languages, including Clojure.
;; They allow you to write code that writes code, effectively extending the language.
;; Macros operate at compile-time, transforming syntax into other valid Clojure code.
;; This is possible because Clojure code is represented as data structures (homoiconicity).

(println "--- Clojure Macros ---")

;; --- Example 1: An 'unless' macro ---
;; Clojure has 'if', 'if-not', but let's define 'unless' for demonstration.
;; 'unless' executes its 'then-branch' if the condition is false,
;; and an optional 'else-branch' if the condition is true.

(defmacro unless [condition then-branch & [else-branch]]
  (println (str "Macroexpanding 'unless' with condition: " condition))
  ;; The body of the macro returns a new list of Clojure code.
  ;; We use syntax-quote (`) and unquote (~) to construct the code.
  `(if (not ~condition)
     ~then-branch
     ~else-branch))

(println "\n--- Using the 'unless' macro ---")

(unless true
  (println "This should NOT print (condition was true)")
  (println "This is the else branch (condition was true)"))

(unless false
  (println "This SHOULD print (condition was false)")
  (println "This is the else branch (condition was false - so this part is skipped)"))

(println "Example with only then-branch:")
(unless (= 5 5) ; condition is true
  (println "Should not print (5 is 5)")
  ;; No else branch here, so if condition is true, macro expands to (if false <then> nil)
  )

(unless (< 5 3) ; condition is false
  (println "Should print (5 is not less than 3)")
  )


;; --- Example 2: A simple logging macro ---
;; This macro will print the expression being evaluated and then its result.

(defmacro log-expr [expr]
  ;; We want to capture the actual expression as a string for printing.
  `(let [result# ~expr] ; using gensym (#) to avoid capturing 'result'
     (println (str "Evaluating: '" '~expr "'"))
     (println (str "Result: " result#))
     result#)) ; The macro should return the result of the expression

(println "\n--- Using the 'log-expr' macro ---")
(log-expr (+ 10 20))

(def x 5)
(log-expr (* x x x))


(println "\n--- Macroexpansion ---")
(println "You can see how a macro expands using 'macroexpand' or 'macroexpand-1'")
(println "Expansion of (unless true (println \"T\") (println \"F\")):")
(println (macroexpand '(unless true (println "T") (println "F"))))

(println "Expansion of (log-expr (+ 1 2)):")
(println (macroexpand '(log-expr (+ 1 2))))


(println "\nDemonstration complete. Macros allow for powerful compile-time code transformations.")

;; To run this example (assuming you have Clojure installed):
;; 1. Save this file as macro_example.clj
;; 2. Open a terminal and navigate to the directory where you saved the file.
;; 3. Run: clj -M macro_example.clj
;; Note: The 'Macroexpanding unless...' messages from the unless macro will appear
;; when the code is compiled (loaded), not necessarily interleaved with the runtime printlns
;; in the order you might expect from a simple top-down script execution.
