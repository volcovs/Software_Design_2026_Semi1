import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5174,
    proxy: {
      '/product-api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        rewrite: (p) => p.replace(/^\/product-api/, ''),
      },
      '/order-api': {
        target: 'http://localhost:8082',
        changeOrigin: true,
        rewrite: (p) => p.replace(/^\/order-api/, ''),
      },
    },
  },
})
