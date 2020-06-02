package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception

object EstoqueService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getEstoque(context: Context): List<Estoque> {
        var estoques = ArrayList<Estoque>()
        val url = "${host}/estoque"
        val json = HttpHelper.get(url)
        estoques = parserJson(json)
        return estoques
    }

    fun delete(estoque: Estoque): Response {
        try {
            val url = "${host}/estoque/${estoque.idEstoque}"
            val json = HttpHelper.delete(url)
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao deletar Estoque")
        }
    }

    fun save(estoque: Estoque): Response {
        try {
            val json = HttpHelper.post("${host}/estoque", estoque.toJson())
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao salvar Estoque")
        }
    }

    fun edit(estoque: Estoque): Response? {
        try {
            val json = HttpHelper.put("$host/estoque/${estoque.idEstoque}", estoque.toJson())
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao editar Estoque")
        }
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