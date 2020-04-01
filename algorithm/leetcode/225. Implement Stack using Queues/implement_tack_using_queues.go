package leetcode

type MyQueue struct {
    
}


/** Initialize your data structure here. */
func Constructor() MyQueue {
    
}


/** Push element x to the back of queue. */
func (this *MyQueue) Push(x int)  {
    
}


/** Removes the element from in front of queue and returns that element. */
func (this *MyQueue) Pop() int {
    
}


/** Get the front element. */
func (this *MyQueue) Peek() int {
    
}


/** Returns whether the queue is empty. */
func (this *MyQueue) Empty() bool {
    
}

type MyStack struct {
	stack []int
}


/** Initialize your data structure here. */
func Constructor() MyStack {
    stack = &int[]{}
}


/** Push element x onto stack. */
func (this *MyStack) Push(x int)  {
    this.stack = append(this.stack, x)
}


/** Removes the element on top of the stack and returns that element. */
func (this *MyStack) Pop() int {
    for len(this.stack) > 0 {
		n := len(this.stack) - 1 // Top element
		fmt.Print(this.stack[n])
	
		return stack[:n] 
	}
}


/** Get the top element. */
func (this *MyStack) Top() int {
    
}


/** Returns whether the stack is empty. */
func (this *MyStack) Empty() bool {
    
}