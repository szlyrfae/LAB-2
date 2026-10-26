package com.athirah.lab2

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AnalysisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_analysis)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textNama = findViewById<TextView>(R.id.textNama)
        val textTarikh = findViewById<TextView>(R.id.textTarikh)

        val nama = intent.getStringExtra("EXTRA_NAME") ?: "No name input"
        val tarikh = intent.getStringExtra("EXTRA_DATE") ?: ""

        val namaTanpaSpace = nama.replace(" ", "")
        val jumlahHuruf = namaTanpaSpace.length
        val jumlahVokal = namaTanpaSpace.filter { it.lowercaseChar() in "aeiou" }.count()
        val hurufBerulang = namaTanpaSpace.lowercase()
            .groupBy { it }
            .filter { it.value.size > 1 }
            .keys
            .joinToString(", ")

        val formatTarikh = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formatTarikh.isLenient = false

        try {
            val dateLahir = formatTarikh.parse(tarikh)
            if (dateLahir == null) {
                Toast.makeText(this, "Enter the date as yyyy-MM-dd", Toast.LENGTH_LONG).show()
                finish()
                return
            }

            val hariLahir = SimpleDateFormat("EEEE", Locale.getDefault()).format(dateLahir)
            val birthday = Calendar.getInstance()
            birthday.time = dateLahir
            val today = Calendar.getInstance()
            var umur = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR)

            if (today.get(Calendar.MONTH) < birthday.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == birthday.get(Calendar.MONTH) &&
                    today.get(Calendar.DAY_OF_MONTH) < birthday.get(Calendar.DAY_OF_MONTH))) {
                umur--
            }

            if (umur < 0) {
                Toast.makeText(this, "Date of birth cannot be in the future", Toast.LENGTH_LONG).show()
                finish()
                return
            }

            val repeatedText = if (hurufBerulang.isEmpty()) "None" else hurufBerulang
            textNama.text = "Name: $nama\nNumber of characters: $jumlahHuruf\nNumber of vowels: $jumlahVokal\nRepeated characters: $repeatedText"
            textTarikh.text = "Age: $umur years\nDay of birth: $hariLahir"
        } catch (e: Exception) {
            Toast.makeText(this, "Enter the date as yyyy-MM-dd", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
