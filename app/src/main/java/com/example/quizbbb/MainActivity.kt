package com.example.quizbbb

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.VideoView
import com.example.quizbbb.Utils.USER_NAME
import com.example.quizbbb.databinding.ActivityMainBinding

private var binding: ActivityMainBinding? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        startVideo()

        // Criar variavel do editText do nome do usuário
        val etUserName: EditText = binding!!.etUserName

        // Criar variavel do botão de inicio
        val btnComecar: Button = binding!!.btnComecar
        // Checa se o campo de nome de usuário está vazio
        btnComecar.setOnClickListener {
            if (etUserName.text.toString().isEmpty()) {
                Toast.makeText(this, "Insira seu nome para começar!", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, QuestionsActivity::class.java)
                intent.putExtra(USER_NAME, etUserName.text.toString())
                startActivity(intent)
                finish()
            }
        }

    }

    // Função que ta play no vídeo de fundo
    private fun startVideo() {
        // Cria variável do videoView, seta seu local e o inicia
        val vvBackground = binding?.vvBackground
        vvBackground?.setVideoPath("android.resource://" + packageName + "/" + R.raw.v_main_background)
        vvBackground?.start()
        // Deixa vídeo em looping
        vvBackground?.setOnPreparedListener { it.isLooping = true }
    }


    // Sobrescrição da função onStart() para que o vídeo seja reiniciado quando o usuário sai e volta ao app
    override fun onStart() {
        super.onStart()
        startVideo()
    }

}