package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tictactoe.ui.theme.TicTacToeTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun GameBoard(
    currentPlayer: Char,
    gameBoard: Array<Array<Char>>,
    onCellClick: (row: Int, column: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .size(280.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = Color.White,
                strokeWidth = 5f,
                start = Offset(x = size.width / 3, y = 0f),
                end = Offset(x = size.width / 3, y = size.height)
            )
            drawLine(
                color = Color.White,
                strokeWidth = 5f,
                start = Offset(x = size.width * 2 / 3, y = 0f),
                end = Offset(x = size.width * 2 / 3, y = size.height)
            )
            drawLine(
                color = Color.White,
                strokeWidth = 5f,
                start = Offset(x = 0f, y = size.height / 3),
                end = Offset(x = size.width, y = size.height / 3)
            )
            drawLine(
                color = Color.White,
                strokeWidth = 5f,
                start = Offset(x = 0f, y = size.height * 2 / 3),
                end = Offset(x = size.width, y = size.height * 2 / 3)
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            gameBoard.forEachIndexed { rowIndex, row ->
                Row {
                    row.forEachIndexed { columnIndex, cell ->
                        Cell(cell, rowIndex, columnIndex, onCellClick)
                    }
                }
            }
        }
    }
}

@Composable
fun Cell(
    cellValue: Char,
    rowIndex: Int,
    columnIndex: Int,
    onCellClick: (row: Int, column: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .padding(5.dp)
            .clickable {
                onCellClick(rowIndex, columnIndex)
            }
            .background(Color(180, 180, 180)),
        contentAlignment = Alignment.Center
    ) {
        when (cellValue) {
            'O' -> O()
            'X' -> X()
        }
    }
}

@Composable
fun O() {
    Canvas(
        modifier = Modifier
            .size(60.dp)
            .padding(5.dp)
    ) {
        drawCircle(
            color = Color.White,
            style = Stroke(width = 20f)
        )
    }
}

@Composable
fun X() {
    Canvas(
        modifier = Modifier
            .size(60.dp)
            .padding(5.dp)
    ) {
        drawLine(
            color = Color(30,30,30),
            strokeWidth = 20f,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = size.height)
        )
        drawLine(
            color = Color(30,30,30),
            strokeWidth = 20f,
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        )
    }
}

@Composable
fun MainScreen() {
    var currentPlayer by remember { mutableStateOf('O') }
    var gameBoard by remember { mutableStateOf(Array(3) { Array(3) { ' ' } }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Player 'O': 0", fontSize = 17.sp)
            Text(text = "Draw: 0", fontSize = 17.sp)
            Text(text = "Player 'X': 0", fontSize = 17.sp)
        }
        Text(
            text = "Tic Tac Toe",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color(30, 30, 30)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(180, 180, 180)),
            contentAlignment = Alignment.Center
        ) {
            GameBoard(
                currentPlayer = currentPlayer,
                gameBoard = gameBoard
            ) { row, column ->
                if (gameBoard[row][column] == ' ') {
                    gameBoard[row][column] = currentPlayer
                    currentPlayer = if (currentPlayer == 'O') 'X' else 'O'
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Player 'O' turn",
                fontSize = 20.sp,
            )
            Button(
                onClick = { },
                shape = RoundedCornerShape(7.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(30,30,30)
                )
            ) {
                Text(
                    text = "Play Again",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

