package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.net.URL

object ClientesService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"


    fun getCliente(context: Context): List<Clientes> {
        val url = "$host/clientes"
        val json = HttpHelper.get(url)
        return parserJson(json)
    }

    fun save(cliente: Clientes): Response? {
        try {
            val json = HttpHelper.post("$host/clientes", cliente.toJson())
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao cadastrar cliente")
        }
    }

    fun delete(cliente: Clientes): Response? {

        try {
            val url = "$host/clientes/${cliente.idCliente}"
            val json = HttpHelper.delete(url)

            return parserJson(json)
        }catch (e : Exception){
            return  Response(status ="FAIL", msg ="Falha ao deletar cliente")
        }
    }

    fun edit(cliente: Clientes): Response? {
        try {
            val json = HttpHelper.put("$host/clientes/${cliente.idCliente}", cliente.toJson())
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao editar cliente")
        }
    }

//    //Salvar Offline
//    fun saveOffline(cliente : Clientes) : Boolean {
//        val dao = DatabaseManager.getClientesDAO()
//        if (! existeCliente(cliente)){
//            dao.insert(cliente)
//        }
//        return true
//    }

//    //Verificar se Ja existe O Cliente
//    fun existeCliente(cliente: Clientes) : Boolean {
//        val dao = DatabaseManager.getClientesDAO()
//        return dao.getById(cliente.idCliente) != null
//    }

    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}