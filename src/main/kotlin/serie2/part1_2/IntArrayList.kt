package serie2.part1_2

class IntArrayList(dim: Int) {
    val arr = IntArray(dim)
    var head = 0
    var tail = 0
    var size = 0
    var offset=0

    fun append(x: Int): Boolean {
        if (size == arr.size) return false
        arr[tail] = x
        arr[tail] = x - offset
        tail = (tail + 1) % arr.size
        size++
        return true
    }


    fun get(n: Int): Int? {
        return if (n < 0 || n >= arr.size) null else arr[(head + n)% arr.size] + offset
    }



    fun addToAll(x: Int)   {
        offset +=x
    }

    fun remove(): Boolean {
        if (size == 0) return false
        head=(head+1)% arr.size
        size=size-1
        return true
    }

}

