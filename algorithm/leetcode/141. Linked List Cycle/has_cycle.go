package main

import "fmt"

/**
Given a linked list, determine if it has a cycle in it.

To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.



Example 1:

Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: There is a cycle in the linked list, where tail connects to the second node.


Example 2:

Input: head = [1,2], pos = 0
Output: true
Explanation: There is a cycle in the linked list, where tail connects to the first node.


Example 3:

Input: head = [1], pos = -1
Output: false
Explanation: There is no cycle in the linked list.

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

	fmt.Println(hasCycle(input))
}

// ListNode Definition for singly-linked list.
type ListNode struct {
    Val int
    Next *ListNode
}

func hasCycle(head *ListNode) bool {
	if head == nil || head.Next == nil {
		return false
	}	

	slow := head
	fast := head.Next

	for slow != fast {
		if fast == nil || fast.Next == nil {
			return false
		}
		slow = slow.Next
		fast = fast.Next.Next
	}
	return true
}


func hasCycleMe(head *ListNode) bool {
	if head == nil || head.Next == nil {
		return false
	}
	
	one := head
	two := head.Next.Next
	
	for two != nil {
		if one == two {
			return true
		}
		one = one.Next
		if one == two {
			return true
		}
		if (two.Next == nil) {
			return false
		}

		two = two.Next.Next
	}
	return false
}
