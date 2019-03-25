package com.sirinlabs.walletcoreexample

import android.util.Log
import com.trustwallet.core.app.utils.toHex
import org.junit.Assert.*
import org.junit.Test
import wallet.core.jni.*

class MainActivityTest {
    init {
        System.loadLibrary("TrustWalletCore")
    }

    @Test
    fun testBIP44BitcoinAddressDerivation() {
        val words = "ripple scissors kick mammal hire column oak again sun offer wealth tomorrow wagon turn fatal"
        val password = ""

        // create a wallet with mnemonic
        val hdWallet = HDWallet(words, password)

        val bitcoin = CoinType.BITCOIN

        // get extended public keys for BIP44, BIP49 and BIP84
        val publicKey44 = hdWallet.getExtendedPublicKey(Purpose.BIP44, CoinType.BITCOIN, HDVersion.XPUB)
        assertEquals(
            "xpub6CGhFjZe528TNZe7QPEdbPyB6djTVAdiiZ6upeLphjG5pxwnpJ7KCx7jXvoTvaWK5fcD5xa9Cc1eeThAFnhiDcS4wcaWkgg4dSMTtVuDtPn",
            publicKey44
        )

        // get public keys for index 2 and 9

        val xpubAddr2_44 =
            HDWallet.getPublicKeyFromExtended(publicKey44, Curve.SECP256K1, HDVersion.XPUB, HDVersion.NONE, 0, 2)
        assertEquals("232880aa42a5fe8248ae8484af7bf38cea5a5af4cca1433c15aa894709fa092bb", xpubAddr2_44.data().toHex())

        val xpubAddr9_44 =
            HDWallet.getPublicKeyFromExtended(publicKey44, Curve.SECP256K1, HDVersion.XPUB, HDVersion.NONE, 0, 9)
        assertEquals("26b716e1769fb8c7a56c0bac3ed4e9de1916ae077392f4ccf71c2c17edc5de932", xpubAddr9_44.data().toHex())

        // Create BitcoinAddress for indexes 2 and 9
        val bitcoinAddress2_44 = BitcoinAddress(xpubAddr2_44, 0x00)
        assertEquals("1U1EJFeKJdxnFD4aQ1FcGTwifStLu2Ek1", bitcoinAddress2_44.description())

        val bitcoinAddress9_44 = BitcoinAddress(xpubAddr9_44, 0x00)
        assertEquals("16MhWKwFNbffohVgVfzZ1Lnw8RT5em9EkT", bitcoinAddress9_44.description())

        // Get address with extended public key for indexes 2 and 9

        //FIXME: Fail here
        val addressFromExtendedPrv2_44 = HDWallet.getAddressFromExtended(publicKey44, Curve.SECP256K1, bitcoin, 0, 2)
        assertEquals("1U1EJFeKJdxnFD4aQ1FcGTwifStLu2Ek1", addressFromExtendedPrv2_44)

        //FIXME: Fail here
        val addressFromExtendedPrv9_44 = HDWallet.getAddressFromExtended(publicKey44, Curve.SECP256K1, bitcoin, 0, 9)
        assertEquals("16MhWKwFNbffohVgVfzZ1Lnw8RT5em9EkT", addressFromExtendedPrv9_44)
    }

