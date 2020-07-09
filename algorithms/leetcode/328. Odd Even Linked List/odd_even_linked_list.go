package main

import "fmt"

// ListNode list node
type ListNode struct {
	Val  int
	Next *ListNode
}

func main() {
	test1 := &ListNode{Val: 1, Next: &ListNode{Val: 2, Next: &ListNode{Val: 3, Next: &ListNode{Val: 4, Next: &ListNode{Val: 5, Next: &ListNode{Val: 6, Next: nil}}}}}}
	node := oddEvenList(test1)

	for node != nil {
		fmt.Println(node.Val)
		node = node.Next
	}
}

func oddEvenList(head *ListNode) *ListNode {
	if head == nil {
		return head
	}

	var odd, even, oddFirst, evenFirst *ListNode
	index := 1

	node := head

	for node != nil {
		if index%2 == 0 {
			if even != nil {
				even.Next = node
				even = node
			} else {
				even = node
				evenFirst = node
			}
		} else {
			if odd != nil {
				odd.Next = node
				odd = node
			} else {
				odd = node
				oddFirst = node
			}
		}
		index++
		node = node.Next

	}

	odd.Next = evenFirst
	if even != nil && even.Next != nil {
		even.Next = nil
	}

	return oddFirst
}
