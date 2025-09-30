package com.example.cadastrodeclientes.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cadastrodeclientes.viewmodel.ClienteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var id by remember { mutableStateOf(0) }
    var textoBotao by remember { mutableStateOf("Salvar") }
    var modoEditar by remember { mutableStateOf(false) }

    var clienteViewModel : ClienteViewModel = viewModel()
    var listaClientes by clienteViewModel.listaClientes
    val context = LocalContext.current
    var focusManager = LocalFocusManager.current

    var mostrarCaixaDialogo by remember { mutableStateOf(false) }

    if (mostrarCaixaDialogo) {
        ExcluirFilme(onConfirm = {
            clienteViewModel.excluirCliente(id)
            mostrarCaixaDialogo = false
        }
            ,
            onDismiss = { mostrarCaixaDialogo = false}
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)) {

        Text(text = "Lista de Clientes", modifier = Modifier.fillMaxWidth(),
            fontSize = 22.sp)

        Spacer(modifier = Modifier.height(15.dp))

        TextField(value = nome,
            onValueChange = { nome = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Nome do cliente")})

        Spacer(modifier = Modifier.height(15.dp))

        TextField(value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "E-mail do cliente")})

        Spacer(modifier = Modifier.height(15.dp))

        Button(modifier = Modifier.fillMaxWidth(),
            onClick = {

                var retorno = ""

                if (modoEditar) {
                    retorno = clienteViewModel.atualizarCliente(id, nome, email)
                    modoEditar = false
                    textoBotao = "Salvar"

                } else {
                    retorno = clienteViewModel.salvarCliente(nome, email)
                }

                Toast.makeText(
                    context,
                    retorno,
                    Toast.LENGTH_LONG
                ).show()

                nome = ""
                email = ""
                focusManager.clearFocus()

            }) {
            Text(text = textoBotao)
        }

        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn {

            items(listaClientes) { cliente ->

                Text(text = "${cliente.nome} (${cliente.email})",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 18.sp)

                Spacer(modifier = Modifier.height(5.dp))

                Row {
                    Button(onClick = {
                        id = cliente.id
                        mostrarCaixaDialogo = true
                    }) {
                        Text(text = "Excluir")
                    }

                    Button(onClick = {

                        modoEditar = true
                        id = cliente.id
                        nome = cliente.nome
                        email = cliente.email
                        textoBotao = "Atualizar"

                    }) {
                        Text(text = "Atualizar")
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                }
            }

        }

    }

}

@Composable
fun ExcluirFilme(onConfirm: () -> Unit, onDismiss: () -> Unit) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Confirmar exclusão")},
        text = { Text(text = "Tem certeza que deseja excluir este cliente?")},
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "Sim, excluir")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Não, cancelar")
            }
        })

}