import { useEffect, useState } from 'react'
import './App.css'

import Header from './components/Header'
import HeroSection from './components/HeroSection'
import AccommodationSection from './components/AccommodationSection'
import ReservationForm from './components/ReservationForm'
import Footer from './components/Footer'
import AuthSection from './components/AuthSection'

import { getAccommodations } from './services/accommodationService'

/**
 * Componente principal de Divina Providencia.
 *
 * Coordina los componentes generales de la aplicación,
 * consume los alojamientos desde la API y administra
 * el alojamiento seleccionado por el usuario.
 */
function App() {
  const [accommodations, setAccommodations] = useState([])
  const [selectedAccommodation, setSelectedAccommodation] =
    useState(null)

  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  /**
   * Consulta los alojamientos del backend
   * cuando se carga la aplicación.
   */
  useEffect(() => {
    const loadAccommodations = async () => {
      try {
        setLoading(true)
        setError('')

        const data = await getAccommodations()

        setAccommodations(data)
      } catch (err) {
        console.error('Error al cargar alojamientos:', err)

        setError(
          'No fue posible cargar los alojamientos. Intenta nuevamente.'
        )
      } finally {
        setLoading(false)
      }
    }

    loadAccommodations()
  }, [])

  /**
   * Actualiza el alojamiento seleccionado y dirige
   * al usuario hacia el formulario de reserva.
   *
   * @param {Object} accommodation Alojamiento seleccionado.
   */
  const handleAccommodationSelect = (accommodation) => {
    setSelectedAccommodation(accommodation)

    setTimeout(() => {
      document
        .getElementById('reservar')
        ?.scrollIntoView({
          behavior: 'smooth',
        })
    }, 0)
  }

  return (
    <>
      <Header />

      <main>
        <HeroSection />
        <AuthSection />

        {loading && (
          <section className="section">
            <div className="container">
              <p>Cargando alojamientos...</p>
            </div>
          </section>
        )}

        {error && (
          <section className="section">
            <div className="container">
              <p>{error}</p>
            </div>
          </section>
        )}

        {!loading && !error && (
          <AccommodationSection
            accommodations={accommodations}
            onSelect={handleAccommodationSelect}
          />
        )}

        <ReservationForm
          selectedAccommodation={selectedAccommodation}
        />
      </main>

      <Footer />
    </>
  )
}

export default App