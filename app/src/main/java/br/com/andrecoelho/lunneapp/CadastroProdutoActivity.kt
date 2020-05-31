package br.com.andrecoelho.lunneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_cadastro_produto.*
import java.lang.Float.parseFloat
import java.lang.Integer.parseInt
import java.lang.Long.parseLong

class CadastroProdutoActivity : AppCompatActivity() {

    var produto : Produtos? = null

    var par1 = arrayOf("UN", "PT","CX","MT","KG")
    var selecao1 = 0

    var origem = arrayOf(0,1,2)
    var selecao2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_produto)



        //spinners
        val spinner1 = this.findViewById<Spinner>(R.id.spinnerUndDesc)
        val spinner2 = this.findViewById<Spinner>(R.id.spinnerOrigem)

        //Adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, this.par1)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, this.origem)


        spinner1.adapter = adapter
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selecao1 = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        spinner2.adapter = adapter2
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selecao2 = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //recuperar objeto
        produto = intent.getSerializableExtra("produto") as? Produtos
        if (produto != null) {

            insertCodProduto1.setText(produto?.codProduto.toString())
            insertCodEan.setText(produto?.codEan.toString())
            insertReferencia.setText(produto?.ref.toString())
            insertNcm.setText(produto?.ncm.toString())
            insertCorProduto.setText(produto?.cor.toString())
            insertDescReduzida.setText(produto?.descricaoReduzida.toString())
            insertDesc.setText(produto?.descricao.toString())
            insertDescUnid.setText(produto?.descricaoUnidadeVend.toString())
            insertUnidade.setText(produto?.unidadeVenda.toString())
            insertCusto.setText(produto?.custo.toString())
            insertPreco.setText(produto?.preco.toString())
            insertAltura.setText(produto?.altura.toString())
            insertLargura.setText(produto?.largura.toString())
            insertComprimento.setText(produto?.comprimento.toString())
            insertPeso.setText(produto?.peso.toString())

            buttonSalvarProduto.setOnClickListener {

                var validarDados = validarDados()
                if(validarDados) {
                    produto?.codProduto = parseLong(insertCodProduto1.text.toString())
                    produto?.codEan = parseLong(insertCodEan.text.toString())
                    produto?.ref = insertReferencia.text.toString()
                    produto?.ncm = insertNcm.text.toString()
                    produto?.cor = insertCorProduto.text.toString()
                    produto?.descricaoReduzida = insertDescReduzida.text.toString()
                    produto?.descricao = insertDesc.text.toString()
                    produto?.gradeVenda = spinnerUndDesc.selectedItem.toString()
                    produto?.descricaoUnidadeVend = insertDescUnid.text.toString()
                    produto?.unidadeVenda = insertUnidade.text.toString()
                    produto?.origem = parseInt(spinnerOrigem.selectedItem.toString())
                    produto?.custo = parseFloat(insertCusto.text.toString())
                    produto?.preco = parseFloat(insertPreco.text.toString())
                    produto?.altura = parseFloat(insertAltura.text.toString())
                    produto?.largura = parseFloat(insertLargura.text.toString())
                    produto?.comprimento = parseFloat(insertComprimento.text.toString())
                    produto?.peso = parseFloat(insertPeso.text.toString())

                    taskPut(produto!!)
                }
            }
            supportActionBar?.title = "Editar Produto"
        }else{
            buttonSalvarProduto.setOnClickListener {

                var validarDados = validarDados()

                if(validarDados) {

                    var produto = Produtos()

                    produto.codProduto = parseLong(insertCodProduto1.text.toString())
                    produto.codEan = parseLong(insertCodEan.text.toString())
                    produto.ref = insertReferencia.text.toString()
                    produto.ncm = insertNcm.text.toString()
                    produto.cor = insertCorProduto.text.toString()
                    produto.descricaoReduzida = insertDescReduzida.text.toString()
                    produto.descricao = insertDesc.text.toString()
                    produto.gradeVenda = spinnerUndDesc.selectedItem.toString()
                    produto.descricaoUnidadeVend = insertDescUnid.text.toString()
                    produto.unidadeVenda = insertUnidade.text.toString()
                    produto.origem = parseInt(spinnerOrigem.selectedItem.toString())
                    produto.custo = parseFloat(insertCusto.text.toString())
                    produto.preco = parseFloat(insertPreco.text.toString())
                    produto.altura = parseFloat(insertAltura.text.toString())
                    produto.largura = parseFloat(insertLargura.text.toString())
                    produto.comprimento = parseFloat(insertComprimento.text.toString())
                    produto.peso = parseFloat(insertPeso.text.toString())

                    taskAtualizar(produto)
                }
            }
            supportActionBar?.title = "Incluir Produto"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun taskAtualizar(produtos: Produtos) {
        // Thread para salvar a Cor
        Thread {
            ProdutosService.save(produtos)
            runOnUiThread {
                // após cadastrar, voltar para activity anterior
                finish()
            }
        }.start()
    }

    private fun taskPut(produtos: Produtos) {
        //Thred para Atualizar a Produto
        Thread {
            ProdutosService.edit(produtos)
            runOnUiThread {
                // após cadastrar, voltar para Tela de Produtos
                intent = Intent(this, TelaProdutosActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    private fun validarDados() : Boolean{

        //validade dos Campos entre 1 ou 0
        var v1 = 0; var v2 = 0; var v3 = 0; var v4 = 0; var v5 = 0; var v6 = 0; var v7 = 0;
        var v8 = 0; var v9 = 0; var v10 = 0; var v11 = 0; var v12 = 0; var v13 = 0; var v14 = 0; var v15 = 0;

        //campo codProduto
        if(!TextUtils.isEmpty(insertCodProduto1.text.toString())){
            v1 = 1
        }else{
            insertCodProduto1.setError("Campo tem que ser preenchido")
            insertCodProduto1.requestFocus()
        }

        //campo EAN13
        if(!TextUtils.isEmpty(insertCodEan.text.toString())){
            v2 = 1
        }else{
            insertCodEan.setError("Campo tem que ser preenchido")
            insertCodEan.requestFocus()
        }

        //campo referencia
        if(!TextUtils.isEmpty(insertReferencia.text.toString()) && insertReferencia.text.length > 0 && insertReferencia.text.length < 11){
            v3 = 1
        }else{
            insertReferencia.setError("Ref tem que ter min 1 caracteres max 10")
            insertReferencia.requestFocus()
        }

        //campo NCM
        if(!TextUtils.isEmpty(insertNcm.text.toString())){
            v4 = 1
        }else{
            insertNcm.setError("Campo tem que ser preenchido")
            insertNcm.requestFocus()
        }

        //campo cor
        if(!TextUtils.isEmpty(insertCorProduto.text.toString())){
            v5 = 1
        }else{
            insertCorProduto.setError("Campo tem que ser preenchido")
            insertCorProduto.requestFocus()
        }

        //campo DescReduzida
        if(!TextUtils.isEmpty(insertDescReduzida.text.toString())){
            v6 = 1
        }else{
            insertDescReduzida.setError("Campo tem que ser preenchido")
            insertDescReduzida.requestFocus()
        }

        //campo desc
        if(!TextUtils.isEmpty(insertDesc.text.toString()) && insertDesc.text.length > 4 && insertDesc.text.length < 51 ){
            v7 = 1
        }else{
            insertDesc.setError("Descrição tem que ter min 5 caracteres max 50")
            insertDesc.requestFocus()
        }

        //campo desc Unidade
        if(!TextUtils.isEmpty(insertDescUnid.text.toString()) && insertDescUnid.text.length > 1 && insertDescUnid.text.length < 11 ){
            v8 = 1
        }else{
            insertDescUnid.setError("Descrição unidade tem que ter min 2 caracteres max 10")
            insertDescUnid.requestFocus()
        }

        //campo Unidade
        var n = insertUnidade.text.toString()
        if(n == ""){n = "0"}
        if(!TextUtils.isEmpty(insertUnidade.text.toString()) && parseInt(n) > 0 && parseInt(n) < 1001 ){
            v9 = 1
        }else{
            insertUnidade.setError("Unidade deve estar entre 1 e 1000")
            insertUnidade.requestFocus()
        }

        //campo Custo
        if(!TextUtils.isEmpty(insertCusto.text.toString())){
            v10 = 1
        }else{
            insertCusto.setError("Campo tem que ser preenchido")
            insertCusto.requestFocus()
        }

        //campo Preço
        if(!TextUtils.isEmpty(insertPreco.text.toString())){
            v11 = 1
        }else{
            insertPreco.setError("Campo tem que ser preenchido")
            insertPreco.requestFocus()
        }

        //campo Altura
        if(!TextUtils.isEmpty(insertAltura.text.toString())){
            v12 = 1
        }else{
            insertAltura.setError("Campo tem que ser preenchido")
            insertAltura.requestFocus()
        }

        //campo Largura
        if(!TextUtils.isEmpty(insertLargura.text.toString())){
            v13 = 1
        }else{
            insertLargura.setError("Campo tem que ser preenchido")
            insertLargura.requestFocus()
        }

        //campo Comprimento
        if(!TextUtils.isEmpty(insertComprimento.text.toString())){
            v14 = 1
        }else{
            insertComprimento.setError("Campo tem que ser preenchido")
            insertComprimento.requestFocus()
        }

        //campo Peso
        if(!TextUtils.isEmpty(insertPeso.text.toString())){
            v15 = 1
        }else{
            insertPeso.setError("Campo tem que ser preenchido")
            insertPeso.requestFocus()
        }

        if(v1 == 1 && v2 == 1 && v3 == 1 && v4 == 1 && v5 == 1 && v6 == 1 && v7 == 1 &&
            v8 == 1 && v9 == 1 && v10 == 1 && v11 == 1 && v12 == 1 && v13 == 1 && v14 == 1 && v15 == 1 ){return true}

        return  false
    }
}
