package com.example.quizbbb

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizbbb.Utils.PONTUACAO
import com.example.quizbbb.Utils.USER_NAME
import com.example.quizbbb.databinding.ActivityMainBinding
import com.example.quizbbb.databinding.ActivityResultsBinding

class ResultsActivity: AppCompatActivity() {

    private var binding: ActivityResultsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // Referencia os valores recebidos pelo intent
        var nomeUsuario: String = intent.getStringExtra(USER_NAME).toString()

        var pontuacao: String = intent.getStringExtra(PONTUACAO).toString()


        var tvSuaPontuacao: TextView = binding!!.tvPontuacao
        tvSuaPontuacao.text = "Você fez ${pontuacao} pontos, ${nomeUsuario}!"


    }
}


