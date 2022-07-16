package com.example.detect

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.detect.databinding.ActivityBaseNumericBinding

class BaseNumerica : AppCompatActivity() {
    private lateinit var binding: ActivityBaseNumericBinding
    private lateinit var elements: MutableList<EditText>
    private var information: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseNumericBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.hide() // Oculta la barra de acciones
        obtenerDatos()
        detectarFocus()
        binding.btnPrimary.setOnClickListener { regresar() }
        binding.btnEnd.setOnClickListener { finishAffinity() }
    }

    private fun obtenerDatos() = with(binding) { elements = mutableListOf(edtA, edtB, edtC, edtD) }

    private fun detectarFocus() {
        elements.forEachIndexed { i, it -> // it = EditText actual que se esta escribiendo
            it.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    it.setSelection(it.text.length)//Enviar el cursor al final del texto
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    it.setSelection(it.text.length)
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    it.setSelection(it.text.length) //Enviar el cursor al final del texto
                    information = it.text.toString() // Guardar el texto en una variable
                    if (it.hasFocus() && information.isNotEmpty() ) {//Si el editText tiene el foco y el texto no esta vacio
                        val base: Int = obtenerBase(i) // Obtener la base del numero que se esta escribiendo
                        if (validarNumero(information,base)) { // Validar el numero
                            enviarTexto(information, i, base) // Enviar el texto al editText correspondiente
                        }
                    }
                    if (information.isEmpty()) clear(i) //Limpia los campos si no hay valor
                }
            })
        }
    }

    private fun enviarTexto(numero: String, index: Int, base: Int) {
        elements.forEachIndexed { i, elem ->
            if (i != index) {
                val baseSiguiente = obtenerBase(i) // Obtener la base del numero que se esta escribiendo
                val resultado = obtenerFuncion(base, baseSiguiente, numero) // Obtener el resultado de la funcion
                elem.setText(resultado) // Enviar el resultado al editText correspondiente
            }
        }
    }

    private fun obtenerFuncion(base: Int, baseEspecial: Int, numero: String): String {
        return when (base) {
            2 -> Converter.binaryToAll(baseEspecial, numero)
            8 -> Converter.octalToAll(baseEspecial, numero)
            10 -> Converter.decimaltoAll(baseEspecial, numero.toInt())
            16 -> Converter.hexaToAll(baseEspecial, numero)
            else -> "Error"
        }
    }

    private fun obtenerBase(index: Int): Int {
        return when (index) {
            0 -> 10
            1 -> 2
            2 -> 16
            3 -> 8
            else -> 0
        }
    }

    private fun obtenerRegex(base: Int) = when (base) {
        2, 8 -> "^[0-1]*$"
        10 -> "^[0-9]*$"
        16 -> "^[0-9A-F]*$"
        else -> ""
    }

    private fun validarNumero(number: String, base: Int) = validarRegex(number, obtenerRegex(base))

    val validarRegex = { numero: String, regexBase: String -> Regex(regexBase).containsMatchIn(numero) }

    private fun clear(i: Int) = elements.forEachIndexed { index, elem -> if (i != index) elem.text.clear() }

    private fun regresar() = Intent(this, MainActivity::class.java).apply {
        finish()
        startActivity(this)
    }
}