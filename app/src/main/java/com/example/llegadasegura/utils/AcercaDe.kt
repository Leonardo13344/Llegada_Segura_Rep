package com.example.llegadasegura.utils

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.llegadasegura.databinding.AcercaDeBinding


class AcercaDe: AppCompatActivity() {

    private lateinit var binding: AcercaDeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AcercaDeBinding.inflate(layoutInflater)

        binding.imgFacebook.setOnClickListener{
            startActivity(getOpenFacebookIntent())
        }
        binding.imgTwitter.setOnClickListener{
            startActivity(getOpenTwitterIntent())
        }

        setContentView(binding.root)
    }
    fun getOpenFacebookIntent(): Intent? {
        Log.d("Facebook", "Esta entrando a la funcion Facebook")
        return try {
            Log.d("Facebook", "Esta entrando a la SI Facebook")
            packageManager.getPackageInfo("com.facebook.katana", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/105917168919137"))
        } catch (e: Exception) {
            return Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Llegada-Segura-105917168919137"))
        }
    }

    fun getOpenTwitterIntent():Intent? {

        return try{
            Log.d("Twitter", "Esta entrando a la SI Facebook")
            packageManager.getPackageInfo("com.twitter.android", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=1564028191681007617"))

        } catch (e: Exception) {
            Log.d("Twitter", "Esta entrando a la NO Facebook")
            return Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/LlegadaSegura"))
        }
    }
}