/**
 * Sección principal de presentación del proyecto.
 *
 * Introduce al usuario a Divina Providencia
 * y lo dirige hacia la oferta de alojamientos.
 */
function HeroSection() {
  return (
    <section id="inicio" className="hero">
      <div className="container hero-content">
        <p className="eyebrow">Naturaleza · descanso · experiencias</p>

        <h1>Un espacio para desconectarte y disfrutar</h1>

        <p className="hero-description">
          Descubre nuestros alojamientos y encuentra el espacio ideal
          para compartir, descansar y vivir nuevas experiencias.
        </p>

        <a className="primary-button" href="#alojamientos">
          Ver alojamientos
        </a>
      </div>
    </section>
  )
}

export default HeroSection