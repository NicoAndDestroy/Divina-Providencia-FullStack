import API_URL from './api'

export async function createReservation(reservation) {
  const response = await fetch(`${API_URL}/reservations`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(reservation),
  })

  if (!response.ok) {
    throw new Error('No fue posible crear la reserva.')
  }

  return response.json()
}