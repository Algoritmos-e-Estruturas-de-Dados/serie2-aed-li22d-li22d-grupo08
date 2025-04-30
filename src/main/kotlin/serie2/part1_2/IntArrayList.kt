package serie2.part1_2

class IntArrayList(k: Int): Iterable <Int> {
    val data = IntArray(k)
    var head = 0
    var tail = 0
    var size = 0
    var offset = 0

    fun append(x: Int): Boolean {
        if (size == data.size) return false
        data[tail] = x - offset
        tail = (tail + 1) % data.size
        size++
        return true
    }

    fun get(n: Int): Int?  {
        if (n < 0 || n >= size) return null
        val index = (head + n) % data.size
        return data[index] + offset
    }

    fun addToAll(x: Int)   {
        offset += x
    }

    fun remove(): Boolean {
        if (size == 0) return false
        head = (head + 1) % data.size
        size--
        return true
    }

    override fun iterator(): Iterator<Int> { // Opcional
        return object : Iterator<Int> {
            var count = 0
            var current = head

            override fun hasNext() = count < size

            override fun next(): Int {
                val value = data[current] + offset
                current = (current + 1) % data.size
                count++
                return value
            }
        }
    }
}

fun main() {
    val lista = IntArrayList(5)

    println("🔹 Append 10, 20, 30")
    lista.append(10)
    lista.append(20)
    lista.append(30)

    println("🔹 Get elementos:")
    println("get(0): ${lista.get(0)}")  // 10
    println("get(1): ${lista.get(1)}")  // 20
    println("get(2): ${lista.get(2)}")  // 30

    println("🔹 Soma 5 a todos")
    lista.addToAll(5)

    println("get(0): ${lista.get(0)}")  // 15
    println("get(1): ${lista.get(1)}")  // 25
    println("get(2): ${lista.get(2)}")  // 35

    println("🔹 Remove primeiro (FIFO)")
    lista.remove()

    println("get(0): ${lista.get(0)}")  // 25
    println("get(1): ${lista.get(1)}")  // 35

    println("🔹 Append 40 e 50")
    lista.append(40)
    lista.append(50)

    println("🔹 Iteração com for:")
    for (x in lista) {
        println(x)
    }

    println("🔹 Teste limite de capacidade:")
    println("Append 60 (esperado true): ${lista.append(60)}")
    println("Append 70 (esperado false): ${lista.append(70)}")
}