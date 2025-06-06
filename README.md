# Clojure Unique Features Examples

This project contains a set of Clojure files, each demonstrating a unique or characteristic feature of the Clojure programming language. These examples aim to provide a basic understanding of these concepts.

## Examples

Below is a list of the example files and the Clojure features they illustrate:

1.  **`persistent_collections.clj`**:
    *   **Feature**: Immutability and Persistent Data Structures
    *   **Description**: Shows how Clojure's core data structures (vectors, maps, sets) are immutable. Operations that "modify" these collections actually return new instances, leaving the original unchanged. This is crucial for Clojure's concurrency model and predictable state management.

2.  **`stm_example.clj`**:
    *   **Feature**: Software Transactional Memory (STM)
    *   **Description**: Demonstrates Clojure's STM system using `ref`s and `dosync` for managing shared, synchronous, and coordinated state. It illustrates how STM ensures atomic, consistent, and isolated changes to multiple transactional references.

3.  **`agent_example.clj`**:
    *   **Feature**: Agents
    *   **Description**: Illustrates Clojure `agent`s for managing shared, mutable state in an asynchronous and independent manner. Actions are sent to an agent and processed sequentially in a separate thread pool.

4.  **`macro_example.clj`**:
    *   **Feature**: Macros
    *   **Description**: Shows Clojure's powerful macro system. Macros are functions that run at compile-time, transforming code itself (which is possible because Clojure is homoiconic - its code is represented as data structures). This allows for powerful syntactic abstractions and domain-specific language creation.

## Running the Examples

To run these examples, you need to have Clojure installed. You can find installation instructions on the [official Clojure website](https://clojure.org/guides/getting_started).

Once Clojure is installed, you can run each example file from your terminal using the Clojure CLI:

1.  Navigate to the directory where you have saved these files.
2.  Execute the desired file using the command:

    ```bash
    clj -M <filename>.clj
    ```
    For example, to run the persistent collections example:
    ```bash
    clj -M persistent_collections.clj
    ```

    And similarly for the other files:
    ```bash
    clj -M stm_example.clj
    clj -M agent_example.clj
    clj -M macro_example.clj
    ```

Each script will print output to the console demonstrating the concepts. Review the comments within each file for more detailed explanations of the code.
