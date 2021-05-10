package main

import "fmt"

func main() {

	l1 := &ListNode{
		Val:2,
		Next: &ListNode{
			Val: 4,
			Next: &ListNode {
				Val: 3,
			},
		},
	}

	l2 := &ListNode{
		Val:5,
		Next: &ListNode{
			Val: 6,
			Next: &ListNode {
				Val: 4,
			},
		},
	}
	fmt.Println(addTwoNumbers(l1, l2))
}


// ListNode Definition for singly-linked list.
 type ListNode struct {
	 Val int
	 Next *ListNode
 }

func addTwoNumbers(l1 *ListNode, l2 *ListNode) *ListNode {
	l1Cur := l1
	l2Cur := l2
	var head, current *ListNode

	carry := 0

	for {
		val := carry
		carry = 0

		if l1Cur != nil {
			val = val + l1Cur.Val
			l1Cur = l1Cur.Next
		}
		
		if l2Cur != nil {
			val = val + l2Cur.Val
			l2Cur = l2Cur.Next
		}

		if val > 9 {
			carry = 1
			val = val - 10
		}
		node := &ListNode{
			Val: val,
		}

		if current != nil {
			current.Next = node
		}

		if head == nil {
			head = node
		}
		current = node



		if l1Cur == nil && l2Cur == nil {
			if carry > 0 {
				node = &ListNode{Val: carry}
				current.Next = node
			}
			break
		}
		
	}


	return head

}
