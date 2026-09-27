package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

// ---------- Palet warna kustom (biar tampilannya "bagus") ----------
private val BackgroundColor = Color(0xFFF7F8FC)
private val CardColor = Color(0xFFFFFFFF)
private val Primary = Color(0xFF4353E7)
private val PrimaryDark = Color(0xFF2F3BB0)
private val TextDark = Color(0xFF212121)
private val TextGray = Color(0xFF6B6F80)
private val Accent = Color(0xFFFF4081)

@Composable
fun DiceRollerApp() {

    // Dua dadu sekaligus (dadu ganda)
    var diceNumber1 by remember { mutableIntStateOf(1) }
    var diceNumber2 by remember { mutableIntStateOf(1) }
    var rotation1 by remember { mutableStateOf(0f) }
    var rotation2 by remember { mutableStateOf(0f) }
    var rollCount by remember { mutableIntStateOf(0) }
    val historyList = remember { mutableStateListOf<String>() }

    // Animasi putar masing-masing dadu saat tombol ditekan
    val animatedRotation1 by animateFloatAsState(
        targetValue = rotation1,
        animationSpec = tween(durationMillis = 450),
        label = "diceRotation1"
    )
    val animatedRotation2 by animateFloatAsState(
        targetValue = rotation2,
        animationSpec = tween(durationMillis = 450),
        label = "diceRotation2"
    )

    fun diceDrawable(number: Int): Int {
        return when (number) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_1
        }
    }

    fun rollDice() {
        diceNumber1 = Random.nextInt(1, 7)
        diceNumber2 = Random.nextInt(1, 7)
        rotation1 += 360f
        rotation2 += 360f
        rollCount += 1
        val total = diceNumber1 + diceNumber2
        historyList.add(0, "Kocokan #$rollCount  →  D1=$diceNumber1, D2=$diceNumber2  (Total: $total)")
        if (historyList.size > 15) historyList.removeAt(historyList.lastIndex)
    }

    fun resetHistory() {
        historyList.clear()
        rollCount = 0
        diceNumber1 = 1
        diceNumber2 = 1
        rotation1 = 0f
        rotation2 = 0f
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // ----- Header -----
        Text(
            text = "🎲 Dice Roller",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
        Text(
            text = "Ketuk tombol di bawah untuk mengocok dua dadu",
            fontSize = 14.sp,
            color = TextGray,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        // ----- Kartu dadu -----
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Dua gambar dadu berdampingan
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = diceDrawable(diceNumber1)),
                        contentDescription = "Dadu pertama menunjukkan angka $diceNumber1",
                        modifier = Modifier
                            .size(140.dp)
                            .graphicsLayer { rotationZ = animatedRotation1 }
                    )
                    Image(
                        painter = painterResource(id = diceDrawable(diceNumber2)),
                        contentDescription = "Dadu kedua menunjukkan angka $diceNumber2",
                        modifier = Modifier
                            .size(140.dp)
                            .graphicsLayer { rotationZ = animatedRotation2 }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Total: ${diceNumber1 + diceNumber2}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDark
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { rollDice() },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "KOCOK DADU",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ----- Kartu riwayat -----
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Riwayat Kocokan",
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = "RESET",
                        color = Accent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { resetHistory() }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (historyList.isEmpty()) {
                    Text(
                        text = "Belum ada riwayat kocokan.",
                        fontSize = 13.sp,
                        color = TextGray
                    )
                } else {
                    LazyColumn {
                        items(historyList) { entry ->
                            Text(
                                text = entry,
                                fontSize = 13.sp,
                                color = TextGray,
                                modifier = Modifier.padding(vertical = 3.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}