package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception

object FormaDePgtoService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getForma(context: Context): List<FormaDePagamento> {
        var formas = ArrayList<FormaDePagamento>()
        val url = "${host}/formadepagamento"
        val json = HttpHelper.get(url)
        formas = parserJson(json)
        return formas
    }

    fun delete(forma: FormaDePagamento): Response {

        try {
            val url = "${host}/formadepagamento/${forma.idFormaDePgto}"
            val json = HttpHelper.delete(url)

            return parserJson(json)
        }catch (e : Exception) {
            return Response(status = "FAIL", msg = "Falha ao deletar Forma de Pagamento")
        }
    }

    fun save(forma: FormaDePagamento): Response {
        try {
            val json = HttpHelper.post("${host}/formadepagamento", forma.toJson())
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao salvar Forma de Pamento")
        }
    }

    fun edit(forma: FormaDePagamento): Response? {
        try {
            val json = HttpHelper.put("$host/formadepagamento/${forma.idFormaDePgto}", forma.toJson())
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao editar Forma de Pagamneto")
        }
    }

    //Salvar Offline
    fun saveOffline(forma: FormaDePagamento) : Boolean {
        val dao = DatabaseManager.getFormaDePgtoDAO()
        if (! existeForma(forma)){
            dao.insert(forma)
        }
        return true
    }

    //Verificar se Ja existe O Cliente
    fun existeForma(forma: FormaDePagamento) : Boolean {
        val dao = DatabaseManager.getFormaDePgtoDAO()
        return dao.getById(forma.idFormaDePgto) != null
    }

    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}