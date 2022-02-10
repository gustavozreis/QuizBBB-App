package com.example.quizbbb

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizbbb.Utils.PONTUACAO
import com.example.quizbbb.Utils.USER_NAME
import com.example.quizbbb.databinding.ActivityMainBinding
import com.example.quizbbb.databinding.ActivityResultsBinding

class ResultsActivity: AppCompatActivity() {

    private var binding: ActivityResultsBinding? = null
    private var tvFrasePersonalizada: TextView? = null
    private var pontuacao: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Referencia o textView da frase personalizada
        tvFrasePersonalizada = binding?.tvFrasePersonalizada

        // Referencia os valores recebidos pelo intent
        val nomeUsuario: String = intent.getStringExtra(USER_NAME).toString()
        pontuacao = intent.getStringExtra(PONTUACAO).toString()

        // Atualiza a frase com o nome e a pontuação do usuário
        val tvSuaPontuacao: TextView? = binding?.tvPontuacao
        tvSuaPontuacao?.text = "Você fez ${pontuacao} pontos, ${nomeUsuario}."

        // Botao 'Jogar novamente' que leva ao início do app
        val btnJogarNovamente: Button? = binding?.btnJogarNovamente
        btnJogarNovamente?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        atualizaFrasePersonalizada()

    }

    // Função que verifica pontuação final e retorna uma frase personalizada
    fun atualizaFrasePersonalizada() {
        when (pontuacao?.toInt()) {
            in 0..9 -> tvFrasePersonalizada?.text = "Eita, você realmente assiste BBB?"
            in 10..19 -> tvFrasePersonalizada?.text = "Podia ser pior, aposto que acertou só os mais famosos, né?"
            in 20..29 -> tvFrasePersonalizada?.text = "Hmmm, você acompanha mas não assiste muito né?"
            in 30..39 -> tvFrasePersonalizada?.text = "Dá pra ver que assistiu bastantes edições!"
            in 40..49 -> tvFrasePersonalizada?.text = "Quase perfeito, foi por pouco!"
            50 -> tvFrasePersonalizada?.text = "Parabéns, você é uma enciclopédia do BBB!"
        }
    }

    // Sobrescricao do botao voltar para que seja desabilitado
    override fun onBackPressed() {
    }
}


