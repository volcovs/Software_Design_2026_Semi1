export async function httpJson(url, options = {}) {
  const res = await fetch(url, {
    headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
    ...options,
  })
  if (res.status === 204) {
    return null
  }
  const text = await res.text()
  const body = text ? JSON.parse(text) : null
  if (!res.ok) {
    const msg = body?.message || body?.error || res.statusText
    throw new Error(msg || `HTTP ${res.status}`)
  }
  return body
}
