package serie2.part1_2
import kotlin.random.Random

fun minimum(maxHeap: Array<Int>, heapSize: Int): Int {
    val leaf = heapSize / 2
    var min = maxHeap[leaf]

    for(i in leaf until heapSize) {
        if(maxHeap[i] < min) {
            min = maxHeap[i]
        }
    }

    return min
}
