package leetcode

import (
	"testing"
)

func Test_Queue(t *testing.T) {
	stack := Constructor()
	stack.Push(1)
	stack.Push(2)
	t.Log(stack)
	t.Log(stack.Top())
	t.Log(stack.Pop())   // returns 2
	t.Log(stack.Empty()) // returns false
}
