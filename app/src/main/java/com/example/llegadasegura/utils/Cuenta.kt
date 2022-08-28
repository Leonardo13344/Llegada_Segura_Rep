package com.example.llegadasegura.utils
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.rotationMatrix
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
    var activarDatos = false
    var activarImagen = false
    @SuppressLint("SetTextI18n")
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
            if(!activarImagen){
                binding.btnFoto.text = "Agregar/Cambiar Foto"
                seleccionarImagen()
                activarImagen=true
                binding.btnFoto.text = "Confirmar"
            }else{
                subirImagen(correo)
                try {
                    Thread.sleep(1500)
                }catch (e: InterruptedIOException){
                    e.printStackTrace()
                }
                cargarImagen(correo)
                activarImagen = false
                binding.btnFoto.text = "Agregar/Cambiar Foto"
            }
        }

        binding.btnEditarDatos.setOnClickListener{
            if(!activarDatos){
                activarCasillas(true)
                binding.btnEditarDatos.text="CONFIRMAR"
                activarDatos = true
                Toast.makeText(this,"Ingrese los nuevos datos", Toast.LENGTH_SHORT)
            }else{
                if(binding.txtNombre.text.isNotBlank()&&binding.txtNombre.text.isNotBlank()&&binding.txtNombre.text.isNotBlank()){
                    cambiarDatos(
                        binding.txtNombre.text.toString(), binding.txtApellido.text.toString(),
                        binding.txtNumero.text.toString(), correo
                    )
                    binding.txtNombre.setText("")
                    binding.txtNumero.setText("")
                    binding.txtApellido.setText("")
                    Toast.makeText(this, "Los datos se han actualizado", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Los campos ingresados no son correctos", Toast.LENGTH_SHORT).show()
                }
                activarCasillas(false)
                db.collection("users").document(correo).get().addOnSuccessListener { document ->
                    if (document != null) {
                        binding.txtNombre.hint = document.data?.get("Nombre").toString()
                        binding.txtApellido.hint = document.data?.get("apellido").toString()
                        binding.txtNumero.hint = document.data?.get("telefono").toString()
                    } else {
                        Log.d("documento", "No se pudo cargar los datos")
                    }
                }
                activarDatos= false
                binding.btnEditarDatos.text = "EDITAR DATOS"
            }
        }
    }

    private fun subirImagen(correo:String) {
        val storageReference = FirebaseStorage.getInstance().getReference("images/$correo")
        storageReference.putFile(ImagenUri).addOnSuccessListener {
            binding.imgFoto.setImageURI(null)
            Toast.makeText(this,"Se subio con exito", Toast.LENGTH_LONG).show()

        }.addOnFailureListener{
            Toast.makeText(this,"No se subio", Toast.LENGTH_LONG).show()
        }
    }

    private fun cargarImagen(correo:String){

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
            rotationMatrix(90f,0f,0f)
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imgFoto.setImageBitmap(bitmap)

        }.addOnFailureListener{
            Toast.makeText(this, "No se pudo cargar la imagen", Toast.LENGTH_LONG).show()
        }
    }
    private fun seleccionarImagen(){
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
    private fun cambiarDatos(nombre: String, apellido:String, telefono:String, correo:String){
        Log.d("EntraD", "Entra con los valores $nombre $apellido $telefono $correo")
        db.collection("users").document(correo).set(
            hashMapOf("Nombre" to nombre,
            "apellido" to apellido,
            "telefono" to telefono)
        )

    }
    private fun activarCasillas(verdad: Boolean){
        binding.txtApellido.isFocusable = verdad
        binding.txtApellido.isClickable = verdad
        binding.txtApellido.isFocusableInTouchMode = verdad
        binding.txtApellido.hint =""
        binding.txtNombre.isClickable = verdad
        binding.txtNombre.isFocusable = verdad
        binding.txtNombre.isFocusableInTouchMode = verdad
        binding.txtNombre.hint =""
        binding.txtNumero.isClickable = verdad
        binding.txtNumero.isFocusable = verdad
        binding.txtNumero.isFocusableInTouchMode = verdad
        binding.txtNumero.hint =""
        this.activarDatos = true
    }

}