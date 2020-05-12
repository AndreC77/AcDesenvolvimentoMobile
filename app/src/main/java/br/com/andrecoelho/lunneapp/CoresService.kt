package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CoresService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getCores(context: Context): List<Cores> {

        if(AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/cor"
            val json = HttpHelper.get(url)

            Log.d(TAG, json)

            return parserJson<List<Cores>>(json)
        }else{
            return ArrayList()
        }
    }

    fun delete(cores: Cores): Response {
        Log.d(TAG, cores.idCor.toString())
        val url = "${host}/cor/${cores.idCor}"
        val json = HttpHelper.delete(url)
        Log.d(TAG, json)
        return parserJson(json)
    }

    fun save(cor: Cores): Response {
        val json = HttpHelper.post("$host/cor", cor.toJson())
        return parserJson(json)
    }


    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}