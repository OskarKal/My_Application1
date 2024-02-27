package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.PreviewTheme
import java.io.File

class ImportPlanu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var text by remember { mutableStateOf("") }
            var flag by remember { mutableStateOf(false) }
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        text = wprowadz_plan()
                        val context = LocalContext.current
                        Button(
                            modifier = Modifier
                                .padding(30.dp),
                            onClick = {
                                flag = true
                                Toast.makeText(context, "Plan treningowy został dodany", Toast.LENGTH_SHORT).show()
                            }

                        ) {
                            Text(text = "Zatwierdź")
                        }
                        if (flag){
                            dodaj_plan(text = text, context = context)
                            finish()
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun wprowadz_plan() : String {

    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(30.dp)
    )
    {
        TextField(
            modifier = Modifier
                .align(Alignment.TopCenter),
            value = text,
            onValueChange = { text = it },
            label = { Text("Wprowadz plan treningowy") }
        )
    }
    return text

}
@Composable
fun dodaj_plan(text : String, context : Context){
    val files: Array<String> = context.fileList()

    files.forEach { file ->
        if (file.contains("TRENING_")){
            //delete file
            context.deleteFile(file)
        }
    }
    var delimeter = ";"
    val parts = text.split(delimeter)
    delimeter = ","
    val info = parts[0].split(delimeter)
    //Zapisz wszystkie potrzebne informacje w pliku TRENING_INFO
    val file = File(context.filesDir, "TRENING_INFO")
    file.writeText(info.joinToString("\n"))
    //Zapisanie TRENIGÓW
    val file2 = File (context.filesDir, "TRENING_NAZWY")
    for (i in 1 until parts.size){
        val fille = File(context.filesDir, "TRENING_$i")
        val infoo = parts[i].split(delimeter)
        file2.appendText(infoo[0] + "\n")
        fille.writeText(info.joinToString("\n"))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    var text by remember { mutableStateOf("") }
    PreviewTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                text = wprowadz_plan()
                //val context = LocalContext.current
                Button(
                    modifier = Modifier
                        .padding(30.dp),
                    onClick = { /*dodaj_plan(text = text, context = context)*/ }

                ) {
                    Text(text = "Zatwierdź")
                }
            }
        }
    }
}



