package main

import (
	"fmt"
	"strings"
)

type ArrayStack struct {
	data []int // 存放数据的位置
	top  int   // 栈顶指针
}

/** Initialize your data structure here. */
func Constructor() *ArrayStack {
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

/** String pring string */
func (this *ArrayStack) String() string {
	if this.Empty() {
		return "empty stack"
	}
	return strings.Trim(strings.Replace(fmt.Sprint(this.data), " ", ",", -1), "[]")
}

func main() {
	obj := Constructor()
	obj.Push(2)
	obj.Push(6)
	obj.Push(8)
	obj.Push(9)

	fmt.Println(obj)
	val := obj.Pop()
	fmt.Println(val)
	val = obj.Peek()
	fmt.Println(val)
	b := obj.Empty()
	fmt.Println(b)
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * obj := Constructor();
 * obj.Push(x);
 * param_2 := obj.Pop();
 * param_3 := obj.Peek();
 * param_4 := obj.Empty();
 */
