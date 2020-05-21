package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FormaDePgtoService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getForma(context: Context): List<FormaDePagamento> {

        if(AndroidUtils.isInternetDisponivel(context)) {
            val url = "${host}/formadepagamento"
            val json = HttpHelper.get(url)

            Log.d(TAG, json)

            return parserJson<List<FormaDePagamento>>(json)

        }else{
            return ArrayList()
        }
    }

    fun delete(forma: FormaDePagamento): Response {
        Log.d(TAG, forma.idFormaDePgto.toString())
        val url = "${host}/formadepagamento/${forma.idFormaDePgto}"
        val json = HttpHelper.delete(url)
        Log.d(TAG, json)
        return parserJson(json)
    }

    fun save(forma: FormaDePagamento): Response {
        val json = HttpHelper.post("${host}/formadepagamento", forma.toJson())
        return parserJson(json)
    }

    fun edit(forma: FormaDePagamento): Response {
        Log.d(TAG, forma.toJson())
        val json = HttpHelper.put("$host/formadepagamento/${forma.idFormaDePgto}", forma.toJson())
        return parserJson(json)
    }

    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}