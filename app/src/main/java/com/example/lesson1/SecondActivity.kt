package com.example.lesson1

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson1.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

  //мы пишем здесь как мы работаем с ней, для того чтобы меня запутили мне нужны данные
    companion object {
        const val KEY = "key" // если здесь указывать, а не наверху, то читабильность будет хуже
        // в компанент обжекте не рекомендуется делать что либо
    }

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val value = intent.extras?.getString(KEY)
       // intent.getStringExtra(KEY) или так можно, но не забываем про Null

        Toast.makeText(this, value, Toast.LENGTH_LONG).show()
    }
}
