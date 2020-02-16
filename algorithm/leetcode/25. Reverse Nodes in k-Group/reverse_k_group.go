package main

/***
Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.

k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.

Example:

Given this linked list: 1->2->3->4->5

For k = 2, you should return: 2->1->4->3->5

For k = 3, you should return: 3->2->1->4->5

Note:

Only constant extra memory is allowed.
You may not alter the values in the list's nodes, only nodes itself may be changed.
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

	output := reverse(input)
	for output != nil {
		
		fmt.Println(output)
		output = output.Next
	}
}

// ListNode Definition for singly-linked list.
type ListNode struct {
    Val int
    Next *ListNode
}

func reverseKGroup(head *ListNode, k int) *ListNode {
    if head == nil {
		return head 
	}
	if k < 2 {
		return head
	}
	reverse := head
	pre,cur := head, head
	var result *ListNode	
	i := 0
	for cur.Next != nil {
		i++	
		if i == k {
		}
		
		cur = cur.Next
		reverse.Next = cur
		
	}
	return result
}

func reverse(head *ListNode) *ListNode {
	if head == nil {
		return head
	}
	reverse := head
	for head.Next != nil {
		head = head.Next
		node := head	
		node.Next = reverse
		reverse = node
	}
}
