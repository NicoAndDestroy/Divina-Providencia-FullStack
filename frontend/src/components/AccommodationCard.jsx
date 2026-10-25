/**
 * Tarjeta reutilizable para representar un alojamiento.
 *
 * @param {Object} accommodation Información del alojamiento.
 * @param {Function} onSelect Función ejecutada al seleccionar
 *                            un alojamiento.
 */
function AccommodationCard({ accommodation, onSelect }) {
  const price =
    accommodation.pricePerNight ??
    accommodation.price ??
    0

  const formattedPrice = new Intl.NumberFormat('es-CO', {
    style: 'currency',
    currency: 'COP',
    maximumFractionDigits: 0,
  }).format(price)

  const accommodationCode =
    accommodation.code ??
    `DP${String(accommodation.id).padStart(3, '0')}`

  return (
    <article className="accommodation-card">
      <div className="accommodation-code">
        {accommodationCode}
      </div>

      <h3>{accommodation.name}</h3>

      <p>{accommodation.description}</p>

      <div className="accommodation-meta">
        <span>
          Capacidad: {accommodation.capacity}{' '}
          {accommodation.capacity === 1 ? 'persona' : 'personas'}
        </span>

        <strong>{formattedPrice} / noche</strong>
      </div>

      <button
        className="primary-button"
        type="button"
        onClick={() => onSelect(accommodation)}
      >
        Seleccionar
      </button>
    </article>
  )
}

export default AccommodationCard