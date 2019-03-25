package com.sirinlabs.walletcoreexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import wallet.core.jni.*
import java.math.BigInteger

class MainActivity : AppCompatActivity() {

    private val TAG: String? = MainActivity::class.simpleName

    init {
        System.loadLibrary("TrustWalletCore")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val words = "ripple scissors kick mammal hire column oak again sun offer wealth tomorrow wagon turn fatal"
        val password = ""

        // create 15 word mnemonic
        val hdWallet15 = HDWallet(160, password)
        val valid = HDWallet.isValid(hdWallet15.mnemonic())
        val wordList2 = hdWallet15.mnemonic().split(" ")
        Log.d(TAG, "Valid - $valid " + wordList2.joinToString { s -> s })

        //create 24 words mnemonic
        val hdWallet24 = HDWallet(256, password)
        val valid1 = HDWallet.isValid(hdWallet24.mnemonic())
        val wordList3 = hdWallet24.mnemonic().split(" ")
        Log.d(TAG, "Valid - $valid1 " + wordList3.joinToString { s -> s })

        // create a wallet with mnemonic
        val hdWallet = HDWallet(words, password)

        val seed = hdWallet.seed().toHex()
        Log.d("TEST_WALLET_CORE", "Seed - $seed")

        val hdwallet2 = HDWallet(seed, password)
        val bitcoin = CoinType.BITCOIN
        val curve = bitcoin.curve()


        //test get key for coin
        val privateKey1 = hdWallet.getKeyForCoin(bitcoin)
        val address1 = bitcoin.deriveAddress(privateKey1)
        val privateKey1b = hdwallet2.getKeyForCoin(bitcoin)
        val address1b = bitcoin.deriveAddress(privateKey1b)
        val privateKey2 = hdWallet.getKeyForCoin(bitcoin)
        val address2 = bitcoin.deriveAddress(privateKey2)
        Log.d(TAG, "privKey 1 BTC - $privateKey1")
        Log.d(TAG, "privKey 2 BTC - $privateKey2")
        Log.d(TAG, "address 1 BTC - $address1")
        Log.d(TAG, "address 2 BTC - $address2")
        Log.d(TAG, "privKey 1 == priveKey1 (hd wallet 2) ? " + (privateKey1.data().toHex() == privateKey1b.data().toHex()))
        Log.d(TAG, "address 1 == address 1 (hd wallet 2) ? " + (address1 == address1b))


        val newPrivateKey = PrivateKey(privateKey1.data())
        Log.d(TAG, "privKey 1 BTC - $privateKey1 == $newPrivateKey ? " + privateKey1.data().toHex().equals(newPrivateKey.data().toHex()))

        bitcoin.derivationPath()

//        val privateKeyPrv = hdWallet.getExtendedPrivateKey(Purpose.BIP49, CoinType.BITCOIN, HDVersion.YPRV)
        val publicKey44 = hdWallet.getExtendedPublicKey(Purpose.BIP44, CoinType.BITCOIN, HDVersion.XPUB)
        val publicKey49 = hdWallet.getExtendedPublicKey(Purpose.BIP49, CoinType.BITCOIN, HDVersion.YPUB)
        val publicKey84 = hdWallet.getExtendedPublicKey(Purpose.BIP84, CoinType.BITCOIN, HDVersion.ZPUB)
        Log.d(TAG, "publicKey44 BTC - $publicKey44")
        Log.d(TAG, "publicKey49 BTC - $publicKey49")
        Log.d(TAG, "publicKey84 BTC - $publicKey84")

        val xpubAddr2_44 = HDWallet.getPublicKeyFromExtended(publicKey44, Curve.SECP256K1, HDVersion.XPUB, HDVersion.NONE, 0, 2)
        val xpubAddr9_44 = HDWallet.getPublicKeyFromExtended(publicKey44, Curve.SECP256K1, HDVersion.XPUB, HDVersion.NONE, 0, 9)
        Log.d(TAG, "xpubAddr2: " + xpubAddr2_44.data().toHex() + " checked = " + (xpubAddr2_44.data().toHex().compareTo("232880aa42a5fe8248ae8484af7bf38cea5a5af4cca1433c15aa894709fa092bb") == 0))
        Log.d(TAG, "xpubAddr9: " + xpubAddr9_44.data().toHex() + " checked = " + (xpubAddr9_44.data().toHex().compareTo("26b716e1769fb8c7a56c0bac3ed4e9de1916ae077392f4ccf71c2c17edc5de932") == 0))
        val bitcoinAddress2_44 = BitcoinAddress(xpubAddr2_44, 0x00)
        val bitcoinAddress9_44 = BitcoinAddress(xpubAddr9_44, 0x00)
        Log.d(TAG, "bitcoinAddress2: " + bitcoinAddress2_44.description())
        Log.d(TAG, "bitcoinAddress9: " + bitcoinAddress9_44.description())
        val addressFromExtendedPrv2_44 = HDWallet.getAddressFromExtended(publicKey44, Curve.SECP256K1, bitcoin, 0, 2)
        val addressFromExtendedPrv9_44 = HDWallet.getAddressFromExtended(publicKey44, Curve.SECP256K1, bitcoin, 0, 9)


        val xpubAddr2_49 = HDWallet.getPublicKeyFromExtended(publicKey49, Curve.SECP256K1, HDVersion.YPUB, HDVersion.NONE, 0, 2)
        val xpubAddr9_49 = HDWallet.getPublicKeyFromExtended(publicKey49, Curve.SECP256K1, HDVersion.YPUB, HDVersion.NONE, 0, 9)
        Log.d(TAG, "xpubAddr2: " + xpubAddr2_49.data().toHex() + " checked = " + (xpubAddr2_49.data().toHex().compareTo("399bec5d79671dc018ef9ba4ad2736caf3e4670541b89f3285bf176906fa17ddf") == 0))
        Log.d(TAG, "xpubAddr9: " + xpubAddr9_49.data().toHex() + " checked = " + (xpubAddr9_49.data().toHex().compareTo("290463bf39056efb31d54f3b4db4cc23db1e08afd01721128fa3f0ad0fcfd908a") == 0))
        val bitcoinAddress2_49 = BitcoinAddress(xpubAddr2_49, 0x05)
        val bitcoinAddress9_49 = BitcoinAddress(xpubAddr9_49, 0x05)
        Log.d(TAG, "bitcoinAddress2: " + bitcoinAddress2_49.description())
        Log.d(TAG, "bitcoinAddress9: " + bitcoinAddress9_49.description())
        val addressFromExtendedPrv2_49 = HDWallet.getAddressFromExtended(publicKey49, Curve.SECP256K1, bitcoin, 0, 2)
        val addressFromExtendedPrv9_49 = HDWallet.getAddressFromExtended(publicKey49, Curve.SECP256K1, bitcoin, 0, 9)


        val xpubAddr2_84 = HDWallet.getPublicKeyFromExtended(publicKey84, Curve.SECP256K1, HDVersion.ZPUB, HDVersion.ZPRV, 0, 2)
        val xpubAddr9_84 = HDWallet.getPublicKeyFromExtended(publicKey84, Curve.SECP256K1, HDVersion.ZPUB, HDVersion.ZPRV, 0, 9)
        Log.d(TAG, "xpubAddr2: " + xpubAddr2_84.data().toHex() + " checked = " + (xpubAddr2_84.data().toHex().compareTo("31e1f64d2f6768dccb6814545b2e2d58e26ad5f91b7cbaffe881ed572c65060db") == 0))
        Log.d(TAG, "xpubAddr9: " + xpubAddr9_84.data().toHex() + " checked = " + (xpubAddr9_84.data().toHex().compareTo("3d2296f102d152a68c15efdb930e3702fcb0a6979c8aa13f2615fb8acc25d0671") == 0))
        val bitcoinAddress2_84 = Bech32Address(HRP.BITCOIN, xpubAddr2_84)
        val bitcoinAddress9_84 = Bech32Address(HRP.BITCOIN, xpubAddr9_84)
        Log.d(TAG, "bitcoinAddress2: " + bitcoinAddress2_84.description())
        Log.d(TAG, "bitcoinAddress9: " + bitcoinAddress9_84.description())
        val addressFromExtendedPrv2_84 = HDWallet.getAddressFromExtended(publicKey84, Curve.SECP256K1, bitcoin, 0, 2)
        val addressFromExtendedPrv9_84 = HDWallet.getAddressFromExtended(publicKey84, Curve.SECP256K1, bitcoin, 0, 9)


    }


    // This is just helper function to transform a ByteArray to a Hex string
    private fun ByteArray.toHex(): String {
        return BigInteger(1, this).toString(16)
    }
}
