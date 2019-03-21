package com.sirinlabs.walletcoreexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import wallet.core.jni.HDWallet

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val words = "ripple scissors kick mammal hire column oak again sun offer wealth tomorrow wagon turn fatal"
        val password = ""

        val hdWallet = HDWallet(words, password)

        Log.d("test", hdWallet.seed().toString())
    }
}
