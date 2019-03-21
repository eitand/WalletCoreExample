package com.sirinlabs.walletcoreexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet
import java.math.BigInteger

class MainActivity : AppCompatActivity() {

    init {
        System.loadLibrary("TrustWalletCore")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val words = "ripple scissors kick mammal hire column oak again sun offer wealth tomorrow wagon turn fatal"
        val password = ""

        val hdWallet = HDWallet(words, password)


        val privateKeyHex = hdWallet.getKeyForCoin(CoinType.ETHEREUM).data().toHex()

        // https://iancoleman.io/bip39/
        Log.d("TEST_WALLET_CORE", privateKeyHex)
    }


    // This is just helper function to transform a ByteArray to a Hex string
    private fun ByteArray.toHex(): String {
        return BigInteger(1, this).toString(16)
    }
}
