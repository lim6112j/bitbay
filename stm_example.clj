;; stm_example.clj
;; This example demonstrates Clojure's Software Transactional Memory (STM) system.
;; STM is used for managing shared, synchronous, coordinated state.
;; It ensures that changes to multiple 'refs' (transactional references) within
;; a transaction ('dosync' block) are atomic, consistent, and isolated.
;; If a transaction conflicts with another, it will be retried automatically.

(println "--- Clojure Software Transactional Memory (STM) ---")

;; Define two refs representing bank accounts with initial balances.
(def account-a (ref 1000))
(def account-b (ref 500))

(println (str "Initial state: Account A: @" account-a ", Account B: @" account-b))

;; Function to transfer money from one account to another.
;; This function must be called within a transaction.
(defn transfer [from-account to-account amount]
  (println (str "Attempting to transfer " amount " from source to destination..."))
  ;; Simulate a condition for transfer (e.g., sufficient funds)
  (when (>= @from-account amount)
    (alter from-account - amount) ; Decrease balance of from-account
    (alter to-account + amount)   ; Increase balance of to-account
    (println (str "Transfer of " amount " successful within transaction logic."))
    true ; Indicate success
    )
  ;; If funds are insufficient, the refs are not altered.
  ;; The transaction will still commit successfully, but without changes from this attempt.
  )

(println "\nInitiating a transaction to transfer 300 from Account A to Account B...")
;; Perform the transfer within a 'dosync' transaction.
(dosync
 (let [transfer-successful? (transfer account-a account-b 300)]
   (if transfer-successful?
     (println "Transaction committed: Transfer logic executed.")
     (println "Transaction committed: Transfer logic indicated insufficient funds or other issue."))))

(println (str "State after transfer: Account A: @" account-a ", Account B: @" account-b))

(println "\nAttempting another transfer of 800 from Account A (should be insufficient)...")
;; This transfer attempt should show that the state is not changed if conditions within the transaction are not met.
(dosync
  (if-not (transfer account-a account-b 800)
    (println "Transaction for 800: Insufficient funds in Account A. No changes made to balances.")))

(println (str "State after attempting to transfer 800: Account A: @" account-a ", Account B: @" account-b))


(println "\n--- Simulating a conflicting transaction (conceptual) ---")
;; It's hard to deterministically cause a visible conflict retry in a simple script
;; without actual concurrent threads trying to modify the same refs.
;; However, the STM system handles this by retrying the dosync block if a conflict occurs.

;; Imagine another thread tries to alter account-a or account-b here.
;; If this dosync block reads a ref, then another thread modifies it before this
;; transaction commits, this transaction would be retried.
(dosync
  (println "Starting a transaction that might be retried in a real concurrent scenario.")
  (let [current-a @account-a]
    ;; Simulate some work
    (Thread/sleep 50) ; Brief pause to increase chance of hypothetical conflict
    (alter account-a + 10)
    (println (str "Transaction: Added 10 to account-a. New balance if committed: " @account-a))))

(println (str "Final state: Account A: @" account-a ", Account B: @" account-b))

(println "\nDemonstration complete. 'refs' ensure that all changes within a 'dosync' block are atomic.")

;; To run this example (assuming you have Clojure installed):
;; 1. Save this file as stm_example.clj
;; 2. Open a terminal and navigate to the directory where you saved the file.
;; 3. Run: clj -M stm_example.clj
