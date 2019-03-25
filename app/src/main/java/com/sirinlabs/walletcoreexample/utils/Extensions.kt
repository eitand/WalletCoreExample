package com.trustwallet.core.app.utils

import java.math.BigInteger

//fun ByteArray.toHex(): String {
//    return Numeric.toHexString(this)
//}

// This is just helper function to transform a ByteArray to a Hex string
fun ByteArray.toHex(): String {
    return BigInteger(1, this).toString(16)
}

fun String.toHexBytes(): ByteArray {
    return Numeric.hexStringToByteArray(this)
}

fun String.toHexByteArray(): ByteArray {
    return  Numeric.hexStringToByteArray(this)
}