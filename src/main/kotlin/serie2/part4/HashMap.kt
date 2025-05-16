package serie2.part4

import serie2.part3.Node

class HashMap<K, V> (initialCapacity: Int = 16, val loadFactor: Float = 0.75f) : MutableMap<K, V> {
    private class HashNode<K, V>(
        override val key: K, override var value: V,
        var next: HashNode<K, V>? = null
    ) : MutableMap.MutableEntry<K, V> {
        var hc = key.hashCode()
        override fun setValue(newValue: V): V {
            val oldValue = value
            value = newValue
            return oldValue
        }
    }


    private var table: Array<HashNode<K, V>?> = arrayOfNulls(initialCapacity)

    override var capacity = initialCapacity // M dimensão da tabela
    override var size = 0    // N chaves

    private fun hash(key: K): Int {
        var idx = key.hashCode() % capacity
        if (idx < 0) idx += capacity
        return idx
    }
     override fun put(key: K,Value:V):V? {

         if (size* loadFactor >= capacity) expand()
         val idx = hash(key)
         var current = table[idx]

         while (current != null) {
             if (current.key == key) {
                 val oldValue = current.value
                 current.value = Value
                 return oldValue
             }
             current = current.next
         }

         val newNode = HashNode(key, Value, next = table[idx])
         table[idx] = newNode
         size++
         return null
    }

    override fun get(key: K): V?{
        val idx = hash(key)
        var curr = table[idx]
        while (curr != null) {
            if (key == curr.key) return curr.value
            curr = curr.next
        }
        return null
    }





    private fun expand() {
        capacity *= 2
        val newTable = arrayOfNulls<HashNode<K, V>?>(capacity)

        for (i in table.indices) {
            var curr = table[i]
            while (curr != null) {
                val next = curr.next

                val idx = hash(curr.key) % capacity
                curr.next = newTable[idx]
                newTable[idx] = curr

                curr = next
            }
        }

        table = newTable
    }
    private inner class MyIterator: Iterator<MutableMap.MutableEntry<K,V>> {
        var currIdx = -1;
        var currNode: HashNode<K,V>? = null
        var list: HashNode<K,V>? = null

        override fun hasNext(): Boolean {
            if (currNode != null) return true
            while (currIdx < capacity) {
                if (list == null) {
                    currIdx++
                    if (currIdx < capacity) list = table[currIdx]
                } else {
                    currNode = list
                    list?.let { l -> list = l.next }
                    return true
                }
            }
            return false;
        }

        override fun next(): MutableMap.MutableEntry<K,V> {
            if (!hasNext()) throw NoSuchElementException()
            val aux = currNode
            currNode = null
            return aux ?: Any() as MutableMap.MutableEntry<K,V>
        }
    }



    override fun iterator(): Iterator<MutableMap.MutableEntry<K,V>,> = MyIterator()




}