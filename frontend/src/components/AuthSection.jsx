import { useState } from 'react'
import {
  loginUser,
  registerUser,
} from '../services/authService'

/**
 * Sección para registro e inicio de sesión
 * utilizando la API de autenticación.
 */
function AuthSection() {
  const [credentials, setCredentials] = useState({
    username: '',
    password: '',
  })

  const [message, setMessage] = useState('')
  const [error, setError] = useState('')
  const [loadingAction, setLoadingAction] = useState('')

  const handleChange = (event) => {
    const { name, value } = event.target

    setCredentials((previousCredentials) => ({
      ...previousCredentials,
      [name]: value,
    }))
  }

  const validateCredentials = () => {
    if (!credentials.username.trim()) {
      setError('El nombre de usuario es obligatorio.')
      return false
    }

    if (!credentials.password) {
      setError('La contraseña es obligatoria.')
      return false
    }

    return true
  }

  const handleRegister = async () => {
    if (!validateCredentials()) {
      return
    }

    try {
      setLoadingAction('register')
      setMessage('')
      setError('')

      const response = await registerUser(credentials)

      setMessage(response.message)
    } catch (requestError) {
      setError(requestError.message)
    } finally {
      setLoadingAction('')
    }
  }

  const handleLogin = async (event) => {
    event.preventDefault()

    if (!validateCredentials()) {
      return
    }

    try {
      setLoadingAction('login')
      setMessage('')
      setError('')

      const response = await loginUser(credentials)

      setMessage(response.message)
    } catch (requestError) {
      setError(requestError.message)
    } finally {
      setLoadingAction('')
    }
  }

  return (
    <section id="acceso" className="section reservation-section">
      <div className="container">
        <div className="section-heading">
          <p className="eyebrow">Acceso de usuarios</p>

          <h2>Ingresa a Divina Providencia</h2>

          <p>
            Registra una cuenta o valida tus credenciales
            para acceder a los servicios de la aplicación.
          </p>
        </div>

        <form
          className="reservation-form"
          onSubmit={handleLogin}
          noValidate
        >
          <div className="form-grid">
            <div className="form-group">
              <label htmlFor="username">
                Nombre de usuario
              </label>

              <input
                id="username"
                name="username"
                type="text"
                value={credentials.username}
                onChange={handleChange}
                placeholder="Nombre de usuario"
                autoComplete="username"
              />
            </div>

            <div className="form-group">
              <label htmlFor="password">
                Contraseña
              </label>

              <input
                id="password"
                name="password"
                type="password"
                value={credentials.password}
                onChange={handleChange}
                placeholder="Contraseña"
                autoComplete="current-password"
              />
            </div>
          </div>

          <div className="auth-actions">
            <button
              className="primary-button"
              type="submit"
              disabled={loadingAction !== ''}
            >
              {loadingAction === 'login'
                ? 'Ingresando...'
                : 'Iniciar sesión'}
            </button>

            <button
              className="primary-button"
              type="button"
              onClick={handleRegister}
              disabled={loadingAction !== ''}
            >
              {loadingAction === 'register'
                ? 'Registrando...'
                : 'Crear cuenta'}
            </button>
          </div>

          {message && (
            <p className="success-message">
              {message}
            </p>
          )}

          {error && (
            <p className="error-message">
              {error}
            </p>
          )}
        </form>
      </div>
    </section>
  )
}

export default AuthSection