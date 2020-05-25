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
        var produtos = ArrayList<Produtos>()
        if(AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/produtos"
            val json = HttpHelper.get(url)
            produtos = parserJson(json)
            //salvar offline
            for (p in produtos){
                saveOffline(p)
            }
            return produtos
        }else{
            val dao = DatabaseManager.getProdutosDAO()
            val produtos = dao.findAll()
            return produtos
        }
    }

    fun delete(produto: Produtos): Response {
        Log.d(TAG, produto.idProduto.toString())
        if(AndroidUtils.isInternetDisponivel(MaisVendasApplication.getInstance().applicationContext)) {
            val url = "${host}/produtos/${produto.idProduto}"
            val json = HttpHelper.delete(url)

            return parserJson(json)
        }else{
            val dao = DatabaseManager.getProdutosDAO()
            dao.delete(produto)
            return Response(status = "OK", msg = "Dados Salvos Localmente")
        }
    }

    fun save(produto: Produtos): Response {
        val json = HttpHelper.post("${host}/produtos", produto.toJson())
        return parserJson(json)
    }

    fun edit(produto: Produtos): Response {
        Log.d(TAG, produto.toJson())
        val json = HttpHelper.put("$host/produtos/${produto.idProduto}", produto.toJson())
        return parserJson(json)
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