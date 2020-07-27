package queue

import "testing"


func TestArrayQueue(t *testing.T) {
	q := Constructor()
	q.Push(1)
	q.Push(2)
	q.Push(3)
	t.Log(q)
	q.Pop()
	t.Log(q)
}
