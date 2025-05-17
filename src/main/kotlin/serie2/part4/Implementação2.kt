package serie2.part4


fun readFiles(file1: String, file2: String): AEDHashMap<Point, MutableSet<String>> {
    val map = AEDHashMap<Point, MutableSet<String>>()

    fun ler(fileName: String, origem: String) {
        val reader = createReader(fileName)
        reader.useLines { lines ->
            for (l in lines) {
                val limpa = l.trim()
                if (limpa.startsWith("v")) {
                    val partes = limpa.split(" ").filter { it.isNotEmpty() }
                    if (partes.size >= 4) {
                        val x = partes[2].toFloatOrNull()
                        val y = partes[3].toFloatOrNull()
                        if (x != null && y != null) {
                            val p = Point(x, y)
                            val conjunto = map.get(p) ?: mutableSetOf<String>().also { map.put(p, it) }
                            conjunto.add(origem)
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


fun union(map: AEDHashMap<Point, MutableSet<String>>): Set<Point> {
    val resultado = mutableSetOf<Point>()
    for (entry in map) {
        resultado.add(entry.key)
    }
    return resultado
}

fun intersection(map: AEDHashMap<Point, MutableSet<String>>): Set<Point> {
    val resultado = mutableSetOf<Point>()
    for (entry in map) {
        if (entry.value.containsAll(setOf("f1", "f2"))) {
            resultado.add(entry.key)
        }
    }
    return resultado
}

fun difference(map: AEDHashMap<Point, MutableSet<String>>): Set<Point> {
    val resultado = mutableSetOf<Point>()
    for (entry in map) {
        val origem = entry.value
        if ("f1" in origem && "f2" !in origem) {
            resultado.add(entry.key)
        }
    }
    return resultado
}

fun main() {
    var pontosMap: AEDHashMap<Point, MutableSet<String>>? = null

    while (true) {
        print("Digite um dos comandos: load, union, intersection, difference, exit\n> ")
        val input = readlnOrNull() ?: break
        val parts = input.trim().split(' ').filter { it.isNotEmpty() }

        when (parts[0].lowercase()) {
            "load" -> {
                if (parts.size < 3) {
                    println("Uso correto: load <ficheiro1> <ficheiro2>")
                    continue
                }
                val file1 = parts[1]
                val file2 = parts[2]

                try {
                    pontosMap = readFiles(file1, file2)
                    println("Ficheiros carregados com sucesso.")
                } catch (e: Exception) {
                    println("Erro ao carregar ficheiros: ${e.message}")
                }
            }

            "union" -> {
                if (pontosMap == null) {
                    println("Carregue os ficheiros com 'load'")
                    continue
                }
                if (parts.size < 2) {
                    println("Uso correto: union <outputfile>")
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
                    println("Uso correto: intersection <outputfile>")
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
                    println("Uso correto: difference <outputfile>")
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
                println("Comando inválido! Comandos válidos: load, union, intersection, difference, exit")
            }
        }
    }
}


