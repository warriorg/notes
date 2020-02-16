package main

/***
现在有两个指针，第一个指针，每走一次走一步，第二个指针每走一次走两步，如果他们走了t次之后相遇在K点
那么       指针一  走的路是      t = X + nY + K        ①
             指针二  走的路是     2t = X + mY+ K       ②          m,n为未知数

把等式一代入到等式二中, 有
2X + 2nY + 2K = X + mY + K
=>   X+K  =  (m-2n)Y    ③
*/

func main() {

}

// ListNode Definition for singly-linked list.
type ListNode struct {
    Val int
    Next *ListNode
}

func detectCycle(head *ListNode) *ListNode {
	if head == nil || head.Next == nil {
		return nil
	}	

	slow, fast := head, head
	for fast != nil && fast.Next != nil {
		slow, fast = slow.Next, fast.Next.Next
		if slow == fast {
			slow = head 
			for slow != fast {
				slow, fast = slow.Next, fast.Next
			}
			return slow
		}
	}
	return nil 
}
