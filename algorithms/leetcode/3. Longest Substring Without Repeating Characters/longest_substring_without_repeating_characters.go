package main

import "fmt"

func main() {
	s := "abcabcbb"
	fmt.Println(3, lengthOfLongestSubstring(s))

	s = "bbbbb"
	fmt.Println(1, lengthOfLongestSubstring(s))

	s = "pwwkew"
	fmt.Println(3, lengthOfLongestSubstring(s))

	s = ""
	fmt.Println(0, lengthOfLongestSubstring(s))

}

func lengthOfLongestSubstring(s string) int {
	m := make(map[byte]int)
	i, ans := 0, 0

	for j := 0; j < len(s); j++ {
		if v, ok := m[s[j]]; ok {
			if v > i {
				i = v
			}
		}

		if j-i+1 > ans {
			ans = j - i + 1
		}
		m[s[j]] = j + 1
	}

	return ans
}
