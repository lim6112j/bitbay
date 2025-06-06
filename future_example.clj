;; future_example.clj
;; This example demonstrates Clojure's Futures.
;; Futures are used to execute computations in parallel in a separate thread.
;; The main thread can continue its work and retrieve the result of the future later.

(println "--- Clojure Futures ---")

;; --- Example 1: Basic Future Usage ---
(println "
--- Future 1: Simple Computation ---")

;; Define a function that simulates some work (e.g., a long computation or I/O)
(defn long-computation [id duration-ms]
  (println (str "Future " id ": Starting computation, will take " duration-ms "ms."))
  (Thread/sleep duration-ms)
  (let [result (str "Future " id ": Computation complete after " duration-ms "ms.")]
    (println result)
    result))

;; Create a future. The body of the future runs in a separate thread from a thread pool.
(def my-future1 (future (long-computation "F1" 1000)))
(println "Main thread: Future F1 has been dispatched.")

(println "Main thread: Doing other work while F1 computes...")
(Thread/sleep 300) ; Simulate other work in the main thread
(println "Main thread: Finished other work.")

;; To get the value of a future, dereference it (@my-future or (deref my-future)).
;; If the future is not yet realized (computation not finished), dereferencing will block
;; until the value is available.
(println "Main thread: Attempting to dereference F1. This will block if F1 is not done.")
(let [result1 @my-future1]
  (println "Main thread: Result from F1:" result1))

;; --- Example 2: Multiple Futures ---
(println "
--- Future 2: Multiple Parallel Computations ---")
(def future-a (future (long-computation "FA" 800)))
(def future-b (future (long-computation "FB" 500)))

(println "Main thread: Dispatched Future FA (800ms) and Future FB (500ms).")

;; Check status without blocking (optional)
(println "Main thread: Is FA realized yet?" (realized? future-a))
(println "Main thread: Is FB realized yet?" (realized? future-b))

(println "Main thread: Waiting for FB...")
(println "Main thread: Result from FB:" @future-b) ; FB should finish first
(println "Main thread: Is FA realized now?" (realized? future-a))

(println "Main thread: Waiting for FA...")
(println "Main thread: Result from FA:" @future-a)

(println "Main thread: Both futures FA and FB are now complete.")

;; --- Example 3: Future with an error ---
(println "
--- Future 3: Future with an Error ---")
(def error-future (future
                    (println "Error-Future: Starting, will throw an exception.")
                    (Thread/sleep 100)
                    (/ 1 0))) ; This will cause an ArithmeticException

(println "Main thread: Dispatched Error-Future.")

;; Attempting to dereference a future that throws an exception will re-throw that exception
;; in the thread that calls deref.
(try
  (println "Main thread: Attempting to dereference Error-Future...")
  (let [error-result @error-future]
    (println "Main thread: Error-Future result (should not be reached):" error-result))
  (catch Exception e
    (println "Main thread: Caught exception from Error-Future:" e)))

;; --- Example 4: Checking future? and realized? ---
(println "
--- Future 4: Checking future status ---")
(def short-future (future (Thread/sleep 50) "done"))
(println "Is short-future a future?" (future? short-future))
(println "Is the string 'hello' a future?" (future? "hello"))

(println "Is short-future realized immediately after creation?" (realized? short-future))
(Thread/sleep 100) ; Give it time to complete
(println "Is short-future realized after a short delay?" (realized? short-future))
(println "Result of short-future:" @short-future)


(println "
Demonstration complete. Futures are useful for offloading computations to run in parallel.")
;; Shut down agents to allow the program to exit cleanly if any agents were started by other examples
;; that might have been run in the same JVM session (e.g. during development/REPL use).
;; Futures use a thread pool that will shut down automatically if it's a daemon pool,
;; but explicitly calling this is good practice if agents were also used.
(shutdown-agents)

;; To run this example (assuming you have Clojure installed):
;; 1. Save this file as future_example.clj
;; 2. Open a terminal and navigate to the directory where you saved the file.
;; 3. Run: clj -M future_example.clj
;; Note: The exact order of print statements from futures and the main thread
;; can vary due to the nature of parallel execution.
