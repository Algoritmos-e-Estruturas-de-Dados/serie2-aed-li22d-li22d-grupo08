package serie2.part4
import java.util.*
import java.io.BufferedReader
import java.io.FileReader
import java.io.PrintWriter
//Implementação com Kotlin

fun createReader(fileName: String): BufferedReader {
    return BufferedReader(FileReader(fileName))
}

fun createWriter(fileName: String): PrintWriter {
    return PrintWriter(fileName)
}


data class Point(val x: Float, val y: Float)

fun lerFicheiros(file1: String, file2: String): HashMap<Point, MutableSet<String>> {
    val map = HashMap<Point, MutableSet<String>>()

    fun ler(fileName: String, origem: String) {
        val reader = createReader(fileName)
        reader.useLines { lines ->
            for (linha in lines) {
                val limpa = linha.trim()
                if (limpa.startsWith("v")) {
                    val partes = limpa.split(" ").filter { it.isNotEmpty() }
                    if (partes.size >= 4) {
                        val x = partes[2].toFloatOrNull()
                        val y = partes[3].toFloatOrNull()
                        if (x != null && y != null) {
                            val p = Point(x, y)
                            map.computeIfAbsent(p) { mutableSetOf() }.add(origem)
                        }
                    }
                }
            }
        }
    }

    ler(file1, "f1")
    ler(file2, "f2")
    return map
}



fun ficheiroFinal(fileName: String, pontos: Set<Point>) {
    val writer = createWriter(fileName)
    for (p in pontos) {
        writer.println("${p.x} ${p.y}")
    }
    writer.close()
}
fun union(map: Map<Point, Set<String>>): Set<Point> {
    return map.keys
}

fun intersection(map: Map<Point, Set<String>>): Set<Point> {
    return map.filter { it.value.containsAll(setOf("f1", "f2")) }.keys
}

fun difference(map: Map<Point, Set<String>>): Set<Point> {
    return map.filter { it.value.contains("f1") && !it.value.contains("f2") }.keys
}


fun main() {
    var pontosMap: HashMap<Point, MutableSet<String>>? = null

    while (true) {
        print("Digite um dos comandos: load, union, intersection, difference, exit")
        val input = readLine() ?: break
        val parts = input.trim().split(' ').filter { it.isNotEmpty() }

        when (parts[0].lowercase()) {
            "load" -> {
//                if (parts.size < 3) {
//                    println("Uso correto: load <file1> <file2>")
//                    continue
//                }
                val file1 = parts[1]
                val file2 = parts[2]

                pontosMap = lerFicheiros(file1, file2)
                println("Ficheiros carregados com sucesso.")
            }

            "union" -> {
                if (pontosMap == null) {
                    println("Carregue os ficheiros com 'load'")
                    continue
                }
                if (parts.size < 2) {
                    println("Union <outputfile>")
                    continue
                }
                val outputFile = parts[1]
                val resultado = union(pontosMap)
                ficheiroFinal(outputFile, resultado)
                println("Ficheiro $outputFile gerado com os pontos da união.")
            }

            "intersection" -> {
                if (pontosMap == null) {
                    println("Carregue os ficheiros com 'load'")
                    continue
                }
                if (parts.size < 2) {
                    println("Intersection <outputfile>")
                    continue
                }
                val outputFile = parts[1]
                val resultado = intersection(pontosMap)
                ficheiroFinal(outputFile, resultado)
                println("Ficheiro $outputFile gerado com os pontos da interseção.")
            }

            "difference" -> {
                if (pontosMap == null) {
                    println("Carregue os ficheiros com 'load'")
                    continue
                }
                if (parts.size < 2) {
                    println("Difference <outputfile>")
                    continue
                }
                val outputFile = parts[1]
                val resultado = difference(pontosMap)
                ficheiroFinal(outputFile, resultado)
                println("Ficheiro $outputFile gerado com os pontos da diferença.")
            }

            "exit" -> {
                println("Aplicação terminada.")
                break
            }

            else -> {
                println("Iválido!! Comandos válidos: load, union, intersection, difference, exit")
            }
        }
    }
}








