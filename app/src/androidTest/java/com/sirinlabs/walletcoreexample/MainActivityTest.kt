package com.sirinlabs.walletcoreexample

import com.trustwallet.core.app.utils.toHex
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import wallet.core.jni.*

class MainActivityTest {
    private lateinit var hdWallet: HDWallet

    init {
        System.loadLibrary("TrustWalletCore")
    }

    @Before
    fun setUp() {
        val words = "ripple scissors kick mammal hire column oak again sun offer wealth tomorrow wagon turn fatal"
        val password = ""

        // create a wallet with mnemonic
        hdWallet = HDWallet(words, password)
    }

    @Test
    fun testBIP44BitcoinAddressDerivation() {

        val bitcoin = CoinType.BITCOIN

        // get extended public keys for BIP44, BIP49 and BIP84
        val publicKey44 = hdWallet.getExtendedPublicKey(Purpose.BIP44, CoinType.BITCOIN, HDVersion.XPUB)
        assertEquals(
            "xpub6CGhFjZe528TNZe7QPEdbPyB6djTVAdiiZ6upeLphjG5pxwnpJ7KCx7jXvoTvaWK5fcD5xa9Cc1eeThAFnhiDcS4wcaWkgg4dSMTtVuDtPn",
            publicKey44
        )

        // get public keys for index 2 and 9

        val xpubAddr2_44 =
            HDWallet.getPublicKeyFromExtended(publicKey44, CoinType.BITCOIN.curve(), HDVersion.XPUB, HDVersion.XPRV, 0, 2)
        assertEquals("232880aa42a5fe8248ae8484af7bf38cea5a5af4cca1433c15aa894709fa092bb", xpubAddr2_44.data().toHex())

        val xpubAddr9_44 =
            HDWallet.getPublicKeyFromExtended(publicKey44, CoinType.BITCOIN.curve(), HDVersion.XPUB, HDVersion.XPRV, 0, 9)
        assertEquals("26b716e1769fb8c7a56c0bac3ed4e9de1916ae077392f4ccf71c2c17edc5de932", xpubAddr9_44.data().toHex())

        // Create BitcoinAddress for indexes 2 and 9
        val bitcoinAddress2_44 = BitcoinAddress(xpubAddr2_44, P2PKHPrefix.BITCOIN.value())
        assertEquals("1U1EJFeKJdxnFD4aQ1FcGTwifStLu2Ek1", bitcoinAddress2_44.description())

        val bitcoinAddress9_44 = BitcoinAddress(xpubAddr9_44, P2PKHPrefix.BITCOIN.value())
        assertEquals("16MhWKwFNbffohVgVfzZ1Lnw8RT5em9EkT", bitcoinAddress9_44.description())

        // Get address with extended public key for indexes 2 and 9

        //FIXME: Fail here
        val addressFromExtendedPrv2_44 = HDWallet.getAddressFromExtended(publicKey44, CoinType.BITCOIN.curve(), bitcoin, 0, 2)
        assertEquals("1U1EJFeKJdxnFD4aQ1FcGTwifStLu2Ek1", addressFromExtendedPrv2_44)

        //FIXME: Fail here
        val addressFromExtendedPrv9_44 = HDWallet.getAddressFromExtended(publicKey44, CoinType.BITCOIN.curve(), bitcoin, 0, 9)
        assertEquals("16MhWKwFNbffohVgVfzZ1Lnw8RT5em9EkT", addressFromExtendedPrv9_44)
    }

    @Test
    fun testBIP49BitcoinAddressDerivation() {
        val bitcoin = CoinType.BITCOIN

        // get extended public keys for BIP44, BIP49 and BIP84
        val publicKey49 = hdWallet.getExtendedPublicKey(Purpose.BIP49, CoinType.BITCOIN, HDVersion.YPUB)
        assertEquals(
            "ypub6WyMy5eSFvcW7igibKYod3rEr5K9dq22McfmUWBYnpwzMnzDE2SooUHhtSn2GU5QUpiqtdSPopepRQM1iVtCA2ZuUde4nHpZSjNSY1HoxP5",
            publicKey49
        )

        // get addresses for BIP49
        val xpubAddr2_49 =
            HDWallet.getPublicKeyFromExtended(publicKey49, CoinType.BITCOIN.curve(), HDVersion.YPUB, HDVersion.YPRV, 0, 2)
        assertEquals(
            "399bec5d79671dc018ef9ba4ad2736caf3e4670541b89f3285bf176906fa17ddf",
            xpubAddr2_49.data().toHex()
        )

        val xpubAddr9_49 =
            HDWallet.getPublicKeyFromExtended(publicKey49, CoinType.BITCOIN.curve(), HDVersion.YPUB, HDVersion.YPRV, 0, 9)
        assertEquals(
            "290463bf39056efb31d54f3b4db4cc23db1e08afd01721128fa3f0ad0fcfd908a",
            xpubAddr9_49.data().toHex()
        )

        //FIXME: fails here
        val bitcoinAddress2_49 = BitcoinAddress(xpubAddr2_49, P2SHPrefix.BITCOIN.value())
        assertEquals(
            "363Bijf6729pXU8PV55jma4G8ugYH5un6Y",
            bitcoinAddress2_49.description()
        )

        //FIXME: fails here
        val bitcoinAddress9_49 = BitcoinAddress(xpubAddr9_49, P2SHPrefix.BITCOIN.value())
        assertEquals(
            "3HFJHTWmwkGvhQw8dyyRm3MaiwePHmCsja",
            bitcoinAddress9_49.description()
        )

        //FIXME: fails here
        val addressFromExtendedPrv2_49 = HDWallet.getAddressFromExtended(publicKey49, CoinType.BITCOIN.curve(), bitcoin, 0, 2)
        assertEquals(
            "363Bijf6729pXU8PV55jma4G8ugYH5un6Y",
            addressFromExtendedPrv2_49
        )

        //FIXME: fails here
        val addressFromExtendedPrv9_49 = HDWallet.getAddressFromExtended(publicKey49, CoinType.BITCOIN.curve(), bitcoin, 0, 9)
        assertEquals(
            "3HFJHTWmwkGvhQw8dyyRm3MaiwePHmCsja",
            addressFromExtendedPrv9_49
        )

    }

