import { useCallback, useEffect, useState } from 'react'
import { createOrder, deleteOrder, listOrders } from '../api/orderApi.js'
import { listProducts } from '../api/productApi.js'
import { OrderComposer } from '../components/OrderComposer.jsx'

function fromProduct(p) {
  if (!p) {
    return null
  }
  return {
    id: p.Id,
    name: p.name ?? '',
    price: p.price,
    stockQuantity: p.stock_quantity,
  }
}

function fromOrder(o) {
  if (!o) {
    return null
  }
  return {
    id: o.Id,
    customerName: o.Customer_Name ?? '',
    lines: (o.lines || []).map((l) => ({
      id: l.Id,
      productId: l.Product_Id,
      quantity: l.Quantity,
      unitPrice: l.Unit_Price,
    })),
  }
}

export function OrdersPage() {
  const [orders, setOrders] = useState([])
  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const load = useCallback(async () => {
    setLoading(true)
    setError('')
    try {
      const [orderRes, productRes] = await Promise.all([listOrders(), listProducts()])
      setOrders((Array.isArray(orderRes) ? orderRes : []).map(fromOrder).filter(Boolean))
      setProducts((Array.isArray(productRes) ? productRes : []).map(fromProduct).filter(Boolean))
    } catch (e) {
      setError(e.message)
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    load()
  }, [load])

  async function onDelete(id) {
    if (!confirm('Delete this order?')) {
      return
    }
    setError('')
    try {
      await deleteOrder(id)
      await load()
    } catch (e) {
      setError(e.message)
    }
  }

  return (
    <section>
      <h1 style={{ marginTop: 0, fontSize: '1.5rem' }}>Orders</h1>
      <p className="muted" style={{ marginTop: 0 }}>
        Data from <code>orders-microservice</code> — <code>GET/POST /api/orders</code>,{' '}
        <code>PATCH/DELETE /api/orders/&#123;id&#125;</code>. Create order lines use the same idea as
        bakery-demo, with JSON fields <code>customer_name</code> and <code>Product_Id</code> /{' '}
        <code>Quantity</code> / <code>Unit_Price</code> on each line.
      </p>
      {error && <p className="error">{error}</p>}

      <div className="panel">
        <div className="row" style={{ marginBottom: '0.5rem' }}>
          <button type="button" onClick={load} disabled={loading}>
            Refresh
          </button>
          {loading && <span className="muted">Loading…</span>}
        </div>
        <div style={{ overflowX: 'auto' }}>
          <table>
            <thead>
              <tr>
                <th>Order</th>
                <th>Customer</th>
                <th>Lines</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {orders.length === 0 && !loading ? (
                <tr>
                  <td colSpan={4} className="muted">
                    No orders yet
                  </td>
                </tr>
              ) : (
                orders.map((o) => (
                  <tr key={o.id}>
                    <td>#{o.id}</td>
                    <td>{o.customerName}</td>
                    <td>
                      <ul className="line-list">
                        {o.lines.length === 0 && <li className="muted">(no lines)</li>}
                        {o.lines.map((l) => (
                          <li key={l.id ?? `${o.id}-${l.productId}`}>
                            product {l.productId} × {l.quantity} @ {l.unitPrice != null ? l.unitPrice.toFixed(2) : '—'}
                          </li>
                        ))}
                      </ul>
                    </td>
                    <td>
                      <button type="button" onClick={() => onDelete(o.id)}>
                        Delete
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      <div className="panel">
        <h2 style={{ marginTop: 0, fontSize: '1.15rem' }}>New order</h2>
        {products.length === 0 && (
          <p className="muted">Add at least one product on the Products tab to build order lines.</p>
        )}
        <OrderComposer
          products={products}
          onCreate={async (payload) => {
            setError('')
            try {
              await createOrder(payload)
              await load()
            } catch (e) {
              setError(e.message)
            }
          }}
        />
      </div>
    </section>
  )
}
