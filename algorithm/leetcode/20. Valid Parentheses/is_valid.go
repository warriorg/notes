package main

/**
Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

An input string is valid if:

Open brackets must be closed by the same type of brackets.
Open brackets must be closed in the correct order.
Note that an empty string is also considered valid.

Example 1:

Input: "()"
Output: true
Example 2:

Input: "()[]{}"
Output: true
Example 3:

Input: "(]"
Output: false
Example 4:

Input: "([)]"
Output: false
Example 5:

Input: "{[]}"
Output: true
*/
import "fmt"

func main() {
	fmt.Println(isValid("()"))
	fmt.Println(isValid("()[]{}"))
	fmt.Println(isValid("(]"))
	fmt.Println(isValid("){"))
}

func isValid(s string) bool {
	if s == "" {
		return true 
	}
	if len(s) < 2 {
        return false
    }
	stack := []string{}
	paren_map := map[string]string{")": "(", "]": "[", "}": "{"}
	
	for _, v := range s {
		 if vv, ok := paren_map[string(v)]; ok {
			if len(stack) == 0 {
				return false
			}
			val := stack[len(stack)-1]
			if val != vv {
				return false
			}
			stack = stack[0:len(stack)-1]
		 } else {
			 stack = append(stack, string(v))
		 }
	}

	return len(stack) == 0

}

