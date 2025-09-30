package com.example.cadastrodeclientes.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cadastrodeclientes.model.Validacao
import com.example.cadastrodeclientes.model.entity.Cliente

class ClienteViewModel: ViewModel() {

    var listaClientes = mutableStateOf(listOf<Cliente>())
        private set

    fun salvarCliente(nome: String, email: String) : String {
        if (Validacao.haCamposEmBranco(nome, email)) {
            return "Preencha todos os campos!"
        }

        var cliente = Cliente(
            Validacao.getId(),
            nome,
            email
        )

        listaClientes.value += cliente

        return "Cliente salvo com sucesso!"
    }

    fun excluirCliente(id: Int) {
        listaClientes.value = listaClientes.value.filter { it.id != id }
    }

    fun atualizarCliente(id: Int, nome: String, email: String) : String {
        if (Validacao.haCamposEmBranco(nome, email)) {
            return ("Ao editar, preencha todos os dados do cliente!")
        }

        val clientesAtualizados = listaClientes.value.map { cliente ->
            if (cliente.id == id) {
                cliente.copy(nome = nome, email = email)
            } else {
                cliente
            }
        }

        listaClientes.value = clientesAtualizados

        return "Cliente atualizado!"
    }
}