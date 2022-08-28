package com.example.llegadasegura.utils

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.llegadasegura.databinding.CuentaConfiguracionBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class Cuenta: AppCompatActivity() {
    private lateinit var binding: CuentaConfiguracionBinding
    private lateinit var ImagenUri: Uri
    private lateinit var correo:String
    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        super.onCreate(savedInstanceState)
        binding = CuentaConfiguracionBinding.inflate(layoutInflater)
        val telefono = bundle?.getString("telefono")
        val nombre = bundle?.getString("nombre")
        val apellido = bundle?.getString("apellido")
        correo = bundle?.getString("correo").toString()
        binding.txtNombre.text = nombre
        binding.txtApellido.text = apellido
        binding.txtNumero.text = telefono
        cargarImagen(correo)
        /*if (!ImagenUri.equals(null)){
                binding.btnFoto.isVisible = true;
                binding.btnSubir.isVisible = false;
        }*/
        setContentView(binding.root)
        binding.btnFoto.setOnClickListener{
            seleccionarImagen()
        }
        binding.btnSubir.setOnClickListener {
            subirImagen(correo)

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
        val storageRef = FirebaseStorage.getInstance().reference.child("images/$correo")
        val localfile = File.createTempFile("tempImage",".jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imgFoto.setImageBitmap(bitmap)

        }.addOnFailureListener{
            Toast.makeText(this, "No se pudo cargar la imagen", Toast.LENGTH_LONG)
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
            ImagenUri =data?.data!!
            binding.imgFoto.setImageURI(ImagenUri)
        }
    }

}