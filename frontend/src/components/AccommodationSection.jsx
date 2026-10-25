import AccommodationCard from './AccommodationCard'

/**
 * Sección encargada de mostrar el catálogo de alojamientos.
 *
 * @param {Array} accommodations Lista de alojamientos.
 * @param {Function} onSelect Función para seleccionar alojamiento.
 */
function AccommodationSection({ accommodations, onSelect }) {
  return (
    <section id="alojamientos" className="section">
      <div className="container">
        <div className="section-heading">
          <p className="eyebrow">Nuestros espacios</p>
          <h2>Encuentra tu alojamiento ideal</h2>

          <p>
            Selecciona el espacio que mejor se adapte al número de
            personas y al tipo de experiencia que deseas disfrutar.
          </p>
        </div>

        <div className="accommodation-grid">
          {accommodations.map((accommodation) => (
            <AccommodationCard
              key={accommodation.id}
              accommodation={accommodation}
              onSelect={onSelect}
            />
          ))}
        </div>
      </div>
    </section>
  )
}

export default AccommodationSection