import React, { useState, useEffect } from 'react';
import { combatService } from '../services/api';
import goblinImg from '../assets/goblin.png';
import orclinImg from '../assets/orclin.png';
import orcImg from '../assets/orc.png';

const CombatArena = () => {
    const [state, setState] = useState(null);

    useEffect(() => {
        fetchState();
    }, []);

    const fetchState = async () => {
        try {
            const response = await combatService.get('/state');
            setState(response.data);
        } catch (error) {
            console.error('Error fetching state:', error);
        }
    };

    const fight = async (move) => {
        try {
            const response = await combatService.post('/fight', { move });
            setState(response.data);
        } catch (error) {
            console.error('Error fighting:', error);
        }
    };

    const heal = async () => {
        try {
            const response = await combatService.post('/heal');
            setState(response.data);
        } catch (error) {
            console.error('Error healing:', error);
        }
    };

    const reset = async () => {
        try {
            const response = await combatService.post('/reset');
            setState(response.data);
        } catch (error) {
            console.error('Error resetting:', error);
        }
    };


    if (!state) return <div className="text-center p-8">Loading Arena...</div>;

    // Get enemy image based on round
    const getEnemyImage = () => {
        if (state.currentRound === 1) return goblinImg;
        if (state.currentRound === 2) return orclinImg;
        return orcImg; // Round 3 - Boss
    };

    const getEnemyName = () => {
        if (state.currentRound === 1) return 'Goblin';
        if (state.currentRound === 2) return 'Orclin';
        return '👑 ORC BOSS';
    };

    return (
        <div className="card" style={{ maxWidth: '800px', margin: '0 auto' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
                <h2 style={{ margin: 0 }}>Round {state.currentRound} / {state.maxRounds}</h2>
                <button onClick={reset} style={{ background: 'var(--bg-dark)', border: '1px solid var(--border)', padding: '0.5rem 1rem', fontSize: '0.9rem' }}>
                    Reset Game
                </button>
            </div>

            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '2rem', gap: '2rem' }}>
                {/* Player Stats */}
                <div style={{ flex: 1, background: 'rgba(59, 130, 246, 0.1)', padding: '1rem', borderRadius: '12px', border: '1px solid rgba(59, 130, 246, 0.3)' }}>
                    <h3 style={{ color: '#60a5fa' }}>HERO ({state.playerClass})</h3>
                    <div style={{ fontSize: '2rem' }}>
                        {'❤️'.repeat(Math.max(0, state.playerHearts))}
                        <span style={{ opacity: 0.3 }}>{'🖤'.repeat(Math.max(0, state.playerMaxHearts - state.playerHearts))}</span>
                    </div>
                    <div style={{ fontSize: '0.9rem', color: 'var(--text-muted)', marginTop: '0.5rem' }}>
                        DMG: {state.playerDamage} per clash
                    </div>
                    <button
                        onClick={heal}
                        disabled={state.playerHearts >= state.playerMaxHearts || state.status !== 'ONGOING'}
                        style={{ marginTop: '1rem', width: '100%', background: 'rgba(16, 185, 129, 0.2)', color: '#34d399', border: '1px solid rgba(16, 185, 129, 0.4)' }}
                    >
                        🧪 Use Potion (+1 HP)
                    </button>
                </div>

                {/* VS */}
                <div style={{ display: 'flex', alignItems: 'center', fontSize: '2rem', fontWeight: 'bold', color: 'var(--text-muted)' }}>VS</div>

                {/* Enemy Stats */}
                <div style={{ flex: 1, background: 'rgba(239, 68, 68, 0.1)', padding: '1rem', borderRadius: '12px', border: '1px solid rgba(239, 68, 68, 0.3)', textAlign: 'center' }}>
                    <h3 style={{ color: '#f87171' }}>
                        {getEnemyName()}
                    </h3>
                    <img
                        src={getEnemyImage()}
                        alt="Enemy"
                        style={{
                            width: '120px',
                            height: '120px',
                            objectFit: 'contain',
                            imageRendering: 'pixelated',
                            margin: '0.5rem auto'
                        }}
                    />
                    <div style={{ fontSize: '2rem' }}>
                        {'❤️'.repeat(Math.max(0, state.enemyHearts))}
                        <span style={{ opacity: 0.3 }}>{'🖤'.repeat(Math.max(0, state.enemyMaxHearts - state.enemyHearts))}</span>
                    </div>
                </div>
            </div>

            {state.status === 'ONGOING' ? (
                <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '1rem', marginBottom: '2rem' }}>
                    <button
                        onClick={() => fight('ROCK')}
                        style={{ background: 'linear-gradient(135deg, #64748b 0%, #475569 100%)', height: '100px', fontSize: '1.2rem' }}
                    >
                        🪨 ROCK
                    </button>
                    <button
                        onClick={() => fight('PAPER')}
                        style={{ background: 'linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%)', color: '#0f172a', height: '100px', fontSize: '1.2rem' }}
                    >
                        📜 PAPER
                    </button>
                    <button
                        onClick={() => fight('SCISSORS')}
                        style={{ background: 'linear-gradient(135deg, #ef4444 0%, #b91c1c 100%)', height: '100px', fontSize: '1.2rem' }}
                    >
                        ✂️ SCISSORS
                    </button>
                </div>
            ) : (
                <div style={{ textAlign: 'center', padding: '2rem', background: state.status === 'VICTORY' ? 'rgba(16, 185, 129, 0.2)' : 'rgba(239, 68, 68, 0.2)', borderRadius: '12px', marginBottom: '2rem' }}>
                    <h2 style={{ color: state.status === 'VICTORY' ? '#34d399' : '#f87171', fontSize: '2.5rem', marginBottom: '1rem' }}>
                        {state.status === 'VICTORY' ? '🏆 VICTORY! 🏆' : '💀 DEFEAT 💀'}
                    </h2>
                    {state.status === 'VICTORY' && <p>You have claimed the CROW!</p>}
                    <button onClick={reset} style={{ marginTop: '1rem' }}>Play Again</button>
                </div>
            )}

            <div style={{
                background: 'rgba(0, 0, 0, 0.3)',
                borderRadius: '12px',
                padding: '1rem',
                height: '200px',
                overflowY: 'auto',
                fontFamily: 'monospace',
                border: '1px solid var(--border)'
            }}>
                {state.logs.length === 0 ? (
                    <div style={{ color: 'var(--text-muted)', textAlign: 'center', marginTop: '4rem' }}>
                        Combat log is empty. Choose your move!
                    </div>
                ) : (
                    state.logs.map((log, index) => (
                        <div key={index} style={{ marginBottom: '0.5rem', borderBottom: '1px solid rgba(255,255,255,0.05)', paddingBottom: '0.25rem' }}>
                            <span style={{ color: 'var(--primary-glow)' }}>&gt;</span> {log}
                        </div>
                    ))
                )}
            </div>
        </div>
    );
};

export default CombatArena;
