package leetcode

import (
	"testing"
)

func Test_Queue(t *testing.T) {
	stack := Constructor()
	stack.Push(1)
	stack.Push(2)
	stack.Peek()  // returns 2
	stack.Pop()   // returns 2
	stack.Empty() // returns false

}

func Test_MyStack(t *testing.T) {

}
