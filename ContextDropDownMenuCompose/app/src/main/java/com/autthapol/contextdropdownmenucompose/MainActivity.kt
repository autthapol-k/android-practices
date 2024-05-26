package com.autthapol.contextdropdownmenucompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.autthapol.contextdropdownmenucompose.ui.theme.ContextDropDownMenuComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContextDropDownMenuComposeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(
                                listOf(
                                    "Phillipp",
                                    "Martin",
                                    "Carl",
                                    "John",
                                    "Jake",
                                    "Melanie"
                                )
                            ) { name ->
                                PersonItem(
                                    name = name,
                                    dropDownItems = listOf(
                                        DropDownItem("Item 1"),
                                        DropDownItem("Item 2"),
                                        DropDownItem("Item 3")
                                    ),
                                    onItemClicked = {
                                        Toast.makeText(
                                            applicationContext,
                                            it.text,
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}