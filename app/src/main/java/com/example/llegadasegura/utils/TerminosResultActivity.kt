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
        binding.textViewTerminos.text = "1.  Los Términos y Condiciones de Uso regulan las reglas a que se sujeta la utilización de la APP Llegada Segura (en adelante, la aplicación), que puede descargarse desde el dominio Play Store. La descarga o utilización de la APP atribuye la condición de Usuario a quien lo haga e implica la aceptación de todas las condiciones incluidas en este documento y en la Política de Privacidad y el Aviso Legal de dicha página Web. El Usuario debería leer estas condiciones cada vez que utilice la APP, ya que podrían ser modificadas en lo sucesivo.\n \n" +
                "2.  La aplicación requiere de acceso a la ubicación obtenida a través de los recursos de su dispositivo móvil, por lo que tras aceptar las condiciones en este documento usted acepta tener conocimiento de esta consideración. \n \n" +
                "3.  La información recolectada sobre su dispositivo y sus datos personales únicamente serán utilizados para brindarle una mejor experiencia mientras la aplicación está en uso, quienes formamos parte del desarrollo del aplicativo nos comprometemos a mantener su información en privado y a no exponerla a otras organizaciones, tal como se establece en la ley orgánica del protección de datos del Ecuador. \n \n" +
                "4.  La aplicación móvil en su versión inicial es gratuita y dispone de los servicios de ubicación y de generación de grupos de seguimiento, en una versión posterior se propone implementar otras funcionalidades como: un botón de emergencia y un sistema de alerta en caso de siniestros. \n \n" +
                "5.  El titular de la aplicación no se responsabiliza por el daño total o parcial de su dispositivo del hardware de su dispositivo móvil. \n \n" +
                "6.  El aplicativo móvil no se responsabiliza de la mala utilización de este, se propone únicamente como una herramienta para la geolocalización de las personas de su círculo social/familiar cercano. \n \n" +
                "7.  Todos los derechos de propiedad intelectual e industrial corresponden al grupo de trabajo de Dispositivos móviles de la carrera de Ciencias de la Computación de la Universidad Central del Ecuador y el usuario del aplicativo se compromete a respetarlos.\n \n"

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