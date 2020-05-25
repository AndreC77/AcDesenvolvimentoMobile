package br.com.andrecoelho.lunneapp

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    val TAG_ = "FIREBASE_LMS"

    override fun onNewToken(token : String?) {
        super.onNewToken(token)
        Log.d(TAG_,"Novo token: $token")
        Prefs.setString("FB_TOKEN", token!!)
    }

    override fun onMessageReceived(menssagenRemota: RemoteMessage?) {
        Log.d(TAG_,"onMessageReceived")
        //super.onMessageReceived(menssagenRemota)
        if(menssagenRemota?.notification != null){
            val titulo = menssagenRemota?.notification?.title
            var corpo = menssagenRemota?.notification?.body
            Log.d(TAG_,"Titulo: $titulo ")
            Log.d(TAG_,"Corpo: $corpo ")

            if(menssagenRemota?.data.isNotEmpty()){
                val clienteId = menssagenRemota.data.get("clienteIds")
                corpo = "$corpo ($clienteId)"
            }

            val intent = Intent(this, ClientesActivity::class.java)
            NotificationUtil.create(this,1,intent,titulo!!,corpo!!)
        }


    }
}