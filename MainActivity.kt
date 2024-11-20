package com.example.sharedtohtml

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.os.VibrationEffect
import android.os.Vibrator

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var vibrator: Vibrator // Переменная для вибрации

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Настройка WebView
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("file:///android_asset/Magic.html")
        
        // Инициализация вибратора
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        // Передача функций в HTML код
        webView.addJavascriptInterface(WebAppInterface(this), "Android")
    }

    private inner class WebAppInterface(private val context: Context) {
        @JavascriptInterface
        fun vibrate() {
            if (vibrator.hasVibrator()) {
                // Вибрация на 200 миллисекунд
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vibrator.vibrate(200) // Для старых версий Android
                }
            }
        }
    }
}
