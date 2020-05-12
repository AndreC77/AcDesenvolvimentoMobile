package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ProdutosService {
    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getProdutos(context: Context): List<Produtos> {

        if(AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/produtos"
            val json = HttpHelper.get(url)

            Log.d(TAG, json)

            return parserJson<List<Produtos>>(json)
        }else{
            return ArrayList()
        }
    }

    fun delete(produto: Produtos): Response {
        Log.d(TAG, produto.idProduto.toString())
        val url = "${host}/produtos/${produto.idProduto}"
        val json = HttpHelper.delete(url)
        Log.d(TAG, json)
        return parserJson(json)
    }

    fun save(produto: Produtos): Response {
        val json = HttpHelper.post("${host}/produtos", produto.toJson())
        return parserJson(json)
    }

    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }
}