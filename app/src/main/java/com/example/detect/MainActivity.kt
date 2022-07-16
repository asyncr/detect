package com.example.detect

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.detect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var elements: MutableList<TextView>
    private var information: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.hide() // Oculta la barra de acciones
        obtenerDatos()
        detectarCambio(binding.edtA)
        binding.btnSecond.setOnClickListener { iniciarIntent(BaseNumerica::class.java) }
        binding.btnValidar.setOnClickListener { iniciarIntent(Form::class.java) }
        terminarAplicacion()
    }

    private fun iniciarIntent(clase: Class<*>) {
        Intent(this, clase).apply { startActivity(this) }
    }

    private fun obtenerDatos() =with(binding) { elements = mutableListOf(tvA, tvB, tvC, tvD, tvE, tvF) }

    private fun detectarCambio(editText: EditText) = with(editText) {
        addTextChangedListener(object : TextWatcher { //Añade un escuchador de cambios
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(newText: CharSequence?,p1: Int,p2: Int,p3: Int) { //Cuando cambia el texto
                information = editText.text.toString().uppercase() //Obtiene el texto en mayusculas
                validarTexto(information) //Valida el texto
            }

            override fun afterTextChanged(newText: Editable?) { seleccionarTexto() }
        })
    }

    fun validarTexto(texto: String) = if (texto.isNotEmpty()) enviarTexto(texto) else clear()

    private fun enviarTexto(texto: String) {
        elements.forEachIndexed { i, elem ->
            val hashEspecial = obtenerTipoSHA(i) // Obtiene el tipo de hash
            val resultado = obtenerSHA(hashEspecial, texto) // Obtiene el hash del texto
            elem.text = resultado // Muestra el hash en el elemento correspondiente
        }
    }

    private fun obtenerSHA(hashEspecial: String, texto: String) = Hash.generarHash(hashEspecial, texto)

    private fun obtenerTipoSHA(index: Int): String {
        return when (index) {
            0 -> "SHA-1"
            1 -> "SHA-256"
            2 -> "SHA-512"
            3 -> "MD5"
            4 -> "SHA-224"
            5 -> "SHA-384"
            else -> "Error"
        }
    }

    private fun clear() = elements.forEach { it.text = "" }

    private fun seleccionarTexto() = elements.forEach { it.setTextIsSelectable(true) }

    private fun terminarAplicacion(){
        if (intent.getBooleanExtra("EXIT", false)) finish()
    }
    //Implementation of Hash Generator, Numeric Base Converter and validation of form
}