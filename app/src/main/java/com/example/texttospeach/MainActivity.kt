package com.example.texttospeach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.example.texttospeach.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    //binding
    private lateinit var binding : ActivityMainBinding

    private lateinit var etMessage  : AppCompatEditText
    private lateinit var tvMessage  : AppCompatTextView
    private lateinit var btnPlay    : AppCompatButton

    //Text to speach
    private val textToSpeach by lazy { TextToSpeech(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBinding()
        setupTextToSpeach()
    }

    private fun setupTextToSpeach(){
        textToSpeach.language = Locale("ES")

        btnPlay.setOnClickListener {
            val message = etMessage.text?.trim().toString()

            textToSpeach.speak(
                if (message.isNotEmpty()) message else "¡Escribe un texto!",
                TextToSpeech.QUEUE_FLUSH,
                null, "")

        }
    }

    override fun onInit(status: Int) {
        tvMessage.text =
            if(status == TextToSpeech.SUCCESS) "Speach Disponible"
            else {
                btnPlay.isEnabled = false
                "Speach No Disponible"
            }
    }

    private fun initBinding() {
        etMessage = binding.etMessage
        tvMessage = binding.tvMessage
        btnPlay   = binding.btnPlay
    }

    override fun onDestroy() {
        textToSpeach.apply {
            stop()
            shutdown()
        }
        super.onDestroy()
    }

}