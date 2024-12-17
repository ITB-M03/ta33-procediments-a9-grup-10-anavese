package controllers
import java.text.DecimalFormat
import java.util.*
import kotlin.system.exitProcess

/**
 * Enumeració que representa els diferents tipus de bitllets i els seus preus
 * @author Anabel
 */
enum class Bitllet(val nom : String, val preu : Double) {
    BITLLETSENZILL( "Bitllet Senzill", 2.40),
    TCASUAL( "TCasual", 11.35),
    TUSUAL( "TUsual", 40.00),
    TFAMILIAR( "TFamiliar", 10.00),
    TJOVE( "TJove", 80.00)
}

/**
 * Data class que manté l'estat de l'etapa actual del procés
 * @author Anabel
 */
data class Etapa(
    public var numEtapa : Int = 0
)

/**
 * Funció principal que inicia l'execució del programa
 * @author Anabel
 */
fun main() {
    val scan : Scanner = abrirScanner()
    admin(scan)
    tancarScanner(scan)

}

/**
 * Funció que gestiona el procés complet de compra de bitllets
 * @param scan Scanner per a la lectura de dades d'entrada de l'usuari
 * @author Anabel
 */
fun admin (scan : Scanner) {
    var preuFinal = 0.0
    var preu = Bitllet.TUSUAL
    var multiplicadorZona = 1
    var contador : Int = 0
    var seguirComprant : Boolean = true
    val etapa: Etapa = Etapa(1)
    while (contador < 3 && seguirComprant) {
        etapa.numEtapa = 1
        contador++

        while (etapa.numEtapa <= 6) {
            when (etapa.numEtapa) {
                1 -> preu = gestionarBitllets(scan, etapa)
                2 -> multiplicadorZona = zona(scan, etapa)
                3 -> preuFinal = precio(scan, multiplicadorZona, preu, etapa)
                4 -> {seguirComprant = seguir(scan, etapa)
                    if (!seguirComprant){
                        etapa.numEtapa = 1
                    }
                }
                5 -> pagament(scan, preuFinal, etapa)
                6 -> ticket(scan, preu, preuFinal, multiplicadorZona, contador, etapa)
            }
        }
    }
}

/**
 * Funció per mostrar el menú inicial de bitllets disponibles
 * @author Anabel
 */
fun bitllet(){
    println("Quin bitllet desitja adquirir?")
    println("1. Bitllet senzill")
    println("2. TCasual")
    println("3. TUsual")
    println("4. TFamiliar")
    println("5. TJove")
}

/**
 * Funció per gestionar l'opció escollida de bitllet
 * @param scan Escaneja l'opció escollida
 * @param etapa Controlador d'etapa
 * @return Bitllet seleccionat
 * @author Anabel
 */
fun gestionarBitllets(scan : Scanner, etapa: Etapa) : Bitllet {
    var sortir = false
    var tipusBitllet : Bitllet = Bitllet.BITLLETSENZILL
    while (!sortir) {
        bitllet() // Mostrem el menú
        when (scan.nextInt()) {
            1 -> {tipusBitllet = Bitllet.BITLLETSENZILL
                sortir = true}
            2 -> {tipusBitllet = Bitllet.TCASUAL
                sortir = true}
            3 -> {tipusBitllet = Bitllet.TUSUAL
                sortir = true}
            4 -> {tipusBitllet = Bitllet.TFAMILIAR
                sortir = true}
            5 -> {tipusBitllet = Bitllet.TJOVE
                sortir = true}
            4321 -> tancarProgramaAdmin()
            else -> println("Opció no vàlida!")
        }
        scan.nextLine()
    }
    etapa.numEtapa++
    return tipusBitllet
}

/**
 * Funció per tancar el programa quan s'introdueix el codi d'administrador
 * @author Anabel
 */
fun tancarProgramaAdmin() {
    println("Programa tancat.")
    //Sé que no és elegant, però és l'única forma que he trobat de fer-ho
    exitProcess(0)
}

/**
 * Funció per gestionar la selecció de zona de viatge
 * @param scan Scanner per a la lectura de dades d'entrada de l'usuari
 * @param etapa Controlador d'etapa
 * @return Número de la zona seleccionada
 * @author Anabel
 */
fun zona(scan: Scanner, etapa: Etapa) : Int{
    println("Quina zona vol viatjar?")
    println("1. Zona 1")
    println("2. Zona 2")
    println("3. Zona 3")
    println("4. Tornar")
    val numZona = scan.nextInt()
    scan.nextLine()
    if (numZona == 4) {
        etapa.numEtapa -= 2
    }
    etapa.numEtapa++
    return numZona
}

