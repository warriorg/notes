package main

func main() {

}

func merge_sort(arr []int) {
	merge_sort_c(arr, 0, len(arr)-1)
}

func merge_sort_c(arr []int, p, r int) {
	// 递归终止条件
	if p >= r {
		return
	}

	// 取p到r之间的中间位置q
	q := (p + r) / 2
	merge_sort_c(arr, p, q)
	merge_sort_c(arr, q+1, r)
	merge(arr, p, q, r)
}

func merge(arr []int, p, q, r int) {

}
