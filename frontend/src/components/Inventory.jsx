import React, { useState, useEffect } from 'react';
import { inventoryService } from '../services/api';
import { useNavigate } from 'react-router-dom';

const Inventory = () => {
    const [items, setItems] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetchInventory();
    }, []);

    const fetchInventory = async () => {
        try {
            const response = await inventoryService.get('');
            setItems(response.data);
        } catch (error) {
            console.error('Error fetching inventory:', error);
        }
    };

    const createRandomItem = async () => {
        try {
            await inventoryService.post('/items', { name: 'Potion', weight: 0.5 });
            fetchInventory();
        } catch (error) {
            console.error('Error creating item:', error);
        }
    };

    return (
        <div className="card">
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
                <h2>Inventory</h2>
                <button onClick={createRandomItem}>
                    + Add Random Item
                </button>
            </div>

            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(200px, 1fr))', gap: '1rem' }}>
                {items.length === 0 ? (
                    <p style={{ color: 'var(--text-muted)', gridColumn: '1/-1', textAlign: 'center' }}>
                        Your inventory is empty. Go find some loot!
                    </p>
                ) : (
                    items.map(item => (
                        <div key={item.id} style={{
                            background: 'rgba(15, 23, 42, 0.4)',
                            padding: '1rem',
                            borderRadius: '12px',
                            border: '1px solid var(--border)',
                            display: 'flex',
                            alignItems: 'center',
                            gap: '0.5rem'
                        }}>
                            <span style={{ fontSize: '1.5rem' }}>{item.type === 'bag' ? '👜' : '🗡️'}</span>
                            <div>
                                <div style={{ fontWeight: 'bold' }}>{item.name}</div>
                                <div style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>{item.weight}kg</div>
                            </div>
                        </div>
                    ))
                )}
            </div>

            <div style={{ marginTop: '2rem', textAlign: 'center' }}>
                <button onClick={() => navigate('/combat')} style={{ background: 'var(--secondary)' }}>
                    Enter Arena ⚔️
                </button>
            </div>
        </div>
    );
};

export default Inventory;
