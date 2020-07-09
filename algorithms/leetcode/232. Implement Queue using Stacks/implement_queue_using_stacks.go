package leetcode

type MyQueue struct {
	stack *MyStack
}

/** Initialize your data structure here. */
func Constructor() MyQueue {
	return MyQueue{
		stack: NewStack(),
	}
}

/** Push element x to the back of queue. */
func (this *MyQueue) Push(x int) {
	this.stack.Push(x)
}

/** Removes the element from in front of queue and returns that element. */
func (this *MyQueue) Pop() int {
	return this.stack.Pop()
}

/** Get the front element. */
func (this *MyQueue) Peek() int {
	return this.stack.Top()
}

/** Returns whether the queue is empty. */
func (this *MyQueue) Empty() bool {
	return this.stack.Empty()
}

type MyStack struct {
	stack []int
}

/** Initialize your data structure here. */
func NewStack() *MyStack {
	return &MyStack{
		stack: []int{},
	}
}

/** Push element x to the back of queue. */
func (this *MyStack) Push(x int) {
	this.stack = append(this.stack, x)
}

/** Removes the element on top of the stack and returns that element. */
func (this *MyStack) Pop() int {
	if len(this.stack) > 0 {
		n := len(this.stack) - 1 // Top element
		val := this.stack[n]

		this.stack = this.stack[:n]
		return val
	}
	return -1
}

/** Get the top element. */
func (this *MyStack) Top() int {
	return this.stack[len(this.stack)-1]
}

/** Returns whether the stack is empty. */
func (this *MyStack) Empty() bool {
	return len(this.stack) == 0
}
