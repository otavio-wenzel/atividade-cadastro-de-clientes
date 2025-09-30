package com.example.cadastrodeclientes.model

abstract class Validacao {

    companion object {

        private var id = 0

        fun getId() : Int {
            return id++
        }

        fun haCamposEmBranco(nome: String, email: String) : Boolean {
            return nome.isBlank() || email.isBlank()
        }
    }
}