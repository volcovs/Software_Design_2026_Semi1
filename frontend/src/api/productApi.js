import { PRODUCT_API } from '../config/urls.js'
import { httpJson } from './http.js'

export function listProducts() {
  return httpJson(`${PRODUCT_API}/api/products`)
}

export function getProduct(id) {
  return httpJson(`${PRODUCT_API}/api/products/${id}`)
}

export function createProduct(payload) {
  return httpJson(`${PRODUCT_API}/api/products`, {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}

export function updateProduct(id, payload) {
  return httpJson(`${PRODUCT_API}/api/products/${id}`, {
    method: 'PUT',
    body: JSON.stringify(payload),
  })
}

export function deleteProduct(id) {
  return httpJson(`${PRODUCT_API}/api/products/${id}`, { method: 'DELETE' })
}
