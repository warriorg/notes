package main

type Node struct {
	prev *Node
	data interface{}
	next *Node
}

func newNode(data interface{}, prev *Node, next *Node) *Node {
	return &Node{
		prev: prev,
		data: data,
		next: next,
	}
}

type DoublyLinkedList struct {
}

func Constructor() DoublyLinkedList {

}

/** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
func (self *DoublyLinkedList) Get(index int) int {

}

/** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
func (this *DoublyLinkedList) AddAtHead(val int) {

}

/** Append a node of value val to the last element of the linked list. */
func (this *DoublyLinkedList) AddAtTail(val int) {

}

/** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
func (this *DoublyLinkedList) AddAtIndex(index int, val int) {

}

/** Delete the index-th node in the linked list, if the index is valid. */
func (this *DoublyLinkedList) DeleteAtIndex(index int) {

}
