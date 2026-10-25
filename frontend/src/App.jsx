import { useState } from 'react'
import './App.css'

import Header from './components/Header'
import HeroSection from './components/HeroSection'
import AccommodationSection from './components/AccommodationSection'
import ReservationForm from './components/ReservationForm'
import Footer from './components/Footer'

import { accommodations } from './data/accommodations'

/**
 * Componente principal de Divina Providencia.
 *
 * Coordina los componentes generales de la aplicación
 * y administra el alojamiento seleccionado por el usuario.
 */
function App() {
  const [selectedAccommodation, setSelectedAccommodation] =
    useState(null)

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

        <AccommodationSection
          accommodations={accommodations}
          onSelect={handleAccommodationSelect}
        />

        <ReservationForm
          selectedAccommodation={selectedAccommodation}
        />
      </main>

      <Footer />
    </>
  )
}

export default App