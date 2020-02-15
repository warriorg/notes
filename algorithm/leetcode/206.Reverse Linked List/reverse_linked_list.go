package main

import "fmt"
/**
Reverse a singly linked list.

Example:

Input: 1->2->3->4->5->NULL
Output: 5->4->3->2->1->NULL
Follow up:

A linked list can be reversed either iteratively or recursively. Could you implement both?
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

	output := reverseList(input)
	for output != nil {
	
	fmt.Println(output)
	output = output.Next
}
}

type ListNode struct {
	Val int
	Next *ListNode
}

func reverseList(head *ListNode) *ListNode {
	if head == nil {
		return head
	}

	reverse := &ListNode {
		Val: head.Val,
	}
	for head.Next != nil {
		head = head.Next
		node := &ListNode {
			Val: head.Val,
		}
		node.Next = reverse
		reverse = node
	}

	return reverse
}

