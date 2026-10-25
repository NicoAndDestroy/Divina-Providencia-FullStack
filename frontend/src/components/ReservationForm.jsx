import { useState } from 'react'

/**
 * Formulario utilizado para realizar una solicitud inicial
 * de reserva sobre el alojamiento seleccionado.
 *
 * @param {Object|null} selectedAccommodation Alojamiento seleccionado.
 */
function ReservationForm({ selectedAccommodation }) {
  const [formData, setFormData] = useState({
    checkIn: '',
    checkOut: '',
    guests: 1,
  })

  const [errors, setErrors] = useState({})
  const [message, setMessage] = useState('')

  /**
   * Actualiza el estado del formulario cada vez que
   * el usuario modifica uno de sus campos.
   */
  const handleChange = (event) => {
    const { name, value } = event.target

    setFormData((previousData) => ({
      ...previousData,
      [name]: value,
    }))
  }

  /**
   * Comprueba que los datos del formulario cumplan
   * con los requisitos básicos de una reserva.
   */
  const validateForm = () => {
    const validationErrors = {}

    if (!selectedAccommodation) {
      validationErrors.accommodation =
        'Debes seleccionar un alojamiento.'
    }

    if (!formData.checkIn) {
      validationErrors.checkIn =
        'La fecha de entrada es obligatoria.'
    }

    if (!formData.checkOut) {
      validationErrors.checkOut =
        'La fecha de salida es obligatoria.'
    }

    if (
      formData.checkIn &&
      formData.checkOut &&
      formData.checkOut <= formData.checkIn
    ) {
      validationErrors.checkOut =
        'La fecha de salida debe ser posterior a la fecha de entrada.'
    }

    const guests = Number(formData.guests)

    if (guests < 1) {
      validationErrors.guests =
        'Debe registrarse al menos un huésped.'
    }

    if (
      selectedAccommodation &&
      guests > selectedAccommodation.capacity
    ) {
      validationErrors.guests =
        `La capacidad máxima de este alojamiento es de ${selectedAccommodation.capacity} personas.`
    }

    return validationErrors
  }

  /**
   * Procesa el envío del formulario después de ejecutar
   * las validaciones.
   */
  const handleSubmit = (event) => {
    event.preventDefault()

    const validationErrors = validateForm()

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors)
      setMessage('')
      return
    }

    setErrors({})

    setMessage(
      `Solicitud registrada para ${selectedAccommodation.name}.`
    )
  }

  return (
    <section id="reservar" className="section reservation-section">
      <div className="container">
        <div className="section-heading">
          <p className="eyebrow">Planea tu estadía</p>
          <h2>Solicita tu reserva</h2>

          <p>
            Selecciona un alojamiento y completa la información
            básica de tu estadía.
          </p>
        </div>

        <form
          className="reservation-form"
          onSubmit={handleSubmit}
          noValidate
        >
          <div className="form-group">
            <label>Alojamiento seleccionado</label>

            <div className="selected-accommodation">
              {selectedAccommodation
                ? selectedAccommodation.name
                : 'Ningún alojamiento seleccionado'}
            </div>

            {errors.accommodation && (
              <p className="error-message">
                {errors.accommodation}
              </p>
            )}
          </div>

          <div className="form-grid">
            <div className="form-group">
              <label htmlFor="checkIn">
                Fecha de entrada
              </label>

              <input
                id="checkIn"
                name="checkIn"
                type="date"
                value={formData.checkIn}
                onChange={handleChange}
              />

              {errors.checkIn && (
                <p className="error-message">
                  {errors.checkIn}
                </p>
              )}
            </div>

            <div className="form-group">
              <label htmlFor="checkOut">
                Fecha de salida
              </label>

              <input
                id="checkOut"
                name="checkOut"
                type="date"
                value={formData.checkOut}
                onChange={handleChange}
              />

              {errors.checkOut && (
                <p className="error-message">
                  {errors.checkOut}
                </p>
              )}
            </div>

            <div className="form-group">
              <label htmlFor="guests">
                Número de huéspedes
              </label>

              <input
                id="guests"
                name="guests"
                type="number"
                min="1"
                value={formData.guests}
                onChange={handleChange}
              />

              {errors.guests && (
                <p className="error-message">
                  {errors.guests}
                </p>
              )}
            </div>
          </div>

          <button
            className="primary-button"
            type="submit"
          >
            Solicitar reserva
          </button>

          {message && (
            <p className="success-message">
              {message}
            </p>
          )}
        </form>
      </div>
    </section>
  )
}

export default ReservationForm