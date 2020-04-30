package br.com.andrecoelho.lunneapp

import android.content.Context

object ClientesService {

    fun getCliente(context: Context) :List<Clientes>{

        val clientes = mutableListOf<Clientes>()

        for(i in 1..10){
            val d = Clientes()
            d.NomeCompleto = "Nome Completo $i"
            d.RazaoSocial = "Razão Social $i"
            d.RegistroFederal = "Registro Federal $i"
            d.telefone = "Telefone $i"
            d.foto = "https://www.pngfind.com/pngs/m/515-5153597_cliente-icon-png-customer-icon-vector-png-transparent.png"
            clientes.add(d)
        }
        return clientes
    }
}