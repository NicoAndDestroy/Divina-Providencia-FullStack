package com.example.divinaprovidencia

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.divinaprovidencia.data.model.ReservationRequest
import com.example.divinaprovidencia.viewmodel.AccommodationViewModel
import com.example.divinaprovidencia.viewmodel.AuthViewModel
import com.example.divinaprovidencia.viewmodel.ReservationViewModel
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var root: LinearLayout

    private enum class AuthAction {
        NONE,
        LOGIN,
        REGISTER
    }

    private var currentAuthAction = AuthAction.NONE

    private val accommodationViewModel: AccommodationViewModel by lazy {
        ViewModelProvider(this)[AccommodationViewModel::class.java]
    }

    private val authViewModel: AuthViewModel by lazy {
        ViewModelProvider(this)[AuthViewModel::class.java]
    }

    private val reservationViewModel: ReservationViewModel by lazy {
        ViewModelProvider(this)[ReservationViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLogin()
    }

    private fun baseLayout(
        title: String
    ): LinearLayout {

        root = LinearLayout(this)

        root.orientation =
            LinearLayout.VERTICAL

        root.setPadding(
            32,
            48,
            32,
            32
        )

        root.setBackgroundColor(
            Color.rgb(
                244,
                239,
                230
            )
        )

        val titleView =
            TextView(this)

        titleView.text =
            title

        titleView.textSize =
            26f

        titleView.setTextColor(
            Color.rgb(
                31,
                61,
                43
            )
        )

        titleView.gravity =
            Gravity.CENTER

        titleView.setPadding(
            0,
            0,
            0,
            24
        )

        root.addView(titleView)

        return root
    }

    private fun button(
        text: String,
        action: () -> Unit
    ): Button {

        val btn =
            Button(this)

        btn.text =
            text

        btn.setOnClickListener {
            action()
        }

        root.addView(btn)

        return btn
    }

    private fun text(
        content: String,
        size: Float = 16f
    ) {

        val view =
            TextView(this)

        view.text =
            content

        view.textSize =
            size

        view.setTextColor(
            Color.rgb(
                36,
                49,
                40
            )
        )

        view.setPadding(
            0,
            12,
            0,
            12
        )

        root.addView(view)
    }

    private fun card(
        content: String
    ) {

        val card =
            TextView(this)

        card.text =
            content

        card.textSize =
            16f

        card.setTextColor(
            Color.rgb(
                36,
                49,
                40
            )
        )

        card.setBackgroundColor(
            Color.WHITE
        )

        card.setPadding(
            24,
            24,
            24,
            24
        )

        val params =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

        params.setMargins(
            0,
            12,
            0,
            12
        )

        root.addView(
            card,
            params
        )
    }

    // =========================================================
    // AUTENTICACIÓN
    // =========================================================

    private fun showLogin() {

        currentAuthAction =
            AuthAction.NONE

        authViewModel.clearMessages()

        val layout =
            baseLayout(
                "Divina Providencia"
            )

        text(
            "Inicio de sesión",
            20f
        )

        text(
            "Ingresa con tu cuenta o crea un nuevo usuario."
        )

        val username =
            EditText(this)

        username.hint =
            "Usuario"

        username.inputType =
            InputType.TYPE_CLASS_TEXT

        layout.addView(username)

        val password =
            EditText(this)

        password.hint =
            "Contraseña"

        password.inputType =
            InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_PASSWORD

        layout.addView(password)

        val progressBar =
            ProgressBar(this)

        progressBar.visibility =
            View.GONE

        val progressParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

        progressParams.gravity =
            Gravity.CENTER

        layout.addView(
            progressBar,
            progressParams
        )

        val statusText =
            TextView(this)

        statusText.textSize =
            14f

        statusText.setPadding(
            0,
            12,
            0,
            12
        )

        layout.addView(statusText)

        val loginButton =
            button(
                "Ingresar"
            ) {

                currentAuthAction =
                    AuthAction.LOGIN

                statusText.text =
                    ""

                authViewModel.login(
                    username.text
                        .toString()
                        .trim(),
                    password.text
                        .toString()
                )
            }

        val registerButton =
            button(
                "Crear cuenta"
            ) {

                currentAuthAction =
                    AuthAction.REGISTER

                statusText.text =
                    ""

                authViewModel.register(
                    username.text
                        .toString()
                        .trim(),
                    password.text
                        .toString()
                )
            }

        val guestButton =
            button(
                "Continuar como invitado"
            ) {
                showHome()
            }

        authViewModel.loading
            .removeObservers(this)

        authViewModel.error
            .removeObservers(this)

        authViewModel.successMessage
            .removeObservers(this)

        authViewModel.loading.observe(
            this
        ) { loading ->

            progressBar.visibility =
                if (loading) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

            loginButton.isEnabled =
                !loading

            registerButton.isEnabled =
                !loading

            guestButton.isEnabled =
                !loading
        }

        authViewModel.error.observe(
            this
        ) { error ->

            if (!error.isNullOrBlank()) {

                statusText.text =
                    error

                statusText.setTextColor(
                    Color.rgb(
                        179,
                        38,
                        30
                    )
                )
            }
        }

        authViewModel.successMessage.observe(
            this
        ) { message ->

            if (!message.isNullOrBlank()) {

                statusText.text =
                    message

                statusText.setTextColor(
                    Color.rgb(
                        31,
                        110,
                        60
                    )
                )

                Toast.makeText(
                    this,
                    message,
                    Toast.LENGTH_LONG
                ).show()

                when (currentAuthAction) {

                    AuthAction.LOGIN -> {

                        authViewModel
                            .clearMessages()

                        currentAuthAction =
                            AuthAction.NONE

                        showHome()
                    }

                    AuthAction.REGISTER -> {

                        authViewModel
                            .clearMessages()

                        currentAuthAction =
                            AuthAction.NONE

                        username.text.clear()
                        password.text.clear()

                        statusText.text =
                            "Cuenta creada. Ahora puedes iniciar sesión."
                    }

                    AuthAction.NONE -> {
                        // Sin acción.
                    }
                }
            }
        }

        setContentView(layout)
    }

    // =========================================================
    // HOME
    // =========================================================

    private fun showHome() {

        val layout =
            baseLayout(
                "Inicio"
            )

        text(
            "Bienvenido a Divina Providencia",
            20f
        )

        text(
            "Explora alojamientos, experiencias y realiza tu solicitud de reserva desde la aplicación móvil."
        )

        button(
            "Ver alojamientos"
        ) {
            showAlojamientos()
        }

        button(
            "Ver experiencias"
        ) {
            showExperiencias()
        }

        button(
            "Reservar ahora"
        ) {
            showReserva()
        }

        button(
            "Contacto"
        ) {
            showContacto()
        }

        button(
            "Cerrar sesión"
        ) {
            showLogin()
        }

        setContentView(layout)
    }

    // =========================================================
    // ALOJAMIENTOS - API REAL
    // =========================================================

    private fun showAlojamientos() {

        setContentView(
            R.layout.activity_alojamientos
        )

        val progressBar =
            findViewById<ProgressBar>(
                R.id.progressBarAlojamientos
            )

        val txtError =
            findViewById<TextView>(
                R.id.txtErrorAlojamientos
            )

        val container =
            findViewById<LinearLayout>(
                R.id.containerAlojamientos
            )

        val btnVolver =
            findViewById<Button>(
                R.id.btnVolverHome
            )

        val currencyFormat =
            NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag(
                    "es-CO"
                )
            )

        accommodationViewModel.loading
            .removeObservers(this)

        accommodationViewModel.error
            .removeObservers(this)

        accommodationViewModel.accommodations
            .removeObservers(this)

        accommodationViewModel.loading.observe(
            this
        ) { loading ->

            progressBar.visibility =
                if (loading) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        accommodationViewModel.error.observe(
            this
        ) { error ->

            if (error.isNullOrBlank()) {

                txtError.visibility =
                    View.GONE

            } else {

                txtError.text =
                    error

                txtError.visibility =
                    View.VISIBLE
            }
        }

        accommodationViewModel.accommodations.observe(
            this
        ) { accommodations ->

            container.removeAllViews()

            if (accommodations.isEmpty()) {

                val emptyView =
                    TextView(this)

                emptyView.text =
                    "No hay alojamientos disponibles en este momento."

                emptyView.textSize =
                    16f

                emptyView.setTextColor(
                    Color.rgb(
                        35,
                        50,
                        43
                    )
                )

                emptyView.setPadding(
                    18,
                    18,
                    18,
                    18
                )

                container.addView(
                    emptyView
                )

                return@observe
            }

            accommodations.forEach { accommodation ->

                val cardView =
                    TextView(this)

                val price =
                    currencyFormat.format(
                        accommodation.pricePerNight
                    )

                val availability =
                    if (accommodation.available) {
                        "Sí"
                    } else {
                        "No"
                    }

                cardView.text =
                    """
                    ${accommodation.name}

                    ${accommodation.description}

                    Capacidad: ${accommodation.capacity} personas
                    Precio por noche: $price
                    Disponible: $availability
                    """.trimIndent()

                cardView.textSize =
                    16f

                cardView.setTextColor(
                    Color.rgb(
                        35,
                        50,
                        43
                    )
                )

                cardView.setBackgroundColor(
                    Color.WHITE
                )

                cardView.setPadding(
                    32,
                    28,
                    32,
                    28
                )

                val params =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                params.setMargins(
                    0,
                    12,
                    0,
                    12
                )

                container.addView(
                    cardView,
                    params
                )
            }
        }

        btnVolver.setOnClickListener {
            showHome()
        }

        accommodationViewModel
            .loadAccommodations()
    }

    // =========================================================
    // EXPERIENCIAS
    // =========================================================

    private fun showExperiencias() {

        val layout =
            baseLayout(
                "Experiencias"
            )

        card(
            """
            Fogatas al Aire Libre
            Actividad nocturna para compartir en grupo.
            """.trimIndent()
        )

        card(
            """
            Día de Piscina
            Espacio de relajación y entretenimiento.
            """.trimIndent()
        )

        card(
            """
            Cine Bajo las Estrellas
            Experiencia familiar en ambiente natural.
            """.trimIndent()
        )

        card(
            """
            Picnic Campestre
            Actividad al aire libre con alimentos y naturaleza.
            """.trimIndent()
        )

        card(
            """
            Avistamiento de Aves
            Experiencia de conexión con el entorno.
            """.trimIndent()
        )

        button(
            "Volver al inicio"
        ) {
            showHome()
        }

        setContentView(layout)
    }

    // =========================================================
    // RESERVA - API REAL
    // =========================================================

    private fun showReserva() {

        reservationViewModel
            .clearMessages()

        val layout =
            baseLayout(
                "Solicitud de Reserva"
            )

        text(
            "Alojamiento: Suite Campestre",
            18f
        )

        text(
            "Capacidad máxima: 4 personas"
        )

        val nombre =
            EditText(this)

        nombre.hint =
            "Nombre completo"

        nombre.inputType =
            InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_FLAG_CAP_WORDS

        layout.addView(nombre)

        val correo =
            EditText(this)

        correo.hint =
            "Correo electrónico"

        correo.inputType =
            InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        layout.addView(correo)

        val entrada =
            EditText(this)

        entrada.hint =
            "Fecha de entrada (AAAA-MM-DD)"

        entrada.inputType =
            InputType.TYPE_CLASS_DATETIME

        layout.addView(entrada)

        val salida =
            EditText(this)

        salida.hint =
            "Fecha de salida (AAAA-MM-DD)"

        salida.inputType =
            InputType.TYPE_CLASS_DATETIME

        layout.addView(salida)

        val huespedes =
            EditText(this)

        huespedes.hint =
            "Número de huéspedes (1 a 4)"

        huespedes.inputType =
            InputType.TYPE_CLASS_NUMBER

        layout.addView(huespedes)

        val progressBar =
            ProgressBar(this)

        progressBar.visibility =
            View.GONE

        val progressParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

        progressParams.gravity =
            Gravity.CENTER

        layout.addView(
            progressBar,
            progressParams
        )

        val statusText =
            TextView(this)

        statusText.textSize =
            14f

        statusText.setPadding(
            0,
            12,
            0,
            12
        )

        layout.addView(statusText)

        val submitButton =
            button(
                "Enviar solicitud"
            ) {

                statusText.text =
                    ""

                val guestName =
                    nombre.text
                        .toString()
                        .trim()

                val guestEmail =
                    correo.text
                        .toString()
                        .trim()

                val checkInText =
                    entrada.text
                        .toString()
                        .trim()

                val checkOutText =
                    salida.text
                        .toString()
                        .trim()

                val guestsText =
                    huespedes.text
                        .toString()
                        .trim()

                if (guestName.isBlank()) {

                    statusText.text =
                        "El nombre del huésped es obligatorio."

                    statusText.setTextColor(
                        Color.rgb(
                            179,
                            38,
                            30
                        )
                    )

                    return@button
                }

                if (
                    guestEmail.isBlank() ||
                    !Patterns.EMAIL_ADDRESS
                        .matcher(guestEmail)
                        .matches()
                ) {

                    statusText.text =
                        "Ingresa un correo electrónico válido."

                    statusText.setTextColor(
                        Color.rgb(
                            179,
                            38,
                            30
                        )
                    )

                    return@button
                }

                val numberOfGuests =
                    guestsText.toIntOrNull()

                if (
                    numberOfGuests == null ||
                    numberOfGuests < 1 ||
                    numberOfGuests > 4
                ) {

                    statusText.text =
                        "El número de huéspedes debe estar entre 1 y 4."

                    statusText.setTextColor(
                        Color.rgb(
                            179,
                            38,
                            30
                        )
                    )

                    return@button
                }

                val checkIn: LocalDate
                val checkOut: LocalDate

                try {

                    checkIn =
                        LocalDate.parse(
                            checkInText
                        )

                    checkOut =
                        LocalDate.parse(
                            checkOutText
                        )

                } catch (
                    e: DateTimeParseException
                ) {

                    statusText.text =
                        "Las fechas deben usar el formato AAAA-MM-DD."

                    statusText.setTextColor(
                        Color.rgb(
                            179,
                            38,
                            30
                        )
                    )

                    return@button
                }

                if (
                    !checkOut.isAfter(
                        checkIn
                    )
                ) {

                    statusText.text =
                        "La fecha de salida debe ser posterior a la fecha de entrada."

                    statusText.setTextColor(
                        Color.rgb(
                            179,
                            38,
                            30
                        )
                    )

                    return@button
                }

                val request =
                    ReservationRequest(
                        guestName =
                            guestName,
                        guestEmail =
                            guestEmail,
                        checkIn =
                            checkIn.toString(),
                        checkOut =
                            checkOut.toString(),
                        numberOfGuests =
                            numberOfGuests,
                        status =
                            "PENDING",
                        accommodationId =
                            1L
                    )

                reservationViewModel
                    .createReservation(
                        request
                    )
            }

        val backButton =
            button(
                "Volver al inicio"
            ) {
                showHome()
            }

        reservationViewModel.loading
            .removeObservers(this)

        reservationViewModel.error
            .removeObservers(this)

        reservationViewModel.successMessage
            .removeObservers(this)

        reservationViewModel.loading.observe(
            this
        ) { loading ->

            progressBar.visibility =
                if (loading) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

            submitButton.isEnabled =
                !loading

            backButton.isEnabled =
                !loading
        }

        reservationViewModel.error.observe(
            this
        ) { error ->

            if (!error.isNullOrBlank()) {

                statusText.text =
                    error

                statusText.setTextColor(
                    Color.rgb(
                        179,
                        38,
                        30
                    )
                )
            }
        }

        reservationViewModel.successMessage.observe(
            this
        ) { message ->

            if (!message.isNullOrBlank()) {

                reservationViewModel
                    .clearMessages()

                showConfirmacion(
                    message
                )
            }
        }

        setContentView(layout)
    }

    // =========================================================
    // CONFIRMACIÓN
    // =========================================================

    private fun showConfirmacion(
        message: String =
            "Reserva creada correctamente."
    ) {

        val layout =
            baseLayout(
                "Reserva Enviada"
            )

        text(
            "¡Solicitud registrada!",
            20f
        )

        text(message)

        text(
            "La reserva fue enviada al servidor de Divina Providencia."
        )

        button(
            "Ver alojamientos"
        ) {
            showAlojamientos()
        }

        button(
            "Volver al inicio"
        ) {
            showHome()
        }

        setContentView(layout)
    }

    // =========================================================
    // CONTACTO
    // =========================================================

    private fun showContacto() {

        val layout =
            baseLayout(
                "Contacto"
            )

        text(
            "Ubicación: zona campestre"
        )

        text(
            "WhatsApp: 300 000 0000"
        )

        text(
            "Correo: reservas@divinaprovidencia.com"
        )

        text(
            "Horario de atención: 8:00 a.m. - 6:00 p.m."
        )

        button(
            "Volver al inicio"
        ) {
            showHome()
        }

        setContentView(layout)
    }
}