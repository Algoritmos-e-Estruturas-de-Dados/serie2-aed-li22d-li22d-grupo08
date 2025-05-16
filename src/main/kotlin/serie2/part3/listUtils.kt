package serie2.part3

class Node<T> (
    var value: T = Any() as T,
    var next: Node<T>? = null,
    var previous: Node<T>? = null) {
}

fun splitEvensAndOdds(list:Node<Int>){

    var curr = list.next
    while (curr != list) {
        val next = curr!!.next
        if(curr.value % 2 ==0){

            curr.previous!!.next = curr.next
            curr.next!!.previous = curr.previous

            curr.next= list.next
            curr.previous= list
            list.next!!.previous = curr
            list.next=curr

        }
        curr = next
    }


}

fun <T> intersection(list1: Node<T>, list2: Node<T>, cmp: Comparator<T>): Node<T>? {

    var head: Node<T>? = null
    var tail: Node<T>? = null

    var curr1=list1.next
    var curr2=list2.next
    while(curr1!= list1 && curr2!=list2){
         val compare=cmp.compare(curr1!!.value, curr2!!.value)
           if(compare<0)
               curr1=curr1.next
           else if(compare>0)
               curr2=curr2.next
           else {
               val next1 = curr1.next
               val next2 = curr2.next

               curr1.previous!!.next = curr1.next
               curr1.next!!.previous = curr1.previous

               curr2.previous!!.next = curr2.next
               curr2.next!!.previous = curr2.previous


               //Adiciona a nova lista
               curr1.next=null
               curr1.previous=tail
               if(tail==null) {
                   head = curr1
               } else{
                   tail.next=curr1
               }
               tail=curr1
               curr1=next1
               curr2=next2

               if (curr1 != list1 && cmp.compare(curr1!!.value, tail!!.value) == 0) {
                   curr1 = curr1.next
               }
               if (curr2 != list2 && cmp.compare(curr2!!.value, tail!!.value) == 0) {
                   curr2 = curr2.next
               }

           }

    }

 return head
}