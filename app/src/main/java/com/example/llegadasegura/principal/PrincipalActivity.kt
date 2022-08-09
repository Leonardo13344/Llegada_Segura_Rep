package com.example.llegadasegura.principal


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.llegadasegura.R
import com.example.llegadasegura.databinding.ActivityPrincipalBinding
import com.example.llegadasegura.principal.fragments.ConfigurationFragment
import com.example.llegadasegura.principal.fragments.DirectionsFragment
import com.example.llegadasegura.principal.fragments.GroupsFragment
import com.example.llegadasegura.principal.fragments.MapaFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PrincipalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrincipalBinding



    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        val bundle= intent.extras
        val correo = bundle?.getString("correo")
        val db = Firebase.firestore
        val datosUsuario = db.collection("users").document(correo.toString()).get()
        datosUsuario.addOnSuccessListener {
            usuario ->
            Log.d("DatosUsuario", "${usuario.data}")
        }
        setContentView(binding.root)
        //createFragment()

        val configurationFragment = ConfigurationFragment()
        val mapaFragment = MapaFragment()
        val directionsFragment = DirectionsFragment()
        val gruposFragment = GroupsFragment()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_mapa -> {
                    setCurrentFragment(mapaFragment)
                    true
                }
                R.id.nav_dirs -> {
                    setCurrentFragment(directionsFragment)
                    true
                }
                R.id.nav_settings -> {
                    setCurrentFragment(configurationFragment)
                    true
                }
                R.id.nav_grupos -> {
                    setCurrentFragment(gruposFragment)
                    true
                }
                else -> false
            }

        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.slide_in,R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            replace(R.id.cointainerView,fragment)
            commit()

        }
    }

   /* private fun irGrupos(){
        val intent = Intent(this, Grupos::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }*/



}