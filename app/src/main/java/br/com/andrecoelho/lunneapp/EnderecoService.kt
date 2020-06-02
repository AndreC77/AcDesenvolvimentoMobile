package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception

object EnderecoService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getEndereco(context: Context, cep : String): List<Endereco> {
        var enderecos = ArrayList<Endereco>()
        if(AndroidUtils.isInternetDisponivel(context)) {
            val url = "http://viacep.com.br/ws/$cep/json/"
            val json = HttpHelper.get(url)
            enderecos = parserJson(json)
            //salvar offline
            for (e in enderecos){
                saveOffiline(e)
            }
            return enderecos
        }else{
            val dao = DatabaseManager.getEnderecoDAO()
            val enderecos = dao.findAll()
            return enderecos
        }
    }

    fun delete(endereco: Endereco): Response {
        if(AndroidUtils.isInternetDisponivel(MaisVendasApplication.getInstance().applicationContext)) {
            val url = "${host}/enderecos/${endereco.idEndereco}"
            val json = HttpHelper.delete(url)
            return parserJson(json)
        }else{
            val dao = DatabaseManager.getEnderecoDAO()
            dao.delete(endereco)
            return Response(status = "OK", msg = "Dados Salvos Localmente")
        }
    }

    fun save(endereco: Endereco): Response {
        try {
            val json = HttpHelper.post("$host/enderecos", endereco.toJson())
            return parserJson(json)
        }catch (e : Exception){
            return Response(status ="FAIL", msg = "Falha ao cadastrar endereço")
        }
    }

    //Salvar Offline
    fun saveOffiline(endereco: Endereco) : Boolean {
        val dao = DatabaseManager.getEnderecoDAO()
        if (! existeEndereco(endereco)){
            dao.insert(endereco)
        }
        return true
    }

    //Verificar se Ja existe O Cliente
    fun existeEndereco(endereco: Endereco) : Boolean {
        val dao = DatabaseManager.getEnderecoDAO()
        return dao.getById(endereco.idEndereco) != null
    }

    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}