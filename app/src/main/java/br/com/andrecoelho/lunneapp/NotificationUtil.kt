package br.com.andrecoelho.lunneapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationUtil {

    internal  val CHANNEL_ID = "1"

    fun createChannel(context: Context) {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val appName = context.getString(R.string.app_name)
            val c = NotificationChannel(CHANNEL_ID, appName, NotificationManager.IMPORTANCE_HIGH)

            manager.createNotificationChannel(c)
        }
    }

    fun create(context: Context, id : Int, intent : Intent, titulo : String, texto: String){

        //Criar canal para mostrar notificação
        createChannel(MaisVendasApplication.getInstance())
        //Intent para disparar broadcast
        val p = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        //Criar notificação
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(p)
            .setContentTitle(titulo)
            .setContentText(texto)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        //Disparar notificação
        with(NotificationManagerCompat.from(MaisVendasApplication.getInstance())){
            val n = builder.build()
            notify(id, n)
        }

    }

}