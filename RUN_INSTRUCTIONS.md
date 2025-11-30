# How to Run the Project

## Prerequisites
- Java 17+ (Ensure `JAVA_HOME` is set)
- Node.js and npm

## Running All Services
You can use the provided PowerShell script to launch all services and the frontend in separate terminal windows.

1. Open a PowerShell terminal in the project root.
2. Run the script:
   ```powershell
   .\run_all.ps1
   ```

## Manual Startup
If you prefer to run them manually, open 4 terminal windows:

1. **Player Service**
   ```bash
   cd player-service
   .\gradlew.bat bootRun
   ```
   Runs on: http://localhost:8081

2. **Inventory Service**
   ```bash
   cd inventory-service
   .\gradlew.bat bootRun
   ```
   Runs on: http://localhost:8082

3. **Combat Service**
   ```bash
   cd combat-service
   .\gradlew.bat bootRun
   ```
   Runs on: http://localhost:8083

4. **Frontend**
   ```bash
   npm start
   ```
   *Note: The frontend configuration seems incomplete (missing `react`, `react-dom`, and start scripts). You may need to run `npm install react react-dom vite` and configure `vite` or `react-scripts`.*
