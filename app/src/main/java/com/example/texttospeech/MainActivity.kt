package com.example.texttospeech

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.texttospeech.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() ,TextToSpeech.OnInitListener{

    private lateinit var binding:ActivityMainBinding
    private var tts:TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val workPermit = "General"
//        val missing = "helmet"
//        val st = "As per the $workPermit Work permit $missing missing "

        binding.btnSpeak.isEnabled = false
        tts = TextToSpeech(this,this)

        binding.btnSpeak.setOnClickListener {
            speakOut()
        }


    }

    private fun speakOut() {
        val text = binding.etInput.text.toString()
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onDestroy() {
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("er", "onInit: Language not supported", )
            }
            else{
                binding.btnSpeak.isEnabled = true

            }
        }

    }
}