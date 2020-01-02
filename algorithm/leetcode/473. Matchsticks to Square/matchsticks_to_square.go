package main

import (
	"math/rand"
	"fmt"
)

func main() {
	arr := []int{1, 1, 2, 2, 2}
	result := makesquare(arr)
	fmt.Println(result)
}

func makesquare(nums []int) bool {
	if len(nums) < 4 {
		return false
	}

	sum := 0;
	for _, value := range nums {
		sum += value
	}

	if sum % 4 != 0 {
		return false
	}

	arr := reverse(quicksort(nums))
	sums := make([]int, 4)
	return search(arr, sums, sum/4, 0)
}

func search(nums []int, sums []int, a int, idx int) bool {
	length := len(nums)
	if idx == length {
		if (sums[0] == a && sums[1] == a && sums[2] == a) {
			return true;
		}
		return false;
	}
	for i := 0; i < 4; i++ {
		if sums[i] + nums[idx] <= a {
			sums[i] += nums[idx];
			if search(nums, sums, a, idx + 1) {
				return true;
			}
			sums[i] -= nums[idx];
		}
	}
	return false;
}

func reverse (a []int) []int {
	for i, j := 0, len(a)-1; i < j; i, j = i+1, j-1 {
		a[i], a[j] = a[j], a[i]
	}
	return a
}


func quicksort(a []int) []int {
	if len(a)<2 {
		return a
	}
	left, right := 0, len(a) - 1
	pivot := rand.Int() % len(a)
	a[pivot], a[right] = a[right], a[pivot]
	for i := range a {
		if a[i] < a[right] {
			a[left], a[i] = a[i], a[left]
			left++
		}
	}
	a[left], a[right] = a[right], a[left]
	quicksort(a[:left])
	quicksort(a[left+1:])
	return a
}