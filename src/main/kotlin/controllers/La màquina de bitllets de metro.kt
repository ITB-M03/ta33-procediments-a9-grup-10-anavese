package org.example.controllers

import java.util.*

data class billetsZona(
    var billet : String,
    var zona : Int,
    var quantitat : Int
)


fun main() {

}

fun abrirScanner(): Scanner {
    var scan : Scanner = Scanner(System.`in`)
    return scan
}

fun tancarScanner(scan : Scanner){
    scan.close()
}

fun billete(scan: Scanner) : Int{
    println("Quin bitllet desitja adquirir?")
    println("1. Bitllet senzill")
    println("2. TCasual")
    println("3. TUsual")
    println("4. TFamiliar")
    println("5. TJove")

    var tipusBillet : Int = scan.nextInt()
    return tipusBillet
}

fun zona(scan: Scanner) : Int{
    println("Quina zona vol viatjar?")
    println("1")
    println("2")
    println("3")

    var numZona : Int = scan.nextInt()
    return numZona
}

fun precio(){
    println("Has escollit la opció: ")
    println("El preu del bitlett es: ")
    println("Vols seguir comprant? [S/N]")
}

fun pago(){

}

fun ticket(){

}