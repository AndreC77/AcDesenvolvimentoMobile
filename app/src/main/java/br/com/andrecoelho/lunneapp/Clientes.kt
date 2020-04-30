package br.com.andrecoelho.lunneapp

class Clientes {

    var id:Long = 0
    var RegistroFederal: String = ""
    var NomeCompleto: String = ""
    var RazaoSocial: String = ""
    var ddd: String = ""
    var telefone: String = ""
    var foto: String = ""

    override fun toString(): String {
        return "Cliente: $NomeCompleto"
    }

}