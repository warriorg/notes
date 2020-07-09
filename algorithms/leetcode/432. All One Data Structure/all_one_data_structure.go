package main

func main() {
	allOne = Constructor()
	allOne.Inc("a")
}

type AllOne struct {
}


/** Initialize your data structure here. */
func Constructor() AllOne {
    return AllOne{}
}


/** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
func (this *AllOne) Inc(key string)  {
    
}


/** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
func (this *AllOne) Dec(key string)  {
    
}


/** Returns one of the keys with maximal value. */
func (this *AllOne) GetMaxKey() string {
    
}


/** Returns one of the keys with Minimal value. */
func (this *AllOne) GetMinKey() string {
    
}


/**
 * Your AllOne object will be instantiated and called as such:
 * obj := Constructor();
 * obj.Inc(key);
 * obj.Dec(key);
 * param_3 := obj.GetMaxKey();
 * param_4 := obj.GetMinKey();
 */