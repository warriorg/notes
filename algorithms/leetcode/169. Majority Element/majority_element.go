package leetcode

/**
Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.

You may assume that the array is non-empty and the majority element always exist in the array.

Example 1:

Input: [3,2,3]
Output: 3
Example 2:

Input: [2,2,1,1,1,2,2]
Output: 2
**/

func majorityElement(nums []int) int {
	var majority int = nums[0]
	dict := map[int]int{}
	for _, val := range nums {
		dict[val]++
		if val == majority {
			continue
		}
		if dict[val] > dict[majority] {
			majority = val
		}
	}

	return majority
}
