package com.example.lider

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityPerecederos : AppCompatActivity() {

    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perecederos)

        val codigoEditText: EditText = findViewById(R.id.codigoPerecederoEditText)
        val descripcionEditText: EditText = findViewById(R.id.descripcionPerecederoEditText)
        val diasParaVencerEditText: EditText = findViewById(R.id.diasParaVencerEditText)
        val nuevoValorEditText: EditText = findViewById(R.id.nuevoValorPerecederoEditText)
        val calcularNuevoValorButton: Button = findViewById(R.id.calcularNuevoValorButton)
        val crearPerecederoButton: Button = findViewById(R.id.crearPerecederoButton)

        sp = getSharedPreferences("productos", Context.MODE_PRIVATE)

        calcularNuevoValorButton.setOnClickListener {
            val diasParaVencer = diasParaVencerEditText.text.toString().toIntOrNull()

            if (diasParaVencer != null) {
                val precioOriginal = nuevoValorEditText.text.toString().toDoubleOrNull() ?: 0.0

                val nuevoValor: Double = when (diasParaVencer) {
                    1 -> precioOriginal / 4
                    2 -> precioOriginal / 3
                    3 -> precioOriginal / 2
                    else -> precioOriginal
                }

                nuevoValorEditText.setText(nuevoValor.toString())
            } else {
                Toast.makeText(this, "Ingrese un número válido de días para vencer", Toast.LENGTH_SHORT).show()
            }
        }

        crearPerecederoButton.setOnClickListener {
            val codigo = codigoEditText.text.toString()
            val descripcion = descripcionEditText.text.toString()
            val diasParaVencer = diasParaVencerEditText.text.toString().toInt()
            val nuevoValor = nuevoValorEditText.text.toString().toDouble()

            if (codigo.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show()
            } else {
                val editor = sp.edit()
                val productoString = "$codigo|$descripcion|$diasParaVencer|$nuevoValor"
                editor.putString(codigo, productoString)
                editor.apply()

                Toast.makeText(this, "Producto perecedero creado con éxito.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
