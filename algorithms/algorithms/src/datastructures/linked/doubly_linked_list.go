package main

import (
	"fmt"
)

type Node struct {
	prev *Node
	data int
	next *Node
}

func newNode(data int, next *Node, prev *Node) *Node {
	return &Node{
		prev: prev,
		data: data,
		next: next,
	}
}

type MyLinkedList struct {
	size int
	head *Node
	tail *Node
}

func Constructor() MyLinkedList {
	return MyLinkedList{}
}

/** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
func (self *MyLinkedList) Get(index int) int {
	if index < 0 || index >= self.size {
		return -1
	}
	curr := self.find(index)
	return curr.data
}

/** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
func (self *MyLinkedList) AddAtHead(val int) {
	node := newNode(val, self.head, nil)
	curr := self.head
	self.head = node
	if curr == nil {
		self.tail = node
	} else {
		curr.prev = node
	}

	self.size += 1
}

/** Append a node of value val to the last element of the linked list. */
func (self *MyLinkedList) AddAtTail(val int) {
	node := newNode(val, nil, self.tail)
	curr := self.tail
	self.tail = node
	if curr == nil {
		self.head = node
	} else {
		curr.next = node
	}
	self.size += 1
}

/** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
func (self *MyLinkedList) AddAtIndex(index int, val int) {
	if index < 0 || index > self.size {
		return
	}

	if index == self.size {
		self.AddAtTail(val)
	} else {
		curr := self.find(index)
		prev := curr.prev
		node := newNode(val, curr, prev)
		curr.prev = node

		if prev == nil {
			self.head = node
		} else {
			prev.next = node
		}
		self.size++
	}
}

/** Delete the index-th node in the linked list, if the index is valid. */
func (self *MyLinkedList) DeleteAtIndex(index int) {
	if index < 0 || index >= self.size {
		return
	}

	curr := self.find(index)
	prev := curr.prev
	next := curr.next

	if prev == nil {
		self.head = next
	} else {
		prev.next = next
		curr.prev = nil
	}

	if next == nil {
		self.tail = prev
	} else {
		next.prev = prev
		curr.next = nil
	}
	curr = nil
	self.size--
}

func (self *MyLinkedList) find(index int) *Node {
	if index < (self.size >> 1) {
		curr := self.head
		for i := 0; i < index; i++ {
			curr = curr.next
		}
		return curr
	} else {
		curr := self.tail
		for i := self.size - 1; i > index; i-- {
			curr = curr.prev
		}
		return curr
	}
}

func main() {
	linked := Constructor()
	linked.AddAtHead(4)
	fmt.Println(linked.Get(1))
	linked.AddAtTail(3)
	linked.AddAtIndex(1, 2)
	fmt.Println(linked.Get(1))
	linked.DeleteAtIndex(0)
	fmt.Println(linked.Get(0))
}
