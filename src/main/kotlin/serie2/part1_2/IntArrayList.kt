package serie2.part1_2

class IntArrayList( ) : Iterable <Int> {
    private val k=0
    private val data= IntArray(k)
    private var size = 0
    private var rear=0


    fun append(x: Int): Boolean {
        if (size >= k) return false
        data[rear] = x
        rear = (rear + 1) % k
        size++
        return true
    }


    fun get(n:Int):Int?  {



    }

    fun addToAll(x:Int)   {
        TODO("Not yet implemented")
    }

    fun remove():Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<Int> { // Opcional
        TODO("Not yet implemented")
    }
}