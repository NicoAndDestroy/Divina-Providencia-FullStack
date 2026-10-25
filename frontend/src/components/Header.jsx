/**
 * Encabezado principal de Divina Providencia.
 *
 * Contiene la identidad del proyecto y los enlaces
 * principales de navegación.
 */
function Header() {
  return (
    <header className="site-header">
      <div className="container header-content">
        <a className="brand" href="#inicio">
          Divina Providencia
        </a>

        <nav
          className="main-navigation"
          aria-label="Navegación principal"
        >
          <a href="#alojamientos">Alojamientos</a>
          <a href="#reservar">Reservar</a>
          <a href="#contacto">Contacto</a>
        </nav>
      </div>
    </header>
  )
}

export default Header