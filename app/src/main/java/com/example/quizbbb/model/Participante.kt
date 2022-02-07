package com.example.quizbbb.model

data class Participante(
    val id: Int,
    val nome: String,
    val edicaoQueParticipou: String,
    val genero: String,
    val foto: Int
) {
}