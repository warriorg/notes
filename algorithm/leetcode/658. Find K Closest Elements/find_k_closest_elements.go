package main

import "fmt"

func findClosestElements(arr []int, k int, x int) []int {
	if arr == nil {
		return []int{}
	}

	if k < 0 {
		return []int{}
	}

	left := 0
	right := len(arr) - k

	// 每次比较的是mid位置和x的距离跟mid+k跟x的距离，以这两者的大小关系来确定二分法折半的方向，最后找到最近距离子数组的起始位置.
	for left < right {
		mid := left + (right-left)/2
		if x-arr[mid] > arr[mid+k]-x {
			left = mid + 1
		} else {
			right = mid
		}
	}

	return arr[left : left+k]
}

func main() {
	reuslt := findClosestElements([]int{1, 2, 3, 4, 5}, 3, 3)
	fmt.Println(reuslt)

	reuslt = findClosestElements([]int{1, 2, 3, 4, 5}, 4, 4)
	fmt.Println(reuslt)

	reuslt = findClosestElements([]int{0, 0, 1, 2, 3, 3, 4, 7, 7, 8}, 3, 5)
	fmt.Println(reuslt)
}
