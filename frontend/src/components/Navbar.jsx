import React from 'react';
import { NavLink } from 'react-router-dom';

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="nav-container">
                <div className="nav-logo">RPS RPG</div>
                <div className="nav-links">
                    <NavLink to="/create-player" className={({ isActive }) => isActive ? "nav-link active" : "nav-link"}>
                        Create Hero
                    </NavLink>
                    <NavLink to="/inventory" className={({ isActive }) => isActive ? "nav-link active" : "nav-link"}>
                        Inventory
                    </NavLink>
                    <NavLink to="/combat" className={({ isActive }) => isActive ? "nav-link active" : "nav-link"}>
                        Combat Arena
                    </NavLink>
                </div>
            </div>
            <style>{`
                .navbar {
                    background: rgba(15, 23, 42, 0.8);
                    backdrop-filter: blur(12px);
                    border-bottom: 1px solid var(--glass-border);
                    position: sticky;
                    top: 0;
                    z-index: 100;
                    padding: 1rem 0;
                    margin-bottom: 2rem;
                }
                .nav-container {
                    max-width: 1200px;
                    margin: 0 auto;
                    padding: 0 2rem;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                }
                .nav-logo {
                    font-size: 1.5rem;
                    font-weight: 800;
                    background: var(--gradient-main);
                    -webkit-background-clip: text;
                    background-clip: text;
                    color: transparent;
                    letter-spacing: -1px;
                }
                .nav-links {
                    display: flex;
                    gap: 1rem;
                }
                .nav-link {
                    color: var(--text-muted);
                    text-decoration: none;
                    font-weight: 500;
                    padding: 0.5rem 1rem;
                    border-radius: 8px;
                    transition: all 0.3s ease;
                }
                .nav-link:hover {
                    color: var(--text-main);
                    background: rgba(255, 255, 255, 0.05);
                }
                .nav-link.active {
                    color: white;
                    background: var(--primary);
                    box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
                }
            `}</style>
        </nav>
    );
};

export default Navbar;
