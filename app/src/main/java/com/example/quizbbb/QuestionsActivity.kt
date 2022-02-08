package com.example.quizbbb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizbbb.Data.Participantes
import com.example.quizbbb.databinding.ActivityQuestionsBinding
import com.example.quizbbb.model.Participante
import org.w3c.dom.Text

class QuestionsActivity : AppCompatActivity() {

    var binding: ActivityQuestionsBinding? = null

    // Lista de participantes que vai ser usada como referencia dos participantes ja escolhidos
    private var listaParticipantes: MutableMap<Int, Participante> = Participantes.participantesLista.toMutableMap()

    // Lista estatica de participantes para pegar seus nomes como outras opções a serem escolhidas
    private var listaParticipantesEstatica: Map<Int, Participante> = Participantes.participantesLista

    private val participantesPassados: MutableList<Int> = mutableListOf()

    // Variáveis de vinculação das opções
    var opcao01: TextView? = null
    var opcao02: TextView? = null
    var opcao03: TextView? = null
    var opcao04: TextView? = null
    var participanteFoto: ImageView? = null

    // Variável que acompanha em qual rodada estamos
    private var rodada: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // Variáveis de vinculação das opções
        opcao01 = binding?.tvPrimeiraOpcao
        opcao02 = binding?.tvSegundaOpcao
        opcao03 = binding?.tvTerceiraOpcao
        opcao04 = binding?.tvQuartaOpcao
        participanteFoto = binding?.ivFotoParticipante

        numRandom()

        // Setar nova questão ao clicar no botão 'Responder'
        val btnResponder = binding?.btResponder
        btnResponder?.setOnClickListener {
            numRandom()
        }

        // Cria lista para adicionar os participantes que já foram escolhidos para que não repitam


    }

    fun numRandom() {

        if (rodada <= 10) {

            var tamanhoLista: Int = listaParticipantes.size

            // Lista com os valores de id dos participantes restantes
            var listaParticipantesRestantes: MutableList<Int> = mutableListOf()
            for ((key, value) in listaParticipantes) {
                listaParticipantesRestantes.add(key)
            }

            var numRandom: Int = (listaParticipantesRestantes).random()

            // Variavel para o participante escolhido da questao
            var participanteEscolhido: Participante? = listaParticipantes[numRandom]

            // Variavel da lista de opções
            var listaOpcoes: MutableList<String> = mutableListOf()

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

            // Adiciona ID do participante na lista de escolhidos para não repetir
            listaParticipantes.remove(participanteEscolhido?.id)

            // Adicionar o nome do participante + 3 nomes aleatorios na listaOpcoes
            listaOpcoes.add(participanteEscolhido!!.nome)
            listaOpcoes.add(listaRandomizada[0])
            listaOpcoes.add(listaRandomizada[1])
            listaOpcoes.add(listaRandomizada[2])
            // Randomizar ordem da lista com as 4 opções
            listaOpcoes.shuffle()

            // Transmitir os valores para as textView de opcoes
            opcao01?.text = listaOpcoes[0]
            opcao02?.text = listaOpcoes[1]
            opcao03?.text = listaOpcoes[2]
            opcao04?.text = listaOpcoes[3]
            participanteFoto?.setImageResource(participanteEscolhido.foto)

            listaOpcoes.clear()

            rodada += 1

        } else {
            val intent = Intent(this, ResultsActivity::class.java)
            startActivity(intent)
        }
    }

}