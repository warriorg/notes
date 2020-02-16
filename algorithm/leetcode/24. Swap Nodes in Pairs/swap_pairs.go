package main

import "fmt"

/**
Given a linked list, swap every two adjacent nodes and return its head.

You may not modify the values in the list's nodes, only nodes itself may be changed.



Example:

Given 1->2->3->4, you should return the list as 2->1->4->3.
*/

func main() {
	input := &ListNode{
		Val: 1,
		Next: &ListNode{
			Val: 2,
			Next: &ListNode {
				Val:3,
				Next: &ListNode {
					Val: 4,
					Next: &ListNode {
						Val: 5,
					},
				},
			},
		},
	}

	output := swapPairs(input)
	for output != nil {
	
	fmt.Println(output)
	output = output.Next
}

}

type ListNode struct {
	Val int
	Next *ListNode
}

func swapPairs(head *ListNode) *ListNode {
	if head == nil || head.Next == nil {
		return head 
	}
	
	ptr := head.Next
	head.Next = ptr.Next
	ptr.Next = head

	if head.Next != nil && head.Next.Next != nil {
		head.Next = swapPairs(head.Next)
	}

	return ptr
}
