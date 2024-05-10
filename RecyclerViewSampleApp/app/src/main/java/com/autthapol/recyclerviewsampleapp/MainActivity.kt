package com.autthapol.recyclerviewsampleapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.autthapol.recyclerviewsampleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ItemListAdapter { item ->
            startAnotherActivity(item)
        }
        binding.recyclerview.adapter = adapter

        adapter.submitList(createItems())
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val name = result.data?.getStringExtra("name")
                Toast.makeText(this, name, Toast.LENGTH_LONG).show()
            }
        }

    private fun startAnotherActivity(item: Item) {
        activityResultLauncher.launch(Intent(this, AnotherActivity::class.java).apply {
            putExtra("name", item.name)
        })
    }

    private fun createItems() = listOf(
        Item(
            id = 1,
            name = "The standard Lorem Ipsum passage, used since the 1500s"
        ),
        Item(
            id = 2,
            name = "Section 1.10.32 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC"
        ),
        Item(
            id = 3,
            name = "1914 translation by H. Rackham"
        ),
        Item(
            id = 4,
            name = "Section 1.10.33 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC"
        ),
        Item(
            id = 5,
            name = "1914 translation by H. Rackham"
        )
    )
}