package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.diceroller.ui.theme.DiceRollerTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DiceRollerTheme {
                DiceRollerApp()
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun DiceRollerApp() {

    var diceNumber by remember {
        mutableStateOf(1)
    }

    val diceImage = when (diceNumber) {
        1 -> com.example.diceroller.R.drawable.dice_1
        2 -> com.example.diceroller.R.drawable.dice_2
        3 -> com.example.diceroller.R.drawable.dice_3
        4 -> com.example.diceroller.R.drawable.dice_4
        5 -> com.example.diceroller.R.drawable.dice_5
        6 -> com.example.diceroller.R.drawable.dice_6
        else -> com.example.diceroller.R.drawable.dice_1
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Dice Roller"
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Image(
            painter = painterResource(id = diceImage),
            contentDescription = "Gambar dadu",
            modifier = Modifier.size(200.dp)
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Button(
            onClick = {
                diceNumber = Random.nextInt(1, 7)
            }
        ) {
            Text(
                text = "Kocok Dadu"
            )
        }
    }
}