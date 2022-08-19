package com.example.llegadasegura.utils

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.example.llegadasegura.databinding.ActivityTerminosResultBinding


class TerminosResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTerminosResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTerminosResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textViewTerminos.text = "1. Estos Términos y Condiciones de Uso regulan las reglas a que se sujeta la utilización de la APP"+
                "Llegada Segura (en adelante, la APP), que puede descargarse desde el dominio Play Store \n" +
                "La descarga o utilización de la APP atribuye la condición de" +
                "Usuario a quien lo haga e implica la aceptación de todas las condiciones incluidas en este documento" +
                "y en la Política de Privacidad y el Aviso Legal de dicha página Web. El Usuario debería leer estas" +
                "condiciones cada vez que utilice la APP, ya que podrían ser modificadas en lo sucesivo."+
                "Llegada Segura (en adelante, la APP), que puede descargarse desde el dominio Play Store \n" +
                "La descarga o utilización de la APP atribuye la condición de" +
                "Usuario a quien lo haga e implica la aceptación de todas las condiciones incluidas en este documento" +
                "y en la Política de Privacidad y el Aviso Legal de dicha página Web. El Usuario debería leer estas" +
                "condiciones cada vez que utilice la APP, ya que podrían ser modificadas en lo sucesivo." +
        "Llegada Segura (en adelante, la APP), que puede descargarse desde el dominio Play Store \n" +
                "La descarga o utilización de la APP atribuye la condición de" +
                "Usuario a quien lo haga e implica la aceptación de todas las condiciones incluidas en este documento" +
                "y en la Política de Privacidad y el Aviso Legal de dicha página Web. El Usuario debería leer estas" +
                "condiciones cada vez que utilice la APP, ya que podrían ser modificadas en lo sucesivo."+
                "Llegada Segura (en adelante, la APP), que puede descargarse desde el dominio Play Store \n" +
                "La descarga o utilización de la APP atribuye la condición de" +
                "Usuario a quien lo haga e implica la aceptación de todas las condiciones incluidas en este documento" +
                "y en la Política de Privacidad y el Aviso Legal de dicha página Web. El Usuario debería leer estas" +
                "condiciones cada vez que utilice la APP, ya que podrían ser modificadas en lo sucesivo."+
                "Llegada Segura (en adelante, la APP), que puede descargarse desde el dominio Play Store \n" +
                "La descarga o utilización de la APP atribuye la condición de" +
                "Usuario a quien lo haga e implica la aceptación de todas las condiciones incluidas en este documento" +
                "y en la Política de Privacidad y el Aviso Legal de dicha página Web. El Usuario debería leer estas" +
                "condiciones cada vez que utilice la APP, ya que podrían ser modificadas en lo sucesivo."+
                "Llegada Segura (en adelante, la APP), que puede descargarse desde el dominio Play Store \n" +
                "La descarga o utilización de la APP atribuye la condición de" +
                "Usuario a quien lo haga e implica la aceptación de todas las condiciones incluidas en este documento" +
                "y en la Política de Privacidad y el Aviso Legal de dicha página Web. El Usuario debería leer estas" +
                "condiciones cada vez que utilice la APP, ya que podrían ser modificadas en lo sucesivo."

        binding.btnAceptar.setOnClickListener{
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.btnCancelar.setOnClickListener{
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}