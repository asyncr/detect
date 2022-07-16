package com.example.detect

class Converter {
    companion object {
        private var HEXADECIMAL = "0123456789ABCDEF"
        fun binaryToAll(base: Int, num: String): String {
            var numero = num.toInt(2)
            if (base != 10) {
                var hexa = ""
                while (numero > 0) {
                    hexa = HEXADECIMAL[numero % base] + hexa
                    numero /= base
                }
                return hexa
            }
            //hexa = if (numero !=0 ) hexa else "0" <- possible feature
            return numero.toString()
        }

        fun octalToAll(base: Int, num: String): String {
            var numero = num.toInt(8)
            var binario = ""
            while (numero > 0) {
                binario = (numero % base).toString() + binario
                numero /= base
            }
            //binario = if (numero !=0 ) binario else "0" <- possible feature
            return binario
        }

        fun hexaToAll(base: Int, num: String): String {
            var numero = num.toInt(16)
            var decimal = ""
            while (numero > 0) {
                decimal = (numero % base).toString() + decimal
                numero /= base
            }
            //decimal = if (numero !=0 ) decimal else "0" <- possible feature
            return decimal
        }

        fun decimaltoAll(base: Int, decimal: Int): String {
            var value = ""
            var decimal = decimal
            while (decimal > 0) {
                val residuo: Int = decimal % base
                value = HEXADECIMAL[residuo] + value // Add to left
                decimal /= base
            }
            //value = if(decimal!=0) value else "0" <- possible feature
            return value
        }
    }
}