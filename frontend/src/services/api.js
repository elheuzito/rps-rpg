import axios from 'axios';

const playerService = axios.create({
    baseURL: 'http://localhost:8081/players'
});

const inventoryService = axios.create({
    baseURL: 'http://localhost:8082/inventory'
});

const combatService = axios.create({
    baseURL: 'http://localhost:8083/combat'
});

export { playerService, inventoryService, combatService };
