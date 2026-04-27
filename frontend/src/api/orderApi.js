import { ORDER_API } from '../config/urls.js'
import { httpJson } from './http.js'

export function listOrders() {
  return httpJson(`${ORDER_API}/api/orders`)
}

export function getOrder(id) {
  return httpJson(`${ORDER_API}/api/orders/${id}`)
}

export function createOrder(payload) {
  return httpJson(`${ORDER_API}/api/orders`, {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}

export function updateOrder(id, payload) {
  return httpJson(`${ORDER_API}/api/orders/${id}`, {
    method: 'PATCH',
    body: JSON.stringify(payload),
  })
}

export function deleteOrder(id) {
  return httpJson(`${ORDER_API}/api/orders/${id}`, { method: 'DELETE' })
}
