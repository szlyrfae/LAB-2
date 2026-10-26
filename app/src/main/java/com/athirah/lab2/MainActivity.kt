package com.athirah.lab2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editNama = findViewById<EditText>(R.id.editNama)
        val editTarikh = findViewById<EditText>(R.id.editTarikh)
        val btnAnalisis = findViewById<Button>(R.id.btnAnalisis)

        btnAnalisis.setOnClickListener {
            val nama = editNama.text.toString().trim()
            val tarikh = editTarikh.text.toString().trim()

            if (nama.isEmpty() || tarikh.isEmpty()) {
                Toast.makeText(this, "Please enter your name and date of birth", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, AnalysisActivity::class.java)
                intent.putExtra("EXTRA_NAME", nama)
                intent.putExtra("EXTRA_DATE", tarikh)
                startActivity(intent)
            }
        }
    }
}
