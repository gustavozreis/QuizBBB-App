package com.example.quizbbb

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.quizbbb.Data.Participantes
import com.example.quizbbb.Utils.*
import com.example.quizbbb.databinding.ActivityQuestionsBinding
import com.example.quizbbb.model.Participante
import org.w3c.dom.Text

class QuestionsActivity : AppCompatActivity() {

    // Dado passado pelo intent: nome de usuário
    var nomeUsuario: String? = null

    var binding: ActivityQuestionsBinding? = null

    // Lista de participantes que vai ser usada como referencia dos participantes ja escolhidos
    private var listaParticipantes: MutableMap<Int, Participante> =
        Participantes.participantesLista.toMutableMap()

    // Lista estatica de participantes para pegar seus nomes como outras opções a serem escolhidas
    private var listaParticipantesEstatica: Map<Int, Participante> =
        Participantes.participantesLista

    private val participantesPassados: MutableList<Int> = mutableListOf()

    // Variáveis de vinculação das opções
    private var opcao01: TextView? = null
    private var opcao02: TextView? = null
    private var opcao03: TextView? = null
    private var opcao04: TextView? = null
    private var participanteFoto: ImageView? = null
    private var btnResposta: Button? = null
    private var tvSuaPontuacao: TextView? = null
    private var tvPontosDaPergunta: TextView? = null
    private var progressBar: ProgressBar? = null
    private var btDica: Button? = null
    private var btExcluiOpcao: Button? = null


    // Variável que acompanha a pontuação do usuário
    var pontuacaoUsuario: String = ""

    // Variável que mostra a pontuação que essa rodada vale
    var pontosDaRodada: Int = 5

