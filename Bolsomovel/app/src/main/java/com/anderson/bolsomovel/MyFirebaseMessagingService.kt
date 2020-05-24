package com.anderson.bolsomovel

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    val TAG = "FIREBASE_Bolsomovel"

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d(TAG, "Novo Token: $token")

        Prefs.setString("FB_TOKEN", token!!)
    }

    //executa apenas com app em aberto
    override fun onMessageReceived(mensagemRemota: RemoteMessage?) {
        Log.d(TAG, "onMessageReceived")

        if(mensagemRemota?.notification != null) {
            val titulo = mensagemRemota?.notification?.title
            var corpo = mensagemRemota?.notification?.body
            Log.d(TAG, "Corpo: $titulo")
            Log.d(TAG, "Corpo: $corpo")

            if (mensagemRemota?.data.isNotEmpty()){
                val produtoId = mensagemRemota.data.get("produtoId")
                corpo = "$corpo ($produtoId)"
            }

            val intent = Intent(this, ProdutoCadastroActivity::class.java)
            NotificationUtils.create(this, 1, intent, titulo!!, corpo!!)
            showNotification(mensagemRemota)
        }
    }

    private fun showNotification(mensagemRemota: RemoteMessage) {

        // Intent para abrir quando clicar na notificação
        val intent = Intent(this, ProdutoCadastroActivity::class.java)
        // se título for nulo, utilizar nome no app
        val titulo = mensagemRemota?.notification?.title?: getString(R.string.app_name)
        var mensagem = mensagemRemota?.notification?.body!!

        // verificar se existem dados enviados no push
        if(mensagemRemota?.data.isNotEmpty()) {
            val produtoId = mensagemRemota.data.get("produtoId")?.toLong()!!
            mensagem += ""
            // recuperar disciplina no WS
            val produto = ProdutoService.getProduto(this, produtoId)
            intent.putExtra("produto", produto)
        }
        NotificationUtils.create(this, 1, intent, titulo, mensagem)
    }

}