package com.example.plantas

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class Actividad2 : FragmentActivity() {

    private val viewModel: Actividad2ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad2)

        // Se accede al ViewModel para asegurar su inicializacion en la actividad.
        viewModel.fragmentText.value

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SoyUnFragmentoFragment())
                .commit()
        }

        val navigationMenu = findViewById<BottomNavigationView>(R.id.activity2_bottom_nav)
        navigationMenu.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.nav_actividad1) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                true
            } else {
                false
            }
        }
    }
}
