package main

import "fmt"

func main() {
	arr := []int{8, 7, 2, 4, 2, 1, 8, 9, 12, 6, 5}
	fmt.Println(arr)
	insertion(arr)
	fmt.Println(arr)
}

func insertion(arr []int) []int {
	if len(arr) <= 1 {
		return arr
	}

	for i := 1; i < len(arr); i++ {
		val := arr[i]
		j := i - 1

		for ; j >= 0; j-- {
			if arr[j] > val {
				arr[j+1] = arr[j]
			} else {
				break
			}
		}
		arr[j+1] = val
	}

	return arr

}
