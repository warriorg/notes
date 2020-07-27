package leetcode

type MyQueue struct {
	s1 *ArrayStack
	s2 *ArrayStack
}

/** Initialize your data structure here. */
func Constructor() MyQueue {
	return MyQueue{
		s1: NewStack(),
		s2: NewStack(),
	}
}

/** Push element x to the back of queue. */
func (this *MyQueue) Push(x int) {
	for !this.s1.Empty() {
		this.s2.Push(this.s1.Pop())
	}
	this.s1.Push(x)
	for !this.s2.Empty() {
		this.s1.Push(this.s2.Pop())
	}
}

/** Removes the element from in front of queue and returns that element. */
func (this *MyQueue) Pop() int {
	return this.s1.Pop()
}

/** Get the front element. */
func (this *MyQueue) Peek() int {
	return this.s1.Peek()
}

/** Returns whether the queue is empty. */
func (this *MyQueue) Empty() bool {
	return this.s1.Empty()
}

type ArrayStack struct {
	data []int // 存放数据的位置
	top  int   // 栈顶指针
}

/** Initialize your data structure here. */
func NewStack() *ArrayStack {
	return &ArrayStack{
		data: make([]int, 0, 32),
		top:  -1,
	}
}

/** Size  */
func (this *ArrayStack) Size() int {
	return this.top + 1
}

/** Push element x to the back of queue. */
func (this *ArrayStack) Push(x int) {
	this.top += 1
	if this.top > len(this.data)-1 {
		this.data = append(this.data, x)
	} else {
		this.data[this.top] = x
	}
}

/** Removes the element from in front of queue and returns that element. */
func (this *ArrayStack) Pop() int {
	if this.Empty() {
		return -1
	}
	x := this.data[this.top]
	this.top -= 1
	return x
}

/** Get the front element. */
func (this *ArrayStack) Peek() int {
	if this.Empty() {
		return -1
	}
	return this.data[this.top]
}

/** Returns whether the queue is empty. */
func (this *ArrayStack) Empty() bool {
	return this.top == -1
}
