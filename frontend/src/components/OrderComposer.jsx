import { useMemo, useState } from 'react'

const emptyLine = () => ({ productId: '', quantity: '1', unitPrice: '' })

export function OrderComposer({ products, onCreate }) {
  const [customerName, setCustomerName] = useState('')
  const [lines, setLines] = useState([emptyLine()])

  const byId = useMemo(() => {
    const m = new Map()
    products.forEach((p) => m.set(String(p.id), p))
    return m
  }, [products])

  function setLine(i, patch) {
    setLines((prev) => prev.map((l, idx) => (idx === i ? { ...l, ...patch } : l)))
  }

  function onPickProduct(i, productId) {
    const p = byId.get(String(productId))
    setLine(i, {
      productId,
      unitPrice: p != null ? String(p.price) : '',
    })
  }

  async function handleCreate() {
    const linePayloads = lines
      .filter((l) => l.productId)
      .map((l) => ({
        Product_Id: Number(l.productId),
        Quantity: Number(l.quantity),
        Unit_Price: Number(l.unitPrice),
      }))

    const payload = {
      customer_name: customerName.trim(),
      lines: linePayloads,
    }

    if (!payload.customer_name) {
      return
    }
    if (!linePayloads.length) {
      return
    }
    await onCreate(payload)
    setCustomerName('')
    setLines([emptyLine()])
  }

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem' }}>
      <label className="stack">
        Customer name
        <input value={customerName} onChange={(e) => setCustomerName(e.target.value)} />
      </label>

      {lines.map((line, i) => (
        <div key={i} className="row" style={{ alignItems: 'flex-end', flexWrap: 'wrap' }}>
          <label className="stack" style={{ minWidth: 220 }}>
            Product
            <select value={line.productId} onChange={(e) => onPickProduct(i, e.target.value)}>
              <option value="">— select —</option>
              {products.map((p) => (
                <option key={p.id} value={p.id}>
                  #{p.id} {p.name} (stock {p.stockQuantity})
                </option>
              ))}
            </select>
          </label>
          <label className="stack" style={{ width: 80 }}>
            Qty
            <input
              value={line.quantity}
              onChange={(e) => setLine(i, { quantity: e.target.value })}
            />
          </label>
          <label className="stack" style={{ width: 110 }}>
            Unit price
            <input
              value={line.unitPrice}
              onChange={(e) => setLine(i, { unitPrice: e.target.value })}
            />
          </label>
          {lines.length > 1 && (
            <button type="button" onClick={() => setLines((prev) => prev.filter((_, idx) => idx !== i))}>
              Remove line
            </button>
          )}
        </div>
      ))}

      <div className="row">
        <button type="button" onClick={() => setLines((prev) => [...prev, emptyLine()])}>
          Add line
        </button>
        <button type="button" onClick={handleCreate}>
          Create order
        </button>
      </div>
    </div>
  )
}
