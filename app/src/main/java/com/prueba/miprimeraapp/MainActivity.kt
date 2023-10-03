package com.prueba.miprimeraapp

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.prueba.miprimeraapp.databinding.ActivityBienvenidaBinding
import com.prueba.miprimeraapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Accedo a los elementos del main
        var mainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainBinding.root)

        //Guardo el boton en una variable
        val boton= mainBinding.boton

        //Cuando pulse el boton
        boton.setOnClickListener {
            //Guardamos el nombre de usuario en un a variable
            var userName = mainBinding.nombreUsuario.text

            if (userName.isNotEmpty()) {
                //Accederemos a los elementos de la vista bienvenida
                var bienvenidaBinding = ActivityBienvenidaBinding.inflate(layoutInflater)

                setContentView(bienvenidaBinding.root)

                //Mostramos un mensaje
                bienvenidaBinding.saludo.text = "Nos alegramos de que vuelvas, $userName"

            }else {
                val toast= Toast.makeText(
                    applicationContext,
                    "Debe introducir un nombre",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }



    }



    //Cuando se pause la aplicacion
    override fun onPause() {
        super.onPause()

        //Accedemos a los elementos de la vista "Bienvenida"
        var bienvenidaBinding = ActivityBienvenidaBinding.inflate(layoutInflater)

        setContentView(bienvenidaBinding.root)

        //Cuando esté en pausa. La foto de pause será visible
        bienvenidaBinding.pause.visibility = View.VISIBLE
        //Y el texto del saludo no
        bienvenidaBinding.saludo.visibility = View.INVISIBLE

    }

    // Cuando se reanude la aplicacion
    override fun onResume() {
        super.onResume()

        //Accedemos a los elementos de los layouts
        var bienvenidaBinding = ActivityBienvenidaBinding.inflate(layoutInflater)

        var mainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(bienvenidaBinding.root)

        //La imagen se vovlerá invisible
        bienvenidaBinding.pause.visibility = View.INVISIBLE

        //Cogemos el valor del nombre en el main
        var name = mainBinding.nombreUsuario.text

        //Mostramos un mensaje que le de la bienvenida de nuevo
        val toast= Toast.makeText(
            applicationContext,
            "Bienvenido de nuevo $name",
            Toast.LENGTH_SHORT
        ).show()

    }

   override fun onDestroy() {
        super.onDestroy()

        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        var name = mainBinding.nombreUsuario.text

        val toast= Toast.makeText(
            applicationContext,
            "Se ha cerrado la sesion de $name",
            Toast.LENGTH_SHORT
        ).show()


        createNotificationChannel1()

        val notification = NotificationCompat.Builder(this,channelID).also {
            it.setContentTitle("The circle of life")
            it.setContentText("Oh no, te has ido")
            it.setSmallIcon(R.drawable.ic_message)
            it.setPriority(NotificationCompat.PRIORITY_HIGH)
        }.build()

        val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(this)


    }

    private val channelID ="channelID"
    private  val channelName = "channelName"

    private fun createNotificationChannel1(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val importance: Int= NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel (channelID,channelName,importance).apply {
                lightColor = Color.RED
                 enableLights(true)
            }
            val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)
        }
    }
}