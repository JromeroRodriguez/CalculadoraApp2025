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

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = result,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            val buttons = listOf(
                "7", "8", "9", "÷",
                "4", "5", "6", "×",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
            )

            buttons.chunked(4).forEach { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    row.forEach { button ->
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
                                .padding(8.dp)
                        ) {
                            Text(button)
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
