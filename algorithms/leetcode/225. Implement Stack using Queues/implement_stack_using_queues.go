package leetcode

type MyStack struct {
	q1 *ArrayQueue
	q2 *ArrayQueue
}

/** Initialize your data structure here. */
func Constructor() MyStack {
	return MyStack{
		q1: NewArrayQueue(),
		q2: NewArrayQueue(),
	}
}

/** Push element x onto stack. */
func (this *MyStack) Push(x int) {
	for !this.q1.Empty() {
		this.q2.Push(this.q1.Pop())
	}
	this.q1.Push(x)
	for !this.q2.Empty() {
		this.q1.Push(this.q2.Pop())
	}
}

/** Removes the element on top of the stack and returns that element. */
func (this *MyStack) Pop() int {
	if this.q1.Empty() {
		return -1
	}
	return this.q1.Pop().(int)
}

/** Get the top element. */
func (this *MyStack) Top() int {
	return this.q1.Peek().(int)
}

/** Returns whether the stack is empty. */
func (this *MyStack) Empty() bool {
	return this.q1.Empty()
}

type ArrayQueue struct {
	q    []interface{}
	size int
	head int
	tail int
}

/** Initialize your data structure here. */
func NewArrayQueue() *ArrayQueue {
	return &ArrayQueue{
		q:    make([]interface{}, 32),
		size: 0,
	}
}

/** Push element x to the back of queue. */
func (this *ArrayQueue) Push(x interface{}) {
	this.enqueue(x)
}

/** Removes the element from in front of queue and returns that element. */
func (this *ArrayQueue) Pop() interface{} {
	return this.dequeue()
}

/** Get the front element. */
func (this *ArrayQueue) Peek() interface{} {
	if this.Empty() {
		return nil
	}
	return this.q[this.head]
}

/** Returns whether the queue is empty. */
func (this *ArrayQueue) Empty() bool {
	return this.size == 0
}

func (this *ArrayQueue) enqueue(x interface{}) bool {
	if this.size == len(this.q) {
		return false
	}
	this.size++
	this.q[this.tail] = x
	this.tail++
	if this.tail == len(this.q) {
		this.tail = 0
	}

	return true
}

func (this *ArrayQueue) dequeue() interface{} {
	if this.Empty() {
		return nil
	}
	x := this.q[this.head]
	this.q[this.head] = nil
	this.size--
	this.head++
	if this.head == len(this.q) {
		this.head = 0
	}

	return x
}

/**
 * Your MyStack object will be instantiated and called as such:
 * obj := Constructor();
 * obj.Push(x);
 * param_2 := obj.Pop();
 * param_3 := obj.Top();
 * param_4 := obj.Empty();
 */
