package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object EstoqueService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getEstoque(context: Context): List<Estoque> {

        if(AndroidUtils.isInternetDisponivel(context)) {
            val url = "${host}/estoque"
            val json = HttpHelper.get(url)

            Log.d(TAG, json)

            return parserJson<List<Estoque>>(json)
        }else{
            return ArrayList()
        }
    }

    fun delete(estoque: Estoque): Response {
        Log.d(TAG, estoque.idEstoque.toString())
        val url = "${host}/estoque/${estoque.idEstoque}"
        val json = HttpHelper.delete(url)
        Log.d(TAG, json)
        return parserJson(json)
    }

    fun save(estoque: Estoque): Response {
        val json = HttpHelper.post("${host}/estoque", estoque.toJson())
        return parserJson(json)
    }

    fun edit(estoque: Estoque): Response {
        Log.d(TAG, estoque.toJson())
        val json = HttpHelper.put("$host/estoque/${estoque.idEstoque}", estoque.toJson())
        return parserJson(json)
    }

    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}