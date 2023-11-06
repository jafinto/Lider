package com.example.lider

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityNoPerecederos : AppCompatActivity() {

    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noperecederos)

        val codigoEditText: EditText = findViewById(R.id.codigoNoPerecederoEditText)
        val descripcionEditText: EditText = findViewById(R.id.descripcionNoPerecederoEditText)
        val tipoProductoEditText: EditText = findViewById(R.id.tipoProductoEditText)
        val crearNoPerecederoButton: Button = findViewById(R.id.crearNoPerecederoButton)

        //sharedpreferences
        sp = getSharedPreferences("productos", Context.MODE_PRIVATE)

        crearNoPerecederoButton.setOnClickListener {
            val codigo = codigoEditText.text.toString()
            val descripcion = descripcionEditText.text.toString()
            val tipoProducto = tipoProductoEditText.text.toString()

            if (codigo.isEmpty() || descripcion.isEmpty() || tipoProducto.isEmpty()) {
                Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show()
            } else {
                val editor = sp.edit()
                val productoString = "$codigo|$descripcion|$tipoProducto"
                editor.putString(codigo, productoString)
                editor.apply()

                Toast.makeText(this, "Producto no perecedero creado.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

