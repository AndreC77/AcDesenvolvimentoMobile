package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception

object CoresService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getCores(context: Context): List<Cores>? {
        var cores = ArrayList<Cores>()
        val url = "$host/cor"
        val json = HttpHelper.get(url)
        cores = parserJson(json)

        return cores
    }


    fun delete(cores: Cores): Response {
        try {
            val url = "${host}/cor/${cores.idCor}"
            val json = HttpHelper.delete(url)
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao deletar cor")
        }
    }

    fun save(cor: Cores): Response {
        try {
            val json = HttpHelper.post("$host/cor", cor.toJson())
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao salvar cor")
        }
    }

    fun edit(cor: Cores): Response? {
        try {
            val json = HttpHelper.put("$host/cor/${cor.idCor}", cor.toJson())
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao editar cor")
        }
    }

    //Salvar Offline
    fun saveOffiline(cor: Cores) : Boolean {
        val dao = DatabaseManager.getCoresDAO()
        if (! existeCor(cor)){
            dao.insert(cor)
        }
        return true
    }

    //Verificar se Ja existe O Cor
    fun existeCor(cor: Cores) : Boolean {
        val dao = DatabaseManager.getCoresDAO()
        return dao.getById(cor.idCor) != null
    }


    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}