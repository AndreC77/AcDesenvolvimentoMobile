package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception

object ProdutosService {
    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getProdutos(context: Context): List<Produtos> {
        var produtos = ArrayList<Produtos>()
        val url = "$host/produtos"
        val json = HttpHelper.get(url)
        produtos = parserJson(json)
        return produtos
    }

    fun delete(produto: Produtos): Response {

        try {
            val url = "${host}/produtos/${produto.idProduto}"
            val json = HttpHelper.delete(url)

            return parserJson(json)
        }catch (e : Exception) {
            return Response(status = "FAIL", msg = "Falha ao deletar Produto")
        }
    }

    fun save(produto: Produtos): Response {
        try {
            val json = HttpHelper.post("${host}/produtos", produto.toJson())
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao salvar Produto")
        }
    }

    fun edit(produto: Produtos): Response? {
        try {
            val json = HttpHelper.put("$host/produtos/${produto.idProduto}", produto.toJson())
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao editar Produto")
        }
    }

    //Salvar Offline
    fun saveOffline(produto: Produtos) : Boolean {
        val dao = DatabaseManager.getProdutosDAO()
        if (! existeProduto(produto)){
            dao.insert(produto)
        }
        return true
    }

    //Verificar se Ja existe O Cliente
    fun existeProduto(produto: Produtos) : Boolean {
        val dao = DatabaseManager.getProdutosDAO()
        return dao.getById(produto.idProduto) != null
    }

    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }
}