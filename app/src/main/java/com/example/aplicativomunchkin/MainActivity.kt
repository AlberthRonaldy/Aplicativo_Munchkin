package com.example.aplicativomunchkin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutGame()
        }
    }
}

@Composable
fun LayoutGame() {

    val jogadores = remember { mutableStateListOf<Player>() } // Lista de jogadores

    // Inicializando a lista com 6 jogadores vazios
    if (jogadores.isEmpty()) {
        repeat(6) {
            jogadores.add(Player("", 1, 0, 0, 0)) // Nome, level, bonusEquip, modificadores
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp) // Espaçamento entre os jogadores
    ) {
        items(jogadores.size) { index ->
            JogadorCard(jogador = jogadores[index], jogadorIndex = index + 1) { updatedPlayer ->
                jogadores[index] = updatedPlayer // Atualiza o jogador na lista
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JogadorCard(jogador: Player, jogadorIndex: Int, onUpdate: (Player) -> Unit) {
    var nome by remember { mutableStateOf(jogador.nome) }
    var level by remember { mutableStateOf(jogador.level) }
    var bonusEquip by remember { mutableStateOf(jogador.bonusEquip) }
    var modificadores by remember { mutableStateOf(jogador.modificadores) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(2.dp, Color.Blue, RoundedCornerShape(8.dp)) // Borda azul com cantos arredondados
            .background(Color.White) // Fundo branco
            .padding(16.dp), // Espaçamento interno

    ) {
        Text(text = "Jogador $jogadorIndex: ", fontSize = 25.sp)

        // Campo Nome do Jogador
        TextField(value = nome, onValueChange = {
            nome = it
            onUpdate(jogador.copy(nome = it)) // Atualiza o jogador na lista
        }, label = { Text(text = "Nome: ") })

        // Exibir Level
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { if (level > 1) level-- }) { Text("-") }
            Text(text = "Level: $level", modifier = Modifier.weight(1f), fontSize = 20.sp)
            Button(onClick = { if (level < 10) level++ }) { Text("+") }
        }

        // Exibir Bônus Equipamento
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { if (bonusEquip > 0) bonusEquip-- }) { Text("-") }
            Text(text = "Bônus Equipamento: $bonusEquip", modifier = Modifier.weight(1f), fontSize = 20.sp)
            Button(onClick = { if (bonusEquip < 99) bonusEquip++ }) { Text("+") }
        }

        // Exibir Modificadores
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { if (modificadores > -10) modificadores-- }) { Text("-") }
            Text(text = "Modificadores: $modificadores", modifier = Modifier.weight(1f), fontSize = 20.sp)
            Button(onClick = { if (modificadores < 10) modificadores++ }) { Text("+") }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Poder Total: ${level + bonusEquip + modificadores}",fontSize = 20.sp)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LayoutGame()
}