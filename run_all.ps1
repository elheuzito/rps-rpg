# Script to run all microservices and frontend

Write-Host "Starting Player Service on port 8081..."
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd player-service; .\gradlew.bat bootRun"

Write-Host "Starting Inventory Service on port 8082..."
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd inventory-service; .\gradlew.bat bootRun"

Write-Host "Starting Combat Service on port 8083..."
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd combat-service; .\gradlew.bat bootRun"

Write-Host "Starting Frontend on port 3000..."
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd frontend; npm run dev"

Write-Host "All services launched in separate windows."
