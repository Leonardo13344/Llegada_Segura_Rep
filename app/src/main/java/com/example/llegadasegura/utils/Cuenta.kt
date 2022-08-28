package com.example.llegadasegura.utils
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.llegadasegura.databinding.CuentaConfiguracionBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.InterruptedIOException

class Cuenta: AppCompatActivity() {
    private lateinit var binding: CuentaConfiguracionBinding
    private lateinit var ImagenUri: Uri
    private lateinit var correo:String
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        super.onCreate(savedInstanceState)
        binding = CuentaConfiguracionBinding.inflate(layoutInflater)
        val telefono = bundle?.getString("telefono")
        val nombre = bundle?.getString("nombre")
        val apellido = bundle?.getString("apellido")
        correo = bundle?.getString("correo").toString()
        binding.txtNombre.hint = nombre
        binding.txtApellido.hint = apellido
        binding.txtNumero.hint = telefono
        cargarImagen(correo)
        setContentView(binding.root)
        binding.btnFoto.setOnClickListener{
            seleccionarImagen()
        }
        binding.btnSubir.setOnClickListener {
            subirImagen(correo)
            try {
                Thread.sleep(1000)
            }catch (e: InterruptedIOException){
                e.printStackTrace()
            }
            cargarImagen(correo)
        }

        if(binding.btnEditarDatos.text.toString() =="EDITAR DATOS"){
            binding.btnEditarDatos.setOnClickListener{
                activarCasillas()
                binding.btnEditarDatos.text="CONFIRMAR"
                Log.d("boton",binding.btnEditarDatos.text.toString() )
                Log.d("botonVerdad",(binding.btnEditarDatos.text.toString() =="EDITAR DATOS").toString())
            }
        }else /*if(binding.btnEditarDatos.text.toString() =="CONFIRMAR")*/ {
            Log.d("EntraCD", "Entra a cambiar los datos")
            binding.btnEditarDatos.setOnClickListener {
                cambiarDatos(
                    binding.txtNombre.text.toString(), binding.txtApellido.text.toString(),
                    binding.txtNumero.text.toString(), correo
                )
                db.collection("users").document(correo).get().addOnSuccessListener { document ->
                    if (document != null) {
                        binding.txtNombre.hint = document.data?.get("Nombre").toString()
                        binding.txtApellido.hint = document.data?.get("apellido").toString()
                        binding.txtNumero.hint = document.data?.get("telefono").toString()
                    } else {
                        Log.d("documento", "No se pudo cargar los datos")
                    }
                    Toast.makeText(this, "Los datos se han actualizado", Toast.LENGTH_LONG)
                }
            }
        }

    }

    fun subirImagen(correo:String) {
        val storageReference = FirebaseStorage.getInstance().getReference("images/$correo")
        storageReference.putFile(ImagenUri).addOnSuccessListener {
            binding.imgFoto.setImageURI(null)
            Toast.makeText(this,"Se subio con exito", Toast.LENGTH_LONG).show()

        }.addOnFailureListener{
            Toast.makeText(this,"No se subio", Toast.LENGTH_LONG).show()
        }
    }

    fun cargarImagen(correo:String){

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Cargando datos...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val storageRef = FirebaseStorage.getInstance().reference.child("images/$correo")
        val localfile = File.createTempFile("tempImage",".jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            if(progressDialog.isShowing){
                progressDialog.dismiss()
            }
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imgFoto.setImageBitmap(bitmap)

        }.addOnFailureListener{
            Toast.makeText(this, "No se pudo cargar la imagen", Toast.LENGTH_LONG).show()
        }
    }
    fun seleccionarImagen(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK){
            ImagenUri = data?.data!!
            binding.imgFoto.setImageURI(ImagenUri)
        }
    }
    fun cambiarDatos(nombre: String, apellido:String, telefono:String, correo:String){
        db.collection("users").document(correo).set(
            hashMapOf("Nombre" to nombre,
            "apellido" to apellido,
            "telefono" to telefono)
        )

    }
    fun activarCasillas(){
        binding.txtApellido.isFocusable = true
        binding.txtApellido.isClickable = true
        binding.txtApellido.isFocusableInTouchMode = true
        binding.txtApellido.hint =""
        binding.txtNombre.isClickable = true
        binding.txtNombre.isFocusable = true
        binding.txtNombre.isFocusableInTouchMode = true
        binding.txtNombre.hint =""
        binding.txtNumero.isClickable = true
        binding.txtNumero.isFocusable = true
        binding.txtNumero.isFocusableInTouchMode = true
        binding.txtNumero.hint =""
        Toast.makeText(this, "Modifique sus datos", Toast.LENGTH_LONG).show()
    }

}