    // Variável que acompanha em qual rodada estamos
    private var rodada: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_questions)
        binding?.questionsActivity = this@QuestionsActivity

        // Variáveis de vinculação das opções
        opcao01 = binding?.tvPrimeiraOpcao
        opcao02 = binding?.tvSegundaOpcao
        opcao03 = binding?.tvTerceiraOpcao
        opcao04 = binding?.tvQuartaOpcao
        participanteFoto = binding?.ivFotoParticipante
        btnResposta = binding?.btResponder
        tvSuaPontuacao = binding?.tvPontuacao
        tvPontosDaPergunta = binding?.tvPontosNessaPergunta
        progressBar = binding?.pbContagemPerguntas
        btDica = binding?.btDica
        btExcluiOpcao = binding?.btExcluirOpcao

        // Referencia nomeUsuario pegado no intent
        nomeUsuario = intent.getStringExtra(USER_NAME).toString()

        gerarOpcoes()

    }

    // Variável que recebe o nome da resposta correta
    private var respostaCorreta: String = ""

    // Variável que recebe a opção escolhida
    private var opcaoEscolhida: String = ""

    // Instancia do participante escolhido para ser usado fora do escopo da função 'gerarOpcoes'
    private var participanteEscolhidoOut: Participante? = null

    // Variável da lista de opções para ser usada na função de excluir opções
    private var listaOpcoesExcluir: MutableList<String> = mutableListOf()

    /*
    Função para altera a cor da resposta escolhida
    */
    fun alteraCorEscolha(view: TextView) {
        if (btnResposta?.text == RESPONDER) {
            opcao01?.setBackgroundResource(R.drawable.questions_background)
            opcao02?.setBackgroundResource(R.drawable.questions_background)
            opcao03?.setBackgroundResource(R.drawable.questions_background)
            opcao04?.setBackgroundResource(R.drawable.questions_background)
            view.setBackgroundResource(R.drawable.questions_background_chosen)
            opcaoEscolhida = view.text.toString()
        }
    }

    /*
    Função que randomiza um participante como correto e altera o nome das opções
    */

    fun gerarOpcoes() {
        var tamanhoLista: Int = listaParticipantes.size

        // Esvazia lista das opcoes de exclusao da funcao 'excluirOpcao' para que o app nao quebre
        listaOpcoesExcluir.clear()

        // Lista com os valores de id dos participantes restantes
        val listaParticipantesRestantes: MutableList<Int> = mutableListOf()
        for ((key, value) in listaParticipantes) {
            listaParticipantesRestantes.add(key)
        }

        val numRandom: Int = (listaParticipantesRestantes).random()

        // Variavel para o participante escolhido da questao
        val participanteEscolhido: Participante? = listaParticipantes[numRandom]

        // Seta o participante para ser usado fora do escopo dessa função
        participanteEscolhidoOut = participanteEscolhido

        // Variavel da lista de opções
        val listaOpcoes: MutableList<String> = mutableListOf()

        // Variavel da lista de nomes dos outros participantes para ser randomizada
        val listaRandomizada: MutableList<String> = mutableListOf()

        // Randomizar nomes dos outros participantes do mesmo genero do escolhido
        for ((key: Int, value: Participante) in listaParticipantesEstatica) {
            if (key != participanteEscolhido?.id
                && value.genero == participanteEscolhido?.genero
            ) {
                listaRandomizada.add(value.nome)
            }
        }
        listaRandomizada.shuffle()

        // Remove ID do participante na lista de escolhidos para não repetir nas proximas perguntas
        listaParticipantes.remove(participanteEscolhido?.id)

        // Definir o nome do participante que é a resposta correta
        respostaCorreta = participanteEscolhido!!.nome

        // Adicionar o nome do participante + 3 nomes aleatorios na listaOpcoes
        listaOpcoes.add(participanteEscolhido.nome)
        listaOpcoes.add(listaRandomizada[0])
        listaOpcoes.add(listaRandomizada[1])
        listaOpcoes.add(listaRandomizada[2])

        // Randomizar ordem da lista com as 4 opções
        listaOpcoes.shuffle()

        // Transfere o valor da lista de opções para variável da função que elimina uma opção
        listaOpcoesExcluir.addAll(listaOpcoes)

        // Transmitir os valores para as textView de opcoes
        opcao01?.text = listaOpcoes[0]
        opcao02?.text = listaOpcoes[1]
        opcao03?.text = listaOpcoes[2]
        opcao04?.text = listaOpcoes[3]
        participanteFoto?.setImageResource(participanteEscolhido.foto)

        listaOpcoes.clear()
    }

    /*
    Função reseta as cores das opções
    */
    fun resetarCores() {
        opcao01?.setBackgroundResource(R.drawable.questions_background)
        opcao02?.setBackgroundResource(R.drawable.questions_background)
        opcao03?.setBackgroundResource(R.drawable.questions_background)
        opcao04?.setBackgroundResource(R.drawable.questions_background)
    }

    /*
    Função que checa se a resposta está correta
    */
    fun checaResposta(): Boolean {
        return opcaoEscolhida == respostaCorreta
    }

    /*
    Função que coloriza as opções após escolha e envio da resposta
    */
    fun colorizaOpcoes() {

        if (opcaoEscolhida == "") {
            Toast.makeText(this, "Escolha uma opção!", Toast.LENGTH_SHORT).show()
        } else {
            val listaOpcoes: MutableList<TextView?> =
                mutableListOf(opcao01, opcao02, opcao03, opcao04)
            // Resposta certa
            if (checaResposta()) {
                btnResposta?.text = CONTINUAR
                atualizaPontuacao()
                for (opcoes in listaOpcoes) {
                    if (opcoes?.text == respostaCorreta) {
                        opcoes.setBackgroundResource(R.drawable.questions_background_correct)
                    } else {
                        opcoes?.setBackgroundResource(R.drawable.questions_background)
                    }
                }
            // Resposta Errada
            } else {
                btnResposta?.text = CONTINUAR
                for (opcoes in listaOpcoes) {
                    if (opcoes?.text == respostaCorreta) {
                        opcoes.setBackgroundResource(R.drawable.questions_background_correct)
                    } else if (opcoes?.text == opcaoEscolhida) {
                        opcoes.setBackgroundResource(R.drawable.questions_background_wrong)
                    } else if (opcoes?.text != opcaoEscolhida || opcoes.text != respostaCorreta) {
                        opcoes?.setBackgroundResource(R.drawable.questions_background)
                    }
                }
            }
        }
    }

    /*
    Função que atualiza a pontuação do usuário
    */
    fun atualizaPontuacao() {
        var pontosAtuais: Int = tvSuaPontuacao?.text.toString().toInt()
        val pontosDaRodada: Int = tvPontosDaPergunta?.text.toString().toInt()
        pontosAtuais += pontosDaRodada
        tvSuaPontuacao?.text = pontosAtuais.toString()
    }

    /*
    Função que atualiza a progress bar
    */
    fun atualizaProgressBar() {
        var progressoAtual: Int = progressBar!!.progress
        progressoAtual = progressoAtual.plus(10)
        progressBar?.progress = progressoAtual
    }

    /*
    Função que dá a dica
    */
    fun darDica() {
        if (!(btDica?.text != DICA || btnResposta?.text != RESPONDER)) {
            btDica?.text = participanteEscolhidoOut?.edicaoQueParticipou
            var pontosDaRodada: Int = tvPontosDaPergunta?.text.toString().toInt()
            pontosDaRodada -= 1
            tvPontosDaPergunta?.text = pontosDaRodada.toString()
        }
    }

    /*
    Função que reseta o botão de dica para o estao original
    */
    fun resetaDica() {
        btDica?.text = DICA
    }

    /*
    Função elimina uma das opçõe de resposta
    */
    fun excluiOpcao() {

        val listaExcluir: MutableList<String> = listaOpcoesExcluir

        val nomeParticipanteEscolhido: String = participanteEscolhidoOut!!.nome
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listaExcluir.removeIf { nome: String -> nome == nomeParticipanteEscolhido }
        }

        listaExcluir.shuffle()
        val nomeParaExcluir: String = listaExcluir[0]

        val listaOpcoes: MutableList<TextView?> = mutableListOf(opcao01, opcao02, opcao03, opcao04)

        if (btnResposta?.text == RESPONDER) {
            for (opcoes in listaOpcoes) {
                if (opcoes?.text == nomeParaExcluir) {
                    opcoes.isVisible = false

                    var pontosDaRodada: Int = tvPontosDaPergunta?.text.toString().toInt()
                    pontosDaRodada -= 1
                    tvPontosDaPergunta?.text = pontosDaRodada.toString()
                    btExcluiOpcao?.isEnabled = false


                }
            }
        }
    }

    /*
    Função que leva o usuário para a activity final
    */
    fun irParaOFinal() {

        pontuacaoUsuario = tvSuaPontuacao?.text.toString()
        val intent = Intent(this, ResultsActivity::class.java)
        intent.putExtra(USER_NAME, nomeUsuario)
        intent.putExtra(PONTUACAO, pontuacaoUsuario)
        startActivity(intent)
        finish()
    }

    /*
    Função que retorna as opções (TextView) para visiveis
    */
    fun retornaOpcoes () {
        val listaOpcoes: MutableList<TextView?> = mutableListOf(opcao01, opcao02, opcao03, opcao04)
        for (opcoes in listaOpcoes) {
            opcoes?.isVisible = true
        }
    }


    fun cliqueDoBotaoResposta() {

        if (rodada < 10 && btnResposta?.text == RESPONDER) {
            colorizaOpcoes()
        } else if (rodada < 10 && btnResposta?.text == CONTINUAR) {
            gerarOpcoes()
            resetarCores()
            opcaoEscolhida = ""
            rodada += 1
            atualizaProgressBar()
            btnResposta?.text = RESPONDER
            tvPontosDaPergunta?.text = "5"
            resetaDica()
            retornaOpcoes()
            btExcluiOpcao?.isEnabled = true
        } else if  (rodada == 10 && (btnResposta!!.text == RESPONDER)) {
            colorizaOpcoes()
            btnResposta?.text = FINALIZAR
            btnResposta?.setOnClickListener {
                irParaOFinal()
            }
        }
    }

}