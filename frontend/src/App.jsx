import { useState } from 'react'
import { OrdersPage } from './pages/OrdersPage.jsx'
import { ProductsPage } from './pages/ProductsPage.jsx'

export default function App() {
  const [tab, setTab] = useState('products')

  return (
    <div className="app-shell">
      <header className="app-header">
        <h1 className="app-title">Bakery microservices</h1>
        <p className="muted app-sub">Simple UI for <code>product-microservice</code> (port 8081) and <code>orders-microservice</code> (port 8082)</p>
        <nav className="tabs" aria-label="Sections">
          <button
            type="button"
            className={tab === 'products' ? 'tab active' : 'tab'}
            onClick={() => setTab('products')}
          >
            Products
          </button>
          <button
            type="button"
            className={tab === 'orders' ? 'tab active' : 'tab'}
            onClick={() => setTab('orders')}
          >
            Orders
          </button>
        </nav>
      </header>
      <main className="app-main">{tab === 'products' ? <ProductsPage /> : <OrdersPage />}</main>
    </div>
  )
}
