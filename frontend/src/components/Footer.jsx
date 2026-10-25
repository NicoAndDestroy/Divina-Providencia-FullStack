/**
 * Pie de página principal de Divina Providencia.
 *
 * Presenta información básica del proyecto
 * y completa la estructura general de la aplicación.
 */
function Footer() {
  return (
    <footer id="contacto" className="site-footer">
      <div className="container footer-content">
        <div>
          <h2>Divina Providencia</h2>
          <p>
            Un espacio para descansar, compartir
            y disfrutar de la naturaleza.
          </p>
        </div>

        <div>
          <h3>Contacto</h3>
          <p>Colombia</p>
          <p>Reservas y alojamiento</p>
        </div>
      </div>

      <div className="footer-bottom">
        <p>
          © 2026 Divina Providencia.
          Proyecto formativo SENA.
        </p>
      </div>
    </footer>
  )
}

export default Footer