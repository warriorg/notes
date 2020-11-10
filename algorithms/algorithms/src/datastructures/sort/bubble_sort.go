package main

/***
冒泡排序
*/

import "fmt"

func main() {
	arr := []int{8, 7, 2, 4, 2, 1, 8, 9, 12, 6, 5}
	fmt.Println(arr)
	bubble(arr)
	fmt.Println(arr)
}

func bubble(arr []int) []int {
	if len(arr) <= 1 {
		return arr
	}

	for i, _ := range arr {
		flag := false // 提前退出交换标志
		for j := 0; j < len(arr)-i-1; j++ {
			if arr[j] > arr[j+1] {
				// 交换数据
				tmp := arr[j]
				arr[j] = arr[j+1]
				arr[j+1] = tmp
				flag = true
			}
		}
		if !flag {
			// 没有发生交换，说明数组是有序的
			break
		}
	}
	return arr
}
