# Code Refactor Summary

## 1.  Main Goals for this code refactoring
The goal of this refactoring code is to improve the code quality of the `InquiryService.java` class. After the improvements, the code should be the following characteristics:
 -  The code in the `InquiryService.java` class is cleaner and easier to read.
 -  The code has better quality, easier to maintain, and easier modification and scaling of future business logic or features.
 - The unit test logic in the `InquiryServiceTest.java` class continues to work effectively with the new code.


## 2. Problems from legacy code

### 2.1 Method is too large

inquiry method in `InquiryService.java` class is doing too many things. It is responsible for **validating input parameters**, **calling an external web service**, **parsing the response**, and **handling various exceptions**. This makes the method large, complex, and difficult to maintain.

### 2.2 Complex nested if-else in many layers
The code uses a long if-else to handle different response codes from the bank response (**approved**, **invalid_data**, **transaction_error**, **unknown**.). This structure is hard to **read**, **modify**, and **easily lead to errors** when new conditions need to be added.

### 2.3 Inappropriate exceptions
In validation logic throws `NullPointerException` for invalid input which should normally represent an unexpected error but in this validation part, it seems to be a predictable validation failure. A custom exception may be more appropriate.


## 3. Refactoring steps

### Refactoring Step 1: Separate Validation Logic
- Create a class called `InquiryRequestValidator.java` with the single responsibility of validating the input.
- Move all if checks from `InquiryService.java` into validate() method inside the new `InquiryRequestValidator.java` class.
- Created a custom exception called `InvalidRequestException` to represent a validation error.
- Wrote a New Test named `InquiryRequestValidatorTest.java` to ensure new validator works correctly.

### Refactoring Step 2: Handling "invalid_data"
- create a new class, `InvalidDataResponseHandler.java`, to handle for this specific case.
- move all the complex code for splitting the string and determining the correct reasonCode and reasonDesc was moved into the handle() method.
- replaced the original code in **"invalid_data"** section with a simple code call to new handler **(invalidDataHandler.handle(response))**.
- Write a New Test named `InvalidDataResponseHandlerTest.java`, to confirm that new handler correctly parses all formats of the description string.


### Refactoring Step 3: Move nested if "transaction_error"
- Create a class called `TransactionErrorHandler.java` to take care of all of this complex logic.
- Moved All Complex Logic and messy code and the string splitting and the special "98" into the handle() method of the new handler.
- replaced `InquiryService.java` with a clean call to transactionErrorHandler.handle(response).
- Write a New Test named `TransactionErrorHandlerTest.java`, to confirm that new handler correctly parses all formats.

### Refactoring Step 4: Handling "unknown" Response
- create a new class, `UnknownResponseHandler.java`, to handle for this specific case.
- Moved all the code for handling the "unknown" case, including the check for an empty description part into the handle() method of this new handler.
- Wrote a New Test `UnknownResponseHandlerTest.java` to verify that it correctly handles all "unknown" scenarios.
