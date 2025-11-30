import axios from 'axios';

const playerService = axios.create({
    baseURL: import.meta.env.VITE_PLAYER_SERVICE_URL || 'http://localhost:8081/players'
});

const inventoryService = axios.create({
    baseURL: import.meta.env.VITE_INVENTORY_SERVICE_URL || 'http://localhost:8082/inventory'
});

const combatService = axios.create({
    baseURL: import.meta.env.VITE_COMBAT_SERVICE_URL || 'http://localhost:8083/combat'
});

export { playerService, inventoryService, combatService };
