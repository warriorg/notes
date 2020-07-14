
const Node = function (data, next) {
    this.data = data
    this.next = next
}

const SinglyLinkedList = function () {
    this.size = 0
    this.head = null
    this.tail = null
}

SinglyLinkedList.prototype.isEmpty = function() {
    return this.size === 0
}

SinglyLinkedList.prototype.add = function(item) {
    this.addLast(item)
}

SinglyLinkedList.prototype.addFirst = function(item) {
    const node = new Node(item, null)    
    if (this.isEmpty()) {
        this.head = this.tail = node;
    } else {
        node.next = this.head
        this.head = node
    }
    this.size++ 
}

SinglyLinkedList.prototype.addLast = function(item) {
    const node = new Node(item, null)    
    if (this.isEmpty()) {
        this.head = node;
    }

    if (this.tail) {
        this.tail.next = node
    }
    this.tail = node
    this.size += 1;
}

SinglyLinkedList.prototype.addAt = function(index, item) {

}

SinglyLinkedList.prototype.find = function(item) {
    let currNode = this.head
    while (currNode && currNode.data !== item) {
        currNode = currNode.next
    }
    return currNode
}

SinglyLinkedList.prototype.findPrev = function(item) {
    let currNode = this.head
    if (currNode && currNode.next && currNode.next.data !== item) {
        currNode = currNode.next
    }
    return currNode
}


SinglyLinkedList.prototype.remove = function (item) {
    let prev = this.findPrev(item)
    if (prev.next) {
        prev.next = prev.next.next
        this.size -= 1
    }
} 


// test 
const linkedList = new SinglyLinkedList()
linkedList.add('1')
linkedList.add('2')
linkedList.add('3')
linkedList.add('4')
linkedList.add('5')
console.log('linked list', linkedList)
console.log('linked list find', linkedList.find('3'))
console.log('linked list find prev node', linkedList.findPrev('3'))
linkedList.remove('2')
console.log('linked list', linkedList)
linkedList.addFirst('6')
console.log('linked list add first', linkedList)



