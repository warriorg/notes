package leetcode

import "testing"

func Test_Queue(t *testing.T) {
	q := Constructor()
	q.Push(1)
	q.Push(2)
	t.Log(q)
	t.Log(q.Peek())
	t.Log(q.Pop())
	t.Log(q.Empty())
}
