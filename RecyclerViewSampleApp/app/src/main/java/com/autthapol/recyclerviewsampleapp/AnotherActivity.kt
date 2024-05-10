package com.autthapol.recyclerviewsampleapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.autthapol.recyclerviewsampleapp.databinding.ActivityAnotherBinding

class AnotherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnotherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnotherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")

        binding.tvName.text = name

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setResult(RESULT_OK, Intent().apply {
                    putExtra("name", binding.tvName.text)
                })
                finish()
            }
        }

        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }
}