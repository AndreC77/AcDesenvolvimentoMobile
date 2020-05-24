package com.anderson.bolsomovel

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.GsonBuilder
import java.net.URL

object ProdutoService {

    //trocar pela URL da sua api
    val host = "https://fesousa.pythonanywhere.com"
    val TAG = "WS_LMSApp"


    fun getProduto(context: Context, id: Long): Produto? {
        val url = "$host/disciplinas/${id}"
        val json = HttpHelper.get(url)
        val produto = parserJson<Produto>(json)

        return produto
    }

    fun getProdutos(context: Context): List<Produto> {

        val produtos = mutableListOf<Produto>()

        if (AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/disciplinas"
            val json = HttpHelper.get(url)

            Log.d(TAG, json)

            return parserJson<List<Produto>>(json)
        } else {
            return ArrayList()
        }
    }

    fun save(produto: Produto): Response {
        //alterar host/disciplina para host/produtos
        val json = HttpHelper.post("$host/disciplinas", produto.toJson())
        return parserJson<Response>(json)

    }

    fun delete(produto: Produto): Response {
        //alterar host/disciplina para host/produtos
        val url = "$host/disciplinas/${produto.id}"
        val json = HttpHelper.delete(url)
        return parserJson<Response>(json)
    }

    inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>() {}.type
        return Gson().fromJson<T>(json, type)
    }
}