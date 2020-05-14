package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL

object ClientesService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getCliente(context: Context): List<Clientes> {

        if(AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/clientes"
            val json = HttpHelper.get(url)

            Log.d(TAG, json)

            return parserJson<List<Clientes>>(json)
        }else{
            return ArrayList()
        }
    }

    fun delete(cliente: Clientes): Response {
        Log.d(TAG, cliente.idCliente.toString())
        val url = "$host/clientes/${cliente.idCliente}"
        val json = HttpHelper.delete(url)
        Log.d(TAG, json)
        return parserJson(json)
    }

    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}