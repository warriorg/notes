package main

/***
 * 1.1　实现一个算法，确定一个字符串的所有字符是否全都不同。假使不允许使用额外的数据结构，又该如何处理？（第46页）
 *
 */

 import (
	 "fmt"
	 "os"
 )

func main() {
	if len(os.Args) < 2 {
		fmt.Println("请输入字符串")
		os.Exit(0)
	}

	if isUniqueChars(os.Args[1]) {
		fmt.Println("没有重复的字符串")
	} else {
		fmt.Println("字符串重复了")
	}
}

func isUniqueChars(str string) bool {
	if len(str) > 256 {
		return false
	}

	var seen [256]byte = [256]byte{0}
	for _, v := range str {
		if seen[v] == 1 {
			return false
		} else {
			seen[v] = 1
		}
	}

	return true
}

