# Volvoassignment
Summary of Work Completed
 **1. Problem Understanding and Requirements:**
- **Objective:** Implement a congestion tax calculation application with an HTTP interface.
- **Constraints:**
  - Calculate tax based on Gothenburg's congestion rules.
  - Implement an HTTP server to interact with the application.
  - Ensure the system can be extended for other cities in the future.
  - Limit the scope to the year 2013.

 **2. Key Components and Updates:**

1. **CongestionTaxCalculator:**
   - **Purpose:** Calculate congestion tax based on vehicle type and timestamps.
   - **Updates:**
     - Refactored to correct bugs in tax calculation.
     - Implemented logic for single charge rule and toll-free dates.
     - Added support for different toll-free vehicles.

2. **Vehicle Class:**
   - **Purpose:** Represent different types of vehicles.
   - **Updates:**
     - Created specific vehicle types (e.g., `Car`, `MotorBike`) to use in tax calculations.
     - Added vehicle types to the `TOLL_FREE_VEHICLES` map for handling exemptions.

3. **CongestionController:**
   - **Purpose:** Set up an HTTP server and handle incoming requests to calculate taxes.
   - **Updates:**
     - Implemented an HTTP server using `HttpServer`.
     - Created `TaxHandler` to process HTTP requests, parse date parameters, and compute the tax.
     - Added error handling and proper response management.

4. **Main Entry Point:**
   - **Purpose:** Start the HTTP server and configure the application.
   - **Updates:**
     - Set up the HTTP server to listen on port 8080.
     - Connected the `/calculateTax` endpoint to the `TaxHandler` for processing requests.

5. **Improvements Made:**
- **Bug Fixes:** Corrected errors in tax calculation logic.
- **Code Refactoring:** Improved code organization and readability.
- **Error Handling:** Added robust error handling for invalid inputs and server errors.
- **HTTP Server Integration:** Successfully integrated the `CongestionTaxCalculator` with an HTTP server to allow remote tax calculations.

#### **6. Future Considerations:**
- **Extensibility:** Refactor the code to handle different tax rules for other cities using external data sources.
- **Testing:** Implement unit and integration tests to ensure the correctness of the tax calculations and server responses.
- **Documentation:** Prepare comprehensive documentation for the codebase and usage instructions for the application.

### Time Management:

- **Initial Setup and Understanding:** ~1 hour
- **Refactoring and Bug Fixes:** ~1.5 hours
- **HTTP Server Integration:** ~1 hour
- **Testing and Validation:** ~0.5 hour
- **Documentation and Final Touches:** ~0.5 hour