    @Test
    fun testBIP84BitcoinAddressDerivation() {
        val bitcoin = CoinType.BITCOIN

        // get extended public keys for BIP44, BIP49 and BIP84
        val publicKey84 = hdWallet.getExtendedPublicKey(Purpose.BIP84, CoinType.BITCOIN, HDVersion.ZPUB)
        assertEquals(
            "zpub6rNUNtxSa9Gxvm4Bdxf1MPMwrvkzwDx6vP96Hkzw3jiQKdg3fhXBStxjn12YixQB8h88B3RMSRscRstf9AEVaYr3MAqVBEWBDuEJU4PGaT9",
            publicKey84
        )

        val xpubAddr2_84 = HDWallet.getPublicKeyFromExtended(publicKey84, CoinType.BITCOIN.curve(), HDVersion.ZPUB, HDVersion.ZPRV, 0, 2)
        assertEquals(
            "31e1f64d2f6768dccb6814545b2e2d58e26ad5f91b7cbaffe881ed572c65060db",
            xpubAddr2_84.data().toHex()
        )
        val xpubAddr9_84 = HDWallet.getPublicKeyFromExtended(publicKey84, CoinType.BITCOIN.curve(), HDVersion.ZPUB, HDVersion.ZPRV, 0, 9)
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
        val addressFromExtendedPrv2_84 = HDWallet.getAddressFromExtended(publicKey84, CoinType.BITCOIN.curve(), bitcoin, 0, 2)
        assertEquals(
            "bc1q7zddsunzaftf4zlsg9exhzlkvc5374a6v32jf6",
            addressFromExtendedPrv2_84
        )
        val addressFromExtendedPrv9_84 = HDWallet.getAddressFromExtended(publicKey84, CoinType.BITCOIN.curve(), bitcoin, 0, 9)
        assertEquals(
            "bc1que850pvrezlk4rd9zthdpkfylj3ashhr74xemg",
            addressFromExtendedPrv9_84
        )

    }

    @Test
    fun testEtherAddressDerivation() {
        val expectedAddress = "0xA3Dcd899C0f3832DFDFed9479a9d828c6A4EB2A7"
        val ether = CoinType.ETHEREUM

        // get extended public keys for BIP44, BIP49 and BIP84
        val publicKey44 = hdWallet.getExtendedPublicKey(Purpose.BIP44, ether, HDVersion.XPUB)
        assertEquals(
            "xpub6BsRBaXawwf5vGydzpBSDXrQJ5rZKrZQGPTWkXRTWXz1KZ11D8TixEwX3uBWHwZE1DzQJuyLT9hgZcp4bFKfYNCx5cYCYBQqP5jbxcVQumc",
            publicKey44
        )

        val privateKey = hdWallet.getKeyForCoin(ether)
        assertEquals(
            "ab4accc9310d90a61fc354d8f353bca4a2b3c0590685d3eb82d0216af3badddc",
            privateKey.data().toHex()
        )

        val address = ether.deriveAddress(privateKey)
        assertEquals(
            expectedAddress,
            address
        )

        val xpubAddr0_44 =
            HDWallet.getPublicKeyFromExtended(publicKey44, ether.curve(), HDVersion.XPUB, HDVersion.XPRV, 0, 0)
        assertEquals(
            "348a9ffac8022f1c7eb5253746e24d11d9b6b2737c0aecd48335feabb95a17991",
            xpubAddr0_44.data().toHex()
        )

        //FIXME: fails - wrong address
        val etherAddress0_44 = EthereumAddress(xpubAddr0_44)
        assertEquals(
            expectedAddress,
            etherAddress0_44.description()
        )

        //FIXME: fails - crash
        val addressFromExtended = HDWallet.getAddressFromExtended(publicKey44, ether.curve(), ether, 0, 0)
        assertEquals(
            expectedAddress,
            addressFromExtended
        )
    }
}