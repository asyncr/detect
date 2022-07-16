package com.example.detect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.detect.databinding.ActivityFormBinding

class Form : AppCompatActivity() {
    private lateinit var binding:ActivityFormBinding
    private lateinit var elements: MutableList<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.hide() // Oculta la barra de acciones
        crearElementos() // Crea los elementos de la vista
        detectarCambios() // Detecta los cambios en los elementos de la vista
        validarElementos() // Valida los elementos de la vista
        binding.btnPrimary.apply { isEnabled = false ; setOnClickListener { regresar() } } // Deshabilita el boton de regresar hasta que se llene todo el formulario
        binding.btnEnd.setOnClickListener { finishAffinity() } // Cierra la aplicacion
    }

    private fun crearElementos() = with(binding){ elements = mutableListOf( edtA,edtB,edtC,edtD,edtF,edtE) }

    private fun detectarCambios() = elements.forEach { it.addTextChangedListener(textwatcher())}

    private fun textwatcher(): TextWatcher? {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnPrimary.isEnabled =  validarElementos()
            }
        }
    }

    private fun validarElementos():Boolean{
        /*elements.forEach {
            it.setOnFocusChangeListener { view, hasFocus ->
                if(!hasFocus){
                    it.error = if(it.text.isEmpty()) "Campo requerido" else null
                }
            }
        }*/
        elements.forEach { if (it.text.isEmpty()) return false }
        return true
    }

    fun regresar() {
        Intent(this, MainActivity::class.java).apply {
            finish()
            startActivity(this)
        }
    }
}