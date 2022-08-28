package com.example.llegadasegura.principal.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.llegadasegura.Clases.MapCoor
import com.example.llegadasegura.R
import com.example.llegadasegura.databinding.FragmentMapaBinding
import com.example.llegadasegura.grupo.grupos_join
import com.example.llegadasegura.principal.PrincipalActivity
import com.example.llegadasegura.utils.MyCallback
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MapaFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {
    // TODO: Rename and change types of parameters

    private lateinit var map: GoogleMap
    private lateinit var binding: FragmentMapaBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var db: DatabaseReference
    private lateinit var mapCoor: MapCoor
    private lateinit var grupo: grupos_join
    private val dbStore = Firebase.firestore


    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Initialize view
        binding = FragmentMapaBinding.inflate(layoutInflater, container, false)
        createFragment()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        db = FirebaseDatabase.getInstance().getReference("usuarios")
        grupo = grupos_join()
        lastKnownLocation()
        validateMembers("1")
        return binding.root
    }


    private fun lastKnownLocation() {
        if (!isAdded) {
            Log.e("Location", "Fragment Not Added to the Principal Activity")
            return
        } else {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    Log.e(
                        "Location",
                        "Longitud: " + location?.longitude + "Latitud: " + location?.latitude
                    )
                    Log.e("Location", db.child(getMail()).key.toString())
                    mapCoor = MapCoor(
                        location?.latitude!!.toDouble(),
                        location?.longitude!!.toDouble(),
                        getMail()
                    )
                    db.child(getMail()).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                Log.e("Location", "Updating Coords...")
                                updateCoor(
                                    getMail(),
                                    location?.latitude.toString(),
                                    location?.longitude.toString()
                                )
                                updateCoordsEveryTenSecs()

                            } else {
                                Log.e("Location", "Inserting Coords...")
                                insertCoor(
                                    getMail(),
                                    location?.latitude.toString(),
                                    location?.longitude.toString()
                                )
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            throw error.toException()
                        }
                    })

                }
        }
    }

    private fun insertCoor(mail: String, lat: String, lang: String) {
        val latLang: HashMap<String, String> = HashMap<String, String>()
        latLang["Longitud"] = lang
        latLang["Latitud"] = lat
        db.child(mail).setValue(latLang).addOnCompleteListener() {
            Log.e("Location", "Correct Insert")
        }
    }

    private fun updateCoor(mail: String, lat: String, lang: String) {
        val latLang: HashMap<String, String> = HashMap()
        latLang["Longitud"] = lang
        latLang["Latitud"] = lat
        db.child(mail).updateChildren(latLang as Map<String, String>).addOnCompleteListener() {
            Log.e("Location", "Correct Update")
        }

    }

    private fun updateCoordsEveryTenSecs() {
        object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                Log.e("LocationCount", "T: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                Log.e("LocationCount", "Count Down Out")
                lastKnownLocation()
                validateMembers("1")
            }
        }.start()
    }

    private fun getMail(): String {
        return if (!isAdded) {
            ""
        } else {
            val prefs =
                this.requireActivity().getSharedPreferences("loginData", Context.MODE_PRIVATE)
            prefs.getString("email", null).toString().replace(".", "!")
        }

    }

    private fun validate(groupMembers: Task<QuerySnapshot>, myCallback: MyCallback) {
        groupMembers.addOnCompleteListener { task ->
            val list = mutableListOf<String>()
            for (document in task.result) {
                val correo = document.id.toString()
                list.add(correo)
            }
            val mail = getMail().replace("!", ".")
            if (list.contains(mail)) {
                myCallback.onCallback(true)
            } else {
                myCallback.onCallback(false)
            }
        }

    }

    private fun validateMembers(id: String) {
        val allMembersStore = dbStore.collection("grupos").document(id).collection("Miembros").get()
        validate(allMembersStore, object : MyCallback {
            override fun onCallback(value: Boolean) {
                if (value) {
                    allMembersStore.addOnSuccessListener { documents ->
                        for (user in documents) {
                            db.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (ds in snapshot.children) {
                                        var userR = ds.key.toString()
                                        Log.d("Deb", userR)
                                        db.child(userR).get().addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                val snap = task.result
                                                val lat = snap.child("Latitud")
                                                    .getValue(String::class.java)
                                                val lang = snap.child("Longitud")
                                                    .getValue(String::class.java)
                                                Log.d("DataStore", "$lat , $lang")
                                                val coords = LatLng(
                                                    lat.toString().toDouble(),
                                                    lang.toString().toDouble()
                                                )
                                                Log.d("Mails","${user.id} , ${getMail().replace("!",".")}")
                                                if (user.id != getMail().replace("!",".")) {
                                                    drawMember(map, coords)
                                                }
                                            } else {
                                                Log.d("DataStore", task.exception!!.message!!)
                                            }
                                        }

                                    }
                                }
                                override fun onCancelled(error: DatabaseError) {
                                }
                            })
                        }
                    }
                } else {
                    return
                }
            }

        })

    }


    private fun drawMember(map: GoogleMap, coords: LatLng) {
        map.addMarker(
            MarkerOptions()
                .position(coords)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun createFragment() {
        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        animateCamera()
        enableLocation()
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
    }


    private fun animateCamera() {
        val coordinates = LatLng(-0.1438661237117817, -78.45302369572558)
        //val marker = MarkerOptions().position(coordinates).title("Quito - Ecuador")
        //map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 10f),
            4000,
            null
        )
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(
                requireContext(),
                "Ve a ajustes y acepta los permisos",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PrincipalActivity.REQUEST_CODE_LOCATION
            )
        }
    }

    @SuppressLint("MissingSuperCall", "MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PrincipalActivity.REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    requireContext(),
                    "Para activar la localización ve a ajuster y acepta los permisos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
        }
    }


    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(requireContext(), "Redireccionando...", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {//el p0 guarda la direccion en latitud y longitud
        Toast.makeText(
            requireContext(),
            "Estas en ${p0.latitude}, ${p0.longitude}",
            Toast.LENGTH_SHORT
        ).show()
    }

}