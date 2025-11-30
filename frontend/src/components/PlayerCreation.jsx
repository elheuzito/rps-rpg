import React, { useState } from 'react';
import { playerService, combatService } from '../services/api';
import { useNavigate } from 'react-router-dom';

const PlayerCreation = () => {
    const [name, setName] = useState('');
    const [classType, setClassType] = useState('Warrior');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await playerService.post('', { name, classType });
            localStorage.setItem('playerId', response.data.id);
            localStorage.setItem('playerClass', classType);

            // Set class in combat service
            await combatService.post('/set-class', { class: classType });

            alert(`Player Created: ${response.data.name} (${response.data.characterClass})`);
            navigate('/combat');
        } catch (error) {
            console.error('Error creating player:', error);
            alert('Failed to create player');
        }
    };

    return (
        <div className="card" style={{ maxWidth: '500px', margin: '0 auto' }}>
            <h2 className="text-center">Create Your Hero</h2>
            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
                <div>
                    <label>Hero Name</label>
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        placeholder="Enter your hero's name..."
                        required
                    />
                </div>
                <div>
                    <label>Class Archetype</label>
                    <select
                        value={classType}
                        onChange={(e) => setClassType(e.target.value)}
                    >
                        <option value="Warrior">⚔️ Warrior (5 HP, 1 DMG)</option>
                        <option value="Mage">🔮 Mage (3 HP, 2 DMG)</option>
                    </select>
                </div>
                <button type="submit" style={{ marginTop: '1rem' }}>
                    Begin Adventure
                </button>
            </form>
        </div>
    );
};

export default PlayerCreation;
