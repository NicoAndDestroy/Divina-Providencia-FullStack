import API_URL from './api'

async function sendAuthRequest(endpoint, credentials) {
  const response = await fetch(`${API_URL}/auth/${endpoint}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(credentials),
  })

  const data = await response.json()

  if (!response.ok) {
    throw new Error(
      data.message || 'Ocurrió un error durante la autenticación.'
    )
  }

  return data
}

export function registerUser(credentials) {
  return sendAuthRequest('register', credentials)
}

export function loginUser(credentials) {
  return sendAuthRequest('login', credentials)
}