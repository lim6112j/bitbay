;; agent_example.clj
;; This example demonstrates Clojure's Agents.
;; Agents provide a way to manage shared, mutable state in an asynchronous
;; and independent manner. Actions are sent to an agent and are executed
;; sequentially in a thread pool, without direct coordination from the sender.

(println "--- Clojure Agents ---")

;; --- Example 1: Agent with a numeric state (counter) ---
(println "\n--- Agent 1: Counter ---")
;; Create an agent with an initial state of 0
(def counter-agent (agent 0))
(println "Initial agent state (counter):" @counter-agent)

;; Define a function to increment the agent's state
(defn increment-state [current-state by]
  (println (str "Action: Incrementing " current-state " by " by))
  (+ current-state by))

;; Send actions to the agent using 'send'.
;; 'send' dispatches the action to run in a thread pool.
(send counter-agent increment-state 10)
(send counter-agent increment-state 5)

(println "State immediately after sending actions (might not be updated yet):" @counter-agent)

;; Wait for all actions dispatched to the agent to complete
(println "Awaiting agent actions for counter-agent...")
(await counter-agent)
(println "State after awaiting actions:" @counter-agent)


;; --- Example 2: Agent with a vector state (collecting items) ---
(println "\n--- Agent 2: Item Collector ---")
(def item-agent (agent [])) ; Initial state is an empty vector
(println "Initial agent state (item list):" @item-agent)

(defn add-item [current-list item]
  (println (str "Action: Adding '" item "' to list " current-list))
  (Thread/sleep 50) ; Simulate some work
  (conj current-list item))

(send item-agent add-item "apple")
(send item-agent add-item "banana")
(send item-agent add-item "cherry")

(println "Item list immediately after sending actions:" @item-agent)
(println "Awaiting agent actions for item-agent...")
(await item-agent)
(println "Item list after awaiting actions:" @item-agent)


;; --- Error Handling in Agents ---
(println "\n--- Agent Error Handling ---")
(def error-agent (agent 10))

(defn cause-error [state]
  (println (str "Action: Attempting operation on " state " that will cause an error."))
  (/ state 0)) ; This will cause a division by zero error

(send error-agent cause-error)

;; Check for agent errors
(println "Awaiting error-agent...")
(await error-agent)

(if (agent-error error-agent)
  (println "Agent encountered an error:" (agent-error error-agent))
  (println "Agent state after error attempt:" @error-agent))

;; To clear an error and set a new state, you can use 'restart-agent'
(println "Restarting error-agent with state 0 and clearing error.")
(restart-agent error-agent 0)
(println "Error after restart:" (agent-error error-agent))
(println "State after restart:" @error-agent)


(println "\nDemonstration complete. Agents are useful for managing state that can be updated asynchronously and independently.")

;; To run this example (assuming you have Clojure installed):
;; 1. Save this file as agent_example.clj
;; 2. Open a terminal and navigate to the directory where you saved the file.
;; 3. Run: clj -M agent_example.clj
;; Note: Due to the asynchronous nature, the exact order of print statements from
;; within agent actions and the main thread might vary slightly between runs,
;; but the final state after 'await' will be consistent.
