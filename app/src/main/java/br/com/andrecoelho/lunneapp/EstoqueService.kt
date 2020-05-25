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
        var estoques = ArrayList<Estoque>()
        if(AndroidUtils.isInternetDisponivel(context)) {
            val url = "${host}/estoque"
            val json = HttpHelper.get(url)
            estoques = parserJson(json)
            //salvar offline
            for (e in estoques){
                saveOffline(e)
            }
            return estoques
        }else{
            val dao = DatabaseManager.getEstoqueDAO()
            val estoque = dao.findAll()
            return estoque
        }
    }

    fun delete(estoque: Estoque): Response {
        Log.d(TAG, estoque.idEstoque.toString())
        if(AndroidUtils.isInternetDisponivel(MaisVendasApplication.getInstance().applicationContext)){
            val url = "${host}/estoque/${estoque.idEstoque}"
            val json = HttpHelper.delete(url)
            return parserJson(json)
        }else{
            val dao = DatabaseManager.getEstoqueDAO()
            dao.delete(estoque)
            return Response(status = "OK", msg = "Dados Salvos Localmente")
        }
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

    //Salvar Offline
    fun saveOffline(estoque: Estoque) : Boolean {
        val dao = DatabaseManager.getEstoqueDAO()
        if (! existeEstoque(estoque)){
            dao.insert(estoque)
        }
        return true
    }

    //Verificar se Ja existe O Cliente
    fun existeEstoque(estoque: Estoque) : Boolean {
        val dao = DatabaseManager.getEstoqueDAO()
        return dao.getById(estoque.idEstoque) != null
    }

    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}