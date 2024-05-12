@file:OptIn(ExperimentalFoundationApi::class)

package com.autthapol.stickyheaderlistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.autthapol.stickyheaderlistapp.ui.theme.StickyHeaderListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StickyHeaderListAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CategorizedLazyColumn(categories = names.map {
                        Category(it.key.toString(), it.value)
                    })
                }
            }
        }
    }
}

@Composable
fun CategoryHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun CategoryItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        text = text,
        fontSize = 14.sp,
    )
}

@Composable
fun CategorizedLazyColumn(
    categories: List<Category>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        categories.forEach { category ->
            stickyHeader {
                CategoryHeader(text = category.name)
            }
            items(category.items) { text ->
                CategoryItem(text = text)
            }
        }
    }
}