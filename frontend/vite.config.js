import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
    plugins: [react()],
    server: {
        port: 3000,
        proxy: {
            '/api/players': {
                target: 'http://localhost:8081',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api\/players/, '/api/players')
            },
            '/api/inventory': {
                target: 'http://localhost:8082',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api\/inventory/, '/api/inventory')
            },
            '/api/combat': {
                target: 'http://localhost:8083',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api\/combat/, '/api/combat')
            }
        }
    }
})
