package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.io.File
/*
Class Cwiczenie(
    val nazwa: String,
    val czyFormulka: Boolean,
    val iloscSerii: Int,
    val iloscPowtorzen: Array<Int>,
    val PersonalRecord: Int,
    //przy cwiczeniach typu ab roller nie potrzebujemy wagi
    val czyPodacWage: Boolean = true
){

}*/
class Trening : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    val context = LocalContext.current
                    var intt = intent
                    var tydzien = intt.getIntExtra("tydzien", -1)
                    if (tydzien == -1) {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp)){
                            Text("Nie udało się pobrać tygodnia", modifier = Modifier
                                .padding(16.dp)
                                .align(
                                    Alignment.Center
                                ))
                        }
                        return@Surface
                    }
                    var wybrany = intt.getIntExtra("wybrany", -1)
                    if (wybrany == -1){
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp)){
                            Text("Nie udało się pobrać treningu", modifier = Modifier
                                .padding(16.dp)
                                .align(
                                    Alignment.Center
                                ))
                        }
                        return@Surface

                    }
                    val NazwaTreningu : String
                    val LiczbaCwiczen : Int

                    try {
                        context.openFileInput("TRENING_$wybrany")
                    } catch (e: Exception) {
                        Text(text ="Nie udało się otworzyć pliku z treningiem")
                    }

                }
            }
        }
    }
}

@Composable
fun pokaz_cwiczenie(){

}

@Composable
fun dobierz_wage(){
    
}

@Composable
fun dobierz_talerze(){

}

@Composable
fun ocena_trudnosci(){

}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

        }

    }
}