    @Test
    fun testBIP49BitcoinAddressDerivation() {
        val words = "ripple scissors kick mammal hire column oak again sun offer wealth tomorrow wagon turn fatal"
        val password = ""

        // create a wallet with mnemonic
        val hdWallet = HDWallet(words, password)

        val bitcoin = CoinType.BITCOIN

        // get extended public keys for BIP44, BIP49 and BIP84
        val publicKey49 = hdWallet.getExtendedPublicKey(Purpose.BIP49, CoinType.BITCOIN, HDVersion.YPUB)
        assertEquals(
            "ypub6WyMy5eSFvcW7igibKYod3rEr5K9dq22McfmUWBYnpwzMnzDE2SooUHhtSn2GU5QUpiqtdSPopepRQM1iVtCA2ZuUde4nHpZSjNSY1HoxP5",
            publicKey49
        )

        // get addresses for BIP49
        val xpubAddr2_49 =
            HDWallet.getPublicKeyFromExtended(publicKey49, Curve.SECP256K1, HDVersion.YPUB, HDVersion.NONE, 0, 2)
        assertEquals(
            "399bec5d79671dc018ef9ba4ad2736caf3e4670541b89f3285bf176906fa17ddf",
            xpubAddr2_49.data().toHex()
        )

        val xpubAddr9_49 =
            HDWallet.getPublicKeyFromExtended(publicKey49, Curve.SECP256K1, HDVersion.YPUB, HDVersion.NONE, 0, 9)
        assertEquals(
            "290463bf39056efb31d54f3b4db4cc23db1e08afd01721128fa3f0ad0fcfd908a",
            xpubAddr9_49.data().toHex()
        )

        //FIXME: fails here
        val bitcoinAddress2_49 = BitcoinAddress(xpubAddr2_49, 0x05)
        assertEquals(
            "363Bijf6729pXU8PV55jma4G8ugYH5un6Y",
            bitcoinAddress2_49.description()
        )

        //FIXME: fails here
        val bitcoinAddress9_49 = BitcoinAddress(xpubAddr9_49, 0x05)
        assertEquals(
            "3HFJHTWmwkGvhQw8dyyRm3MaiwePHmCsja",
            bitcoinAddress9_49.description()
        )

        //FIXME: fails here
        val addressFromExtendedPrv2_49 = HDWallet.getAddressFromExtended(publicKey49, Curve.SECP256K1, bitcoin, 0, 2)
        assertEquals(
            "363Bijf6729pXU8PV55jma4G8ugYH5un6Y",
            addressFromExtendedPrv2_49
        )

        //FIXME: fails here
        val addressFromExtendedPrv9_49 = HDWallet.getAddressFromExtended(publicKey49, Curve.SECP256K1, bitcoin, 0, 9)
        assertEquals(
            "3HFJHTWmwkGvhQw8dyyRm3MaiwePHmCsja",
            addressFromExtendedPrv9_49
        )

    }

    @Test
    fun testBIP84BitcoinAddressDerivation() {
        val words = "ripple scissors kick mammal hire column oak again sun offer wealth tomorrow wagon turn fatal"
        val password = ""

        // create a wallet with mnemonic
        val hdWallet = HDWallet(words, password)

        val bitcoin = CoinType.BITCOIN

        // get extended public keys for BIP44, BIP49 and BIP84
        val publicKey84 = hdWallet.getExtendedPublicKey(Purpose.BIP84, CoinType.BITCOIN, HDVersion.ZPUB)
        assertEquals(
            "zpub6rNUNtxSa9Gxvm4Bdxf1MPMwrvkzwDx6vP96Hkzw3jiQKdg3fhXBStxjn12YixQB8h88B3RMSRscRstf9AEVaYr3MAqVBEWBDuEJU4PGaT9",
            publicKey84
        )

        val xpubAddr2_84 = HDWallet.getPublicKeyFromExtended(publicKey84, Curve.SECP256K1, HDVersion.ZPUB, HDVersion.ZPRV, 0, 2)
        assertEquals(
            "31e1f64d2f6768dccb6814545b2e2d58e26ad5f91b7cbaffe881ed572c65060db",
            xpubAddr2_84.data().toHex()
        )
        val xpubAddr9_84 = HDWallet.getPublicKeyFromExtended(publicKey84, Curve.SECP256K1, HDVersion.ZPUB, HDVersion.ZPRV, 0, 9)
        assertEquals(
            "3d2296f102d152a68c15efdb930e3702fcb0a6979c8aa13f2615fb8acc25d0671",
            xpubAddr9_84.data().toHex()
        )
        val bitcoinAddress2_84 = Bech32Address(HRP.BITCOIN, xpubAddr2_84)
        assertEquals(
            "bc1q7zddsunzaftf4zlsg9exhzlkvc5374a6v32jf6",
            bitcoinAddress2_84.description()
        )
        val bitcoinAddress9_84 = Bech32Address(HRP.BITCOIN, xpubAddr9_84)
        assertEquals(
            "bc1que850pvrezlk4rd9zthdpkfylj3ashhr74xemg",
            bitcoinAddress9_84.description()
        )
        val addressFromExtendedPrv2_84 = HDWallet.getAddressFromExtended(publicKey84, Curve.SECP256K1, bitcoin, 0, 2)
        assertEquals(
            "bc1q7zddsunzaftf4zlsg9exhzlkvc5374a6v32jf6",
            addressFromExtendedPrv2_84
        )
        val addressFromExtendedPrv9_84 = HDWallet.getAddressFromExtended(publicKey84, Curve.SECP256K1, bitcoin, 0, 9)
        assertEquals(
            "bc1que850pvrezlk4rd9zthdpkfylj3ashhr74xemg",
            addressFromExtendedPrv9_84
        )

    }
}