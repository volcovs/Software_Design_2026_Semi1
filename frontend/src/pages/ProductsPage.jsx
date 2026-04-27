import { useCallback, useEffect, useState } from 'react'
import { createProduct, deleteProduct, listProducts, updateProduct } from '../api/productApi.js'

const defaultForm = { name: '', description: '', price: '', stockQuantity: '' }

function fromApi(p) {
  if (!p) {
    return null
  }
  return {
    id: p.Id,
    name: p.name ?? '',
    description: p.description ?? '',
    price: p.price,
    stockQuantity: p.stock_quantity,
  }
}

export function ProductsPage() {
  const [items, setItems] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [editingId, setEditingId] = useState(null)
  const [form, setForm] = useState(defaultForm)

  const load = useCallback(async () => {
    setLoading(true)
    setError('')
    try {
      const raw = await listProducts()
      setItems((Array.isArray(raw) ? raw : []).map(fromApi).filter(Boolean))
    } catch (e) {
      setError(e.message)
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    load()
  }, [load])

  async function onSave() {
    setError('')
    const payload = {
      name: form.name.trim(),
      description: (form.description || '').trim() || null,
      price: Number(form.price),
      stock_quantity: Number(form.stockQuantity),
    }
    try {
      if (editingId) {
        await updateProduct(editingId, payload)
      } else {
        await createProduct(payload)
      }
      setForm(defaultForm)
      setEditingId(null)
      await load()
    } catch (e) {
      setError(e.message)
    }
  }

  async function onDelete(id) {
    if (!confirm('Delete this product?')) {
      return
    }
    setError('')
    try {
      await deleteProduct(id)
      if (editingId === id) {
        setEditingId(null)
        setForm(defaultForm)
      }
      await load()
    } catch (e) {
      setError(e.message)
    }
  }

  function startEdit(p) {
    setEditingId(p.id)
    setForm({
      name: p.name,
      description: p.description ?? '',
      price: p.price != null ? String(p.price) : '',
      stockQuantity: p.stockQuantity != null ? String(p.stockQuantity) : '',
    })
  }

  return (
    <section>
      <h1 style={{ marginTop: 0, fontSize: '1.5rem' }}>Products</h1>
      <p className="muted" style={{ marginTop: 0 }}>
        Data from <code>product-microservice</code> — <code>GET/POST /api/products</code>,{' '}
        <code>PUT/DELETE /api/products/&#123;id&#125;</code>
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
                <th>Id</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Stock</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {items.length === 0 && !loading ? (
                <tr>
                  <td colSpan={6} className="muted">
                    No products yet
                  </td>
                </tr>
              ) : (
                items.map((p) => (
                  <tr key={p.id}>
                    <td>{p.id}</td>
                    <td>{p.name}</td>
                    <td>{p.description}</td>
                    <td>{p.price != null ? Number(p.price).toFixed(2) : '—'}</td>
                    <td>{p.stockQuantity ?? '—'}</td>
                    <td>
                      <div className="row">
                        <button type="button" onClick={() => startEdit(p)}>
                          Edit
                        </button>
                        <button type="button" onClick={() => onDelete(p.id)}>
                          Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      <div className="panel">
        <h2 style={{ marginTop: 0, fontSize: '1.15rem' }}>{editingId ? `Edit #${editingId}` : 'New product'}</h2>
        <div className="form-grid">
          <label className="stack">
            Name
            <input value={form.name} onChange={(e) => setForm((f) => ({ ...f, name: e.target.value }))} />
          </label>
          <label className="stack" style={{ gridColumn: '1 / -1' }}>
            Description
            <input
              value={form.description}
              onChange={(e) => setForm((f) => ({ ...f, description: e.target.value }))}
            />
          </label>
          <label className="stack">
            Price
            <input
              type="number"
              step="0.01"
              min="0"
              value={form.price}
              onChange={(e) => setForm((f) => ({ ...f, price: e.target.value }))}
            />
          </label>
          <label className="stack">
            Stock
            <input
              type="number"
              min="0"
              value={form.stockQuantity}
              onChange={(e) => setForm((f) => ({ ...f, stockQuantity: e.target.value }))}
            />
          </label>
        </div>
        <div className="row" style={{ marginTop: '0.75rem' }}>
          <button type="button" onClick={onSave}>
            Save
          </button>
          {editingId && (
            <button
              type="button"
              onClick={() => {
                setEditingId(null)
                setForm(defaultForm)
              }}
            >
              Cancel
            </button>
          )}
        </div>
      </div>
    </section>
  )
}
