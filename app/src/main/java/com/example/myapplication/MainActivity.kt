package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.content.ContextCompat.startActivity
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    var refresh by mutableStateOf(0)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposable(refresh = refresh)
        }
    }
    override fun onResume() {
        super.onResume()
        refresh++
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyComposable(refresh : Int){
    var wybrany: String
    var tydzien: Int
    MyApplicationTheme {
        val context = LocalContext.current
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = refresh.toString())
                wybrany = trening_ExposedDropdownMenu()
                tydzien = wybor_Tygodnia_ExposedDropDownMenu()

                Zacznij(wybrany, tydzien)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ){
                    Button(
                        onClick = {

                            val intent = Intent(context, ImportPlanu::class.java)
                            startActivity(context, intent, null)
                        },
                        modifier = Modifier

                            .align(Alignment.BottomEnd)
                    ) {
                        Text(text = "Edytuj Plan")
                    }
                }
            }

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun trening_ExposedDropdownMenu(): String {
    val context = LocalContext.current
    val file = File(context.filesDir, "TRENING_INFO")
    if (!file.exists()){
        Text(text = "Brak planu treningowego")
        return ""
    }
    val file2 = File(context.filesDir, "TRENING_NAZWY")
    if (!file2.exists()){
        Text(text = "Błąd")
        return ""
    }
    val Treningi = file2.readText().split("\n")
    var expanded by remember { mutableStateOf(false) }
    var wybranyTrening by remember { mutableStateOf(Treningi[0]) }
    var wasChosen by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        ) {
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(

                value = when (wasChosen) {
                    true -> wybranyTrening
                    false -> "Wybierz trening"
                },
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                Treningi.forEach { trening ->
                    DropdownMenuItem(
                        text = { Text(text  = trening) },
                        onClick = {
                            wasChosen = true
                            wybranyTrening = trening
                            expanded = false
                            Toast.makeText(context, "Wybrano trening: $wybranyTrening", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
    return wybranyTrening
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun wybor_Tygodnia_ExposedDropDownMenu(): Int {
    val context = LocalContext.current
    val file = File(context.filesDir, "TRENING_INFO")
    if (!file.exists()){
        return -2
    }
    val info = file.readText().split("\n")

    var firstDay = LocalDate.parse(info[0])
    val Tygodnie = mutableListOf<String>()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    var option by remember { mutableStateOf(-1) }

    for (i in 0 until info[1].toInt()) {
        val end = firstDay.plusDays(6)
        val tydzienn = "${firstDay.format(formatter)} - ${end.format(formatter)}"
        Tygodnie.add(tydzienn)
        firstDay = firstDay.plusDays(7)
    }
    var expanded by remember { mutableStateOf(false) }
    var wybranyTydzien by remember { mutableStateOf(Tygodnie[0]) }
    var wasChosen by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {

            TextField(

                value = when (wasChosen) {
                    true -> wybranyTydzien
                    false -> "Wybierz tydzień"
                },
                onValueChange = { /* Do nothing */ },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()

            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                Tygodnie.forEach { tydzien ->
                    DropdownMenuItem(
                        text = { Text(text  = tydzien) },
                        onClick = {
                            option = Tygodnie.indexOf(tydzien)
                            wasChosen = true
                            wybranyTydzien = tydzien
                            expanded = false
                            Toast.makeText(context, "Wybrano tydzień: $wybranyTydzien", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
    return 1
    //return option
}
@Composable
fun Zacznij(trening: String, numer_tygodnia: Int){
    val context = LocalContext.current
    if (numer_tygodnia == -2) return
    Button(
        onClick = {

            if (trening == "Wybierz trening" || numer_tygodnia == -1) {
                Toast.makeText(context, "Wybierz trening i tydzień", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(context, Trening::class.java)

                startActivity(context, intent, null)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Zacznij trening")
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Previev(modifier: Modifier = Modifier) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            //place the column slightly below the top of the screen


            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ){
            var wybrany = trening_ExposedDropdownMenu()
            var tydzien = wybor_Tygodnia_ExposedDropDownMenu()
            Zacznij(wybrany, tydzien)


        }
    }
}
