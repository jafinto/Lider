package com.example.lider

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val codigoEditText = findViewById<EditText>(R.id.codigoEditText)
        val descripcionEditText = findViewById<EditText>(R.id.descripcionEditText)
        val precioEditText = findViewById<EditText>(R.id.precioEditText)
        val perecederoCheckBox = findViewById<CheckBox>(R.id.perecederoCheckBox)
        val crearButton = findViewById<Button>(R.id.crearButton)

        sp = getSharedPreferences("productos", Context.MODE_PRIVATE)

        data class ProductoPerecedero(val codigo: String, val descripcion: String, val precio: Double) : Serializable
        data class ProductoNoPerecedero(val codigo: String, val descripcion: String, val precio: Double) : Serializable

        crearButton.setOnClickListener {
            val codigo = codigoEditText.text.toString()
            val descripcion = descripcionEditText.text.toString()
            val precio = precioEditText.text.toString().toDoubleOrNull()
            val esPerecedero = perecederoCheckBox.isChecked

            if (codigo.isEmpty() || descripcion.isEmpty() || precio == null) {
                Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val producto: Serializable = if (esPerecedero) {
                ProductoPerecedero(codigo, descripcion, precio)
            } else {
                ProductoNoPerecedero(codigo, descripcion, precio)
            }

            val editor = sp.edit()
            editor.putString(codigo, producto.toString())
            editor.apply()

            Toast.makeText(this, "Producto creado con éxito", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, if (esPerecedero) ActivityPerecederos::class.java else ActivityNoPerecederos::class.java)
            intent.putExtra("producto", producto)
            startActivity(intent)
        }
    }
}

