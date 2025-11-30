import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import PlayerCreation from './components/PlayerCreation';
import Inventory from './components/Inventory';
import CombatArena from './components/CombatArena';

function App() {
    return (
        <Router>
            <div className="app-wrapper">
                <Navbar />
                <div className="container animate-fade-in">
                    <Routes>
                        <Route path="/" element={<Navigate to="/create-player" />} />
                        <Route path="/create-player" element={<PlayerCreation />} />
                        <Route path="/inventory" element={<Inventory />} />
                        <Route path="/combat" element={<CombatArena />} />
                    </Routes>
                </div>
            </div>
        </Router>
    );
}

export default App;
