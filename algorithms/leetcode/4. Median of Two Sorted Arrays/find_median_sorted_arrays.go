package main

import (
	"fmt"
)

func main() {
	nums1 := []int{1, 3}
	nums2 := []int{2}
	val := findMedianSortedArrays(nums1, nums2)
	fmt.Println(val)

	nums1 = []int{1, 3}
	nums2 = []int{2, 4}
	val = findMedianSortedArrays(nums1, nums2)
	fmt.Println(val)

	nums1 = []int{1, 2}
	nums2 = []int{3, 4}
	val = findMedianSortedArrays(nums1, nums2)
	fmt.Println(val)
}

func findMedianSortedArrays(nums1 []int, nums2 []int) float64 {
	nums := merge(nums1, nums2)

	fmt.Println(nums)
	length := len(nums)
	mid := (length - 1) / 2
	if length%2 == 0 {
		return float64(nums[mid]+nums[mid+1]) / 2.0
	} else {
		return float64(nums[mid])
	}
}

func merge(nums1, nums2 []int) []int {
	nums := make([]int, len(nums1)+len(nums2))
	i, j := 0, 0
	for i < len(nums1) && j < len(nums2) {
		if nums1[i] < nums2[j] {
			nums[i+j] = nums1[i]
			i++
		} else {
			nums[i+j] = nums2[j]
			j++
		}
	}

	for i < len(nums1) {
		nums[i+j] = nums1[i]
		i++
	}

	for j < len(nums2) {
		nums[i+j] = nums2[j]
		j++
	}
	return nums
}
