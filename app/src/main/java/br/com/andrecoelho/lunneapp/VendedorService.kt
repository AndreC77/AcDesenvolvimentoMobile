package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object VendedorService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getVendedor(context: Context): List<Vendedor> {

        if(AndroidUtils.isInternetDisponivel(context)) {
            val url = "${host}/vendedores"
            val json = HttpHelper.get(url)

            Log.d(ClientesService.TAG, json)

            return parserJson<List<Vendedor>>(json)
        }else{
            return ArrayList()
        }
    }

    fun delete(vendedor: Vendedor): Response {
        Log.d(TAG, vendedor.id.toString())
        val url = "${host}/vendedores/${vendedor.id}"
        val json = HttpHelper.delete(url)
        Log.d(TAG, json)
        return parserJson(json)
    }

    fun save(vendedor: Vendedor): Response {
        val json = HttpHelper.post("${host}/vendedores", vendedor.toJson())
        return parserJson(json)
    }

    fun senha(vendedor: Vendedor): Vendedor? {
        val json = HttpHelper.post("${host}/vendedores/login", vendedor.toJson())
        if (json == "vendedor não cadatrado" || json == "senha invalida"){
            return null
        }
        return parserJson(json)
    }

    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}