package com.example.quizbbb

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizbbb.Data.Participantes
import com.example.quizbbb.databinding.ActivityQuestionsBinding
import com.example.quizbbb.model.Participante

class QuestionsActivity : AppCompatActivity() {

    private var binding: ActivityQuestionsBinding? = null
    private var listaParticipantes: Map<Int, Participante> = Participantes.participantesLista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // Variáveis de vinculação das opções
        var opcao01 = binding?.tvPrimeiraOpcao
        var opcao02 = binding?.tvSegundaOpcao
        var opcao03 = binding?.tvTerceiraOpcao
        var opcao04 = binding?.tvQuartaOpcao

        // Função que randomiza um numero e escolhe o participante por id
        var participanteEscolhido: Participante?
        fun numRandom() {
            var tamanhoLista: Int = listaParticipantes.size
            var numRandom = (1..tamanhoLista).random()
            participanteEscolhido = listaParticipantes[numRandom]

           // Variavel da lista de opções
            var listaOpcoes: MutableList<String> = mutableListOf()

            //TODO: randomizar nomes dos participantes do mesmo genero do escolhido

            //TODO: adicionar nomes dos participantes em outra lista

            //TODO: adicionar o nome do participante + 3 nomes aleatorios na listaOpcoes

            //TODO: dar um shuffle nessa lista e transmitir os valores para as textView de opcoes


        }

        // Função que adiciona valores nas opções
        fun adicionaOpçoes(participante: Participante) {

        }



    }

}