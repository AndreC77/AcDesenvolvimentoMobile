package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object EnderecoService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getEndereco(context: Context, cep : String): List<Endereco> {

        if(AndroidUtils.isInternetDisponivel(context)) {
            val url = "http://viacep.com.br/ws/$cep/json/"
            val json = HttpHelper.get(url)

            Log.d(TAG, json)

            return parserJson<List<Endereco>>(json)
        }else{
            return ArrayList()
        }
    }

    fun delete(endereco: Endereco): Response {
        Log.d(TAG, endereco.idEndereco.toString())
        val url = "${host}/enderecos/${endereco.idEndereco}"
        val json = HttpHelper.delete(url)
        Log.d(TAG, json)
        return parserJson(json)
    }

    fun save(endereco: Endereco): Response {
        val json = HttpHelper.post("$host/enderecos", endereco.toJson())
        return parserJson(json)
    }

    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}