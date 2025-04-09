package com.example.calculadoraapp2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.calculadoraapp2025.ui.theme.CalculadoraApp2025Theme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraApp2025Theme {
                Calculadora()
            }
        }
    }
}

@Composable
fun Calculadora() {
    var result by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF121212) // fondo modo oscuro
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = result.ifBlank { "0" },
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(24.dp))

            val buttons = listOf(
                "7", "8", "9", "÷",
                "4", "5", "6", "×",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
            )

            buttons.chunked(4).forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { button ->
                        val backgroundColor = when (button) {
                            "C" -> Color(0xFF2979FF) // Azul fuerte
                            "=" -> Color(0xFF00C853) // Verde
                            "+", "-", "×", "÷" -> Color(0xFF7C4DFF) // Morado
                            else -> Color(0xFF2C2C2C) // Gris oscuro
                        }

                        val textColor = if (button in listOf("C", "=", "+", "-", "×", "÷")) {
                            Color.White
                        } else {
                            Color(0xFFE0E0E0)
                        }

                        Button(
                            onClick = {
                                result = when (button) {
                                    "=" -> {
                                        try {
                                            val cleanExpr = result.replace("×", "*").replace("÷", "/")
                                            eval(cleanExpr).toString()
                                        } catch (e: Exception) {
                                            "Error"
                                        }
                                    }
                                    "C" -> ""
                                    else -> result + button
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(64.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = backgroundColor,
                                contentColor = textColor
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = button,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }
            }
        }
    }
}



fun eval(expression: String): Double {
    val expr = ExpressionBuilder(expression).build()
    return expr.evaluate()
}