/**
 * Funció per calcular el preu final del bitllet seleccionat segons la zona
 * @param scan Scanner per a la lectura de dades d'entrada de l'usuari
 * @param zona Número de la zona seleccionada
 * @param gestionarBitllets Tipus de bitllet seleccionat
 * @param etapa Controlador d'etapa
 * @return Preu final del bitllet
 * @author Anabel
 */
fun precio(scan: Scanner, zona : Int, gestionarBitllets : Bitllet, etapa: Etapa) : Double{
    println("Has escollit la opció: ${gestionarBitllets.nom}, $zona ")
    var multiplicador : Double = 1.0
    if (zona == 2) {
        multiplicador = 1.3125
    }
    else if (zona == 3) {
        multiplicador = 1.8443
    }
    val df = DecimalFormat("0.00")
    val preuFinal = gestionarBitllets.preu * multiplicador
    println("El preu del bitlett es: ${df.format(preuFinal)}")

    println("Vols tornar enrere? [S/N]")
    val continuar : String = scan.nextLine().uppercase()
    if (continuar == "S") {
        etapa.numEtapa-=2
    }
    etapa.numEtapa++
    return preuFinal
}

/**
 * Funció que demana a l'usuari si vol seguir comprant més bitllets
 * @param scan Scanner per a la lectura de dades d'entrada de l'usuari
 * @param etapa Controlador d'etapa
 * @return true si l'usuari vol seguir comprant, false en cas contrari
 * @author Anabel
 */
fun seguir(scan : Scanner, etapa: Etapa) :Boolean {
    println("Vols comprar més bitllets? [S/N]")
    var sortir : Boolean = false
    var resposta: String
    var error : Boolean = true
    do {
        resposta = scan.nextLine().uppercase()
        if (resposta == "S" || resposta == "N") {
            if (resposta == "N") {
                sortir = true
            }
            error = false
        }
        else println("Opció no vàlida!")
    }while (error && !sortir)
    etapa.numEtapa++
    return sortir
}

/**
 * Funció per gestionar el procés de pagament del bitllet
 * @param scan Scanner per a la lectura de dades d'entrada de l'usuari
 * @param preuFinal Preu final del bitllet a pagar
 * @param etapa Controlador d'etapa
 * @return true si el pagament es completa correctament
 * @author Anabel
 */
fun pagament(scan : Scanner, preuFinal : Double, etapa: Etapa) : Boolean {
    val df = DecimalFormat("0.00")
    println("El preu del bitllet és: ${df.format(preuFinal)} €")
    println("Introdueix l'import en efectiu (accepta monedes i bitllets d'EURO):")
    val importsValids = listOf(0.05, 0.10, 0.20, 0.50, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0)
    var importTotal = 0.00
    while (importTotal < preuFinal) {
        val dinersIntroduits = scan.nextDouble()
        scan.nextLine()
        if (dinersIntroduits in importsValids) {
            importTotal += dinersIntroduits
            if (preuFinal - importTotal >= 0.01) {
                println("Encara queden ${df.format(preuFinal - importTotal)} € per pagar.")
            }
            else{
                println("Canvi retornat: ${df.format(importTotal- preuFinal)} €")
            }
        } else {
            println("Import no vàlid! Torna a introduir.")
        }
    }
    etapa.numEtapa++
    return true
}

/**
 * Funció per imprimir i mostrar el tiquet de compra
 * @param scan Scanner per a la lectura de dades d'entrada de l'usuari
 * @param bitllet Tipus de bitllet seleccionat
 * @param preuFinal Preu final del bitllet
 * @param zona Número de la zona seleccionada
 * @param comptador Nombre de bitllets comprats
 * @param etapa Controlador d'etapa
 * @author Anabel
 */
fun ticket(scan: Scanner, bitllet : Bitllet, preuFinal : Double, zona : Int, comptador : Int, etapa: Etapa) {
    println("Vols tiquet de compra? [S/N]")
    val resposta : String = scan.nextLine().uppercase()
    if (resposta== "S") {
        println("----- TIQUET DE COMPRA -----")
        println("Tipus de bitllet: ${bitllet.nom} ")
        println("Nombre de zones: $zona")
        println("Nombre de bitllets: $comptador")
        println("Preu total: $preuFinal €")
        println("----------------------------")
        println("Recull el teu tiquet.")
        println("Adeu.")
        println()
    }
    else if (resposta != "N") {
        println("Opció no vàlida!")
    }
    etapa.numEtapa = 1
}

/**
 * Funció per obrir l'escàner
 * @return Scanner obert
 * @author Anabel
 */
fun abrirScanner(): Scanner {
    val scan : Scanner = Scanner(System.`in`)
    return scan
}

/**
 * Funció per tancar l'escàner
 * @param scan Tanquem l'escàner
 * @author Anabel
 */
fun tancarScanner(scan : Scanner){
    scan.close()
}