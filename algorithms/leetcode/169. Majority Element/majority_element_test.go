package leetcode

import "testing"

func Test_majorityElement(t *testing.T) {
	nums := []int{3, 2, 3}
	val := majorityElement(nums)
	t.Log(val)

	nums = []int{2, 2, 1, 1, 1, 2, 2}
	val = majorityElement(nums)
	t.Log(val)
}
