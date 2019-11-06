## 数组
[1.1 实现一个算法，确定一个字符串的所有字符是否全都不同。假使不允许使用额外的 数据结构，又该如何处理](#1.1) <br/>

[1.2 用C或者C++实现void reverse(char* str)函数，即反转一个null结尾的字符串](#1.2)<br/>

[1.3 给定两个字符串，请编写程序，确定其中一个字符串的字符重新排列后，能否变成另一个字符串](#1.3)<br/>

[1.4 编写一个方法，将字符串中的空格全部替换为“% 20”。假定该字符串尾部有足够的空间存放新增字符，并且知道字符串的“真实”长度。（注：用Java实现的话，请使用字符数组实现，以便直接在数组上操作）](#1.4)<br/>

[1.5 利用字符重复出现的次数，编写一个方法，实现基本的字符串压缩功能。比如，字符串aabcccccaaa会变为a2b1c5a3。若“压缩”后的字符串没有变短，则返回原先的字符串。](#1.5)<br/>

[1.6 给定一幅由 N×N 矩阵表示的图像，其中每个像素的大小为4字节，编写一个方法，将图像旋转90度。不占用额外内存空间能否做到？](#1.6)<br/>

[1.7 编写一个算法，若MxN矩形中牧歌元素为0，则将其所在的行与列清零。](#1.7)<br/>

[1.8 假定有一个方法isSubstring,可检查一个单词是否为其他字符串的子串。给定两个字符串s1,s2,请编写代码检查s2是否为s1旋转而成，要求只能调用一次isSubstring。(比如waterbottle是erbottlewat旋转后的字符串)](#1.8)<br/>

## 链表
[2.1 编写代码，移除未排序链表中重复节点。如果不得使用缓冲区，改怎么解决](#2.1)

[2.2 实现一个算法, 找出单向链表中倒数第k个结点](#2.2)

[2.3 实现一个算法，删除单向链表中间的某个结点，假定你只能访问该结点。](#2.3)

[2.4 编写代码，以给定值x为基准将链表分割成两部分，所有小于x的结点排在大于或等于x的结点之前。](#2.4)

[2.5 给定两个用链表表示的整数，每个结点包含一个数位。这些数位是反向存放的，也就是个位排在链表首部。编写函数对这两个整数求和，并用链 表形式返回结果。(假设这些位数正向存放，请在做一遍)](#2.5)

[2.6 给定一个有环链表，实现一个算法返回环路的开头结点。](#2.6)

[2.7 编写一个函数，检车链表是否为回文](#2.7)

## 栈与队列
[3.1 描述如何只用一个数组来实现三个栈](#3.1) 

<h5 id='1.1'>
1.1 实现一个算法，确定一个字符串的所有字符是否全都不同。假使不允许使用额外的 数据结构，又该如何处理
</h5>

假定所有字符集为ASCII

```java
public boolean isUniqueChars2(String str) {
	if (str.length() > 256) return false;
	
	boolean[] char_set = new boolean[256];
	for (int i = 0; i < str.length(); i++) {
		int val = str.charAt(i);
		if (char_set[val]) {
			return false;
		}
		
		char_set[val] = true;
	}
	return true;
}
```
使用 ==微向量(bit vector)== 可以控制空间占用减少为远先1/8下面这段代码假定字符串只含有小写字母a到z。

```java
public boolean isUniqueChars(String str) {
	if (str.length() > 26) return false;
	
	int checker = 0;
	for (int i = 0; i < str.length(); i++) {
		int val = str.charAt(i) - 'a';
		if ((checker & (1 << val)) > 0) {
			return false;
		}
		checker |= (1 << val);
	}
	return true;
}
```
另外，还有一下2种解法

1. 将字符串中的每一个字符与其余字符进行比较。时间复杂度𝐎(n^2)，空间复杂度 𝐎(1)。
2. 若允许修改输入字符串，可以在𝐎(nlog(n))时间里对字符串排序。然后检查其中有无相邻字符完全相同的情况。

<h5 id='1.2'>
1.2 用C或者C++实现void reverse(char* str)函数，即反转一个null结尾的字符串
</h5>

```C
void reverse(char *str) {
	char* end = str;
	char tmp;
	if (str) {
		while (*end) { //找出字符串末尾
			++end;
		}
		--end; //回退一个字符，最后一个为null字符
	}
	
	//从字符串首尾开始交换两个字符, 直至两个指针在中间碰头
	while (str < end) {
		tmp = *str;
		*str++ = *end;
		*end-- = tmp;
	}
}
```
<h5 id='1.3'>
1.3 给定两个字符串，请编写程序，确定其中一个字符串的字符重新排列后，能否变成另一个字符串
</h5>
1. 排序字符串，然后比较
2. 检查两个字符串的字符数是否相同

```java
public boolean permutation(String s, String t) {
	if (s.length() != t.length()) {
		return false;
	}
	
	int[] letters = new int[256]; //假设为ASCII
	char[] s_array = s.toCharArray();
	for (char c : s_array) {
		letters[c]++;
	}
	
	for (int i = 0; i < t.length(); i++) {
		int c = (int) t.charAt(i);
		if (--letters[c] < 0) {
			return false;
		}
	}
	
	return true;
}
```
<h5 id='1.4'>
1.4 编写一个方法，将字符串中的空格全部替换为“% 20”。假定该字符串尾部有足够的空间存放新增字符，并且知道字符串的“真实”长度。（注：用Java实现的话，请使用字符数组实现，以便直接在数组上操作）
</h5>

处理字符串操作问题时，常见做法是从字符串的尾部开始编辑，从后往前反向操作。这种做法很有用，因为字符串尾部有额外的缓冲，可以直接修改，不必担心覆写原有数据

```java
public void replaceSpaces(char[] str, int lenght) {
	//找出所有空格
	int spaceCount = 0, newLength, i;
	for (i = 0; i < length; i++) {
		if (str[i] == ' ') {
			spaceCount++;
		}
	}
	newLength = length + spaceCount * 2;
	str[newLength] = '\0';
	for (i = length -1; i >= 0; i--) {
		if (str[i] == ' ') {
			str[newLength - 1] = '0';
			str[newLength - 2] = '2';
			str[newLength - 3] = '%';
			newLength = newLength - 3;
		} else {
			str[newLength-1] = str[i];
			newLength = newLength - 1;
		}
	}
}
```

<h5 id='1.5'>1.5 利用字符重复出现的次数，编写一个方法，实现基本的字符串压缩功能。比如，字符串aabcccccaaa会变为a2b1c5a3。若“压缩”后的字符串没有变短，则返回原先的字符串。</h5>
```java
public String compressBad(String str) {
	String mystr = "";
	char last = str.charAt(0);
	int count = 1;
	for (int i = 1; i < str.length(); i++) {
		if (str.charAt(i) == last) {
			count++;
		} else {
			mystr += last + "" + count;
			last = str.charAt(i);
			count = 1;
		}
	}
	return mystr + last + count;
}
```
>这段代码并未处理压缩后字符串比原始字符冲长的情况
>这段代码的执行时间为𝐎(p + k^2), 其中p为原始字符串长度，k为字符序列的数量。执行速度慢，原因是字符串拼接操作的时间复杂度为𝐎(n^2)

使用StringBuffer优化部分性能

```java
String compressBetter(String str){
	//检查压缩后的字符串是否变得更长
	int size = countCompression(str);
	if (size >= str.length()) {
		return str;
	}
	
	StringBuffer mystr = new StringBuffer();
	char last = str.charAt(0);
	int count = 1;
	for (int i = 1; i < str.length(); i++) {
		if (str.charAt(i) == last) {
			//找到重复字符
			count++;
		} else {
			//插入字符的数目，更新last字符
			mystr.append(last);
			mystr.append(count);
			last = str.chatAt(i);
			count = 1;
		}
	}
	mystr.append(last);
	mystr.append(count);
	return mystr.toString();
}

int countCompression(String str) {
	if (str == null || str.isEmpty()) return 0;
	char last = str.charAt(0);
	int size = 0;
	int count = 1;
	for (int i = 1; i < str.length(); i++) {
		if (str.charAt(i) == last) {
			count++;
		} else {
			last = str.charAt(i);
			size += 1 + String.valueOf(count).length();
			count = 1;
		}
	}
	size += 1 + String.valueOf(count).length();
	return size;	
}
```
不使用StringBuffer的方式

```java
String compressAlternate(String str) {
	//检查压缩后的字符串是否会变得更长
	int size = countCompression(str);
	if (size >= str.length()) {
		return str;
	}
	
	char[] array = new char[size];
	int index = 0;
	char last = str.charAt(0);
	int count = 1;
	for (int i = 1; i < str.length(); i++) {
		if (str.charAt(i) == last) {
			//找到重复的字符
			count++;
		} else {
			//更新重复字符的数目
			index = setChar(array, last, index, count);
			last = str.charAt(i);
			count = 1
		}
	}
	//以最后一组重复字符更新字符串
	intdex = setChar(array, last, index, count);
	return String.valueOf(array);
}

int setChar(char[] array, char c, int index, int count) {
	array[index] = c;
	index++;
	
	//将数目穿成字符串，然后转换成字符数组
	char[] cnt = String.valueOf(count).toCharArray();
	
	for (char x : cnt) {
		array[index] = x;
		index++;
	}
	return index;
}

int countCompression(String str) {
	//与之前相同
}
```

<h5 id='1.6'>1.6 给定一幅由 N×N 矩阵表示的图像，其中每个像素的大小为4字节，编写一个方法，将图像旋转90度。不占用额外内存空间能否做到</h5>
![image](images/interview-1.6.png)

```java
public void rotate(int[][] matrix, int n) {
	for (int layer = 0; layer = n / 2; ++layer) {
		int first = layer;
		int last = n - 1 - layer;
		for (int i = first; i < last; ++i) {
			int offset = i = first;
			//存储上边	
			int top = matrix[first][i];
			//左到上
			matrix[first][i] = matrix[last-offset][first];
			//下到左
			matrix[last-offset][first] = matrix[last[last - offset];
			//右到下
			matrix[last][last - offset] = matrix[i][last];
			//上到右
			matrix[i][last] = top;
		}
	}
}
```

<h5 id='1.7'>1.7 编写一个算法，若MxN矩形中牧歌元素为0，则将其所在的行与列清零。</h5>
> 需要先遍历数据，标记所有的0

```java
public void setZeros(int[][] matrix) {
	boolean[] row = new boolean[matrix.length];
	boolean[] column = new boolean[matrix[0].length];
	//记录值为0的元素所在行索引和列索引
	for (int i = 0; i < matrix.length; i++) {
		for (int j = 0; j < matrix[0].length; j++) {
			if (matrix[i][j] == 0) {
				row[i] = true;
				column[j] = true;
			}
		}
	}
	
	//若行i或者j有个元素为0，则将arr[i][j]置为0
	for (int i = 0; i < matrix.length; i++) {
		for (int j = 0; j < matrix[0].length; j++) {
			if (row[i] || column[j]) {
			
			}
		}
	}
}
```

<h5 id='1.8'>1.8 假定有一个方法isSubstring,可检查一个单词是否为其他字符串的子串。给定两个字符串s1,s2,请编写代码检查s2是否为s1旋转而成，要求只能调用一次isSubstring。(比如waterbottle是erbottlewat旋转后的字符串)</h5>
>假设s2由s1旋转而成，我们只要找出旋转点在那。<br/>
>s1 = xy = waterbottle;<br/>
>x = wat<br/>
>y = erbottle<br/>
>s2 = yx = erbottlewat
>我们需要确认有没有办法将s1切分成x和y，以满足xy = s1 和 yx = s2,不论x和y之间的分割点在何处，我们会发现 yx肯定是 xyxy 的字串。也即 s2 总是s1s1的字串

```java
public boolean isRotation(String s1, String s2) {
	int len = s1.length();
	//检查s1和s2是否等长且不为空
	if (len == s2.length() && len > 0) {
		//拼接s1和s2， 放入新字符串中
		String s1s1 = s1 + s1;
		return isSubstring(s1s1, s2);
	}
	return false;
}
```

<h5 id='2.1'>2.1 编写代码，移除未排序链表中重复节点。如果不得使用缓冲区，改怎么解决</h5>
>迭代访问整个链表，将每个结点加入散列表。若发现重复，则将该结点移除

```java
public static void deleteDups(LinkedListNode n) {
	HashTable table = new HashTable();
	LinkedListNode previous = null;
	while (n != null) {
		if (table.containsKey(n.data)) {
			previous.next = n.next;
		} else {
			table.put(n.data, true);
			previous = n;
		}
		n = n.next;
	}
}
```

不使用缓冲区

```java
public static void deleteDups(LinkedListNode head) {
	if (head == null) return;
	
	LinkedListNode current = head;
	while (current != null) {
		//移除后续只相同的所有结点
		LinkedListNode runner = current;
		while (runner.next != null) {
			if (runner.next.data == current.data) {
				runner.next = runner.next.next;
			} else {
				runner = runner.next;
			}
		}
		current = current.next;
	}
}
```

<h5 id='2.2'>2.2 实现一个算法, 找出单向链表中倒数第k个结点</h5>
解法1 链表长度已知

> 倒数第k个几点就是 (length - k)

解法2 递归

方法A

```java 
public static int nthToLast(LinkedListNode head, int k) {
	if (head == null) {
		return 0;
	}
	
	int i = nthToLast(head.next, k) + 1;
	if (i == k) {
		System.out.println(head.data);
	}
	return i;
}
```
方法B

通过引用传递值，返回结点值

```C++
node* nthToLast(node* head, intk, int& i) {
	if (head == NULL) {
		return NULL;
	}
	node * nd = nthToLast(head->next, k, i);
	i = i + 1;
	if (i == k) {
		return head;
	}
	return nd;
}
```

方法C 创建包裹类

```java
public class IntWrapper {
	public int value = 0;
}

LinkedListNode nthToLastR2(LinkedListNode head, int k, IntWrapper i) {
	if (head == null) {
		return null;
	}
	LinkedListNode node = nthToLastR2(head.next, k, i);
	i.value = i.value + 1;
	if (i.value = k) {
		return head;
	}
	return node;
}
```

解法3 迭代法

一种效率更高但不太直观的解法是以迭代方式实现。我们可以使用两个指针p1和p2，并将它们指向链表中相距k个结点的两个结点，具体做法是先将p1和p2指向链表首结点，然后将p2向前移动k个结点。之后，我们以相同的速度移动这两个指针，p2会在移动LENGTH - k步后抵达链表尾结点。此时，p1会指向链表第LENGTH - k个结点，或者说倒数第k个结点。

```java
LinkedListNode nthToLast(LinkedListNode head, int k) {
	if (k <= 0) return null;
	LinkedListNode p1 = head;
	LinkedListNode p2 = head;
	
	//p2向前移动k结点
	for (int i = 0; i < k - 1; i++) {
		if (p2 == null) return null;
		p2 = p2.next;
	}
	if (p2 == null) return null;
	
	//现在已同样的速冻移动p1和p2,当p2抵达链表末尾时，
	//p1 刚好只想倒数第k个节点。
	while (p2.next != null) {
		p1 = p1.next;
		p2 = p2.next;
	}
	return p1;
}
```

<h5 id='2.3'>2.3 实现一个算法，删除单向链表中间的某个结点，假定你只能访问该结点。</h5>
> 直接将后续节点的数据复制到当前节点，然后删除后续的节点，如果待删除结点为链表的结尾，问题误解

```java
public static boolean deleteNode(LinkedListNode n) {
	if (n == null || n.next == null) {
		return false;
	} 
	
	LinkedListNode next = n.next;
	n.data = next.data;
	n.next = next.next;
	return true;
}
```

<h5 id='2.4'>2.4 编写代码，以给定值x为基准将链表分割成两部分，所有小于x的结点排在大于或等于x的结点之前。</h5>
>创建2个链表，一个存放小于x的元素；另一个存放大于x的元素

```java
public LinkedListNode partition(LinkedListNode node, int x) {
	LinkedListNode beforeStart = null;
	LinkedListNode beforeEnd = null;
	LinkedListNode afterStart = null;
	LinkedListNode afterEnd = null;
	
	//分割链表
	while (node != null) {
		LinkedListNode next = node.next;
		node.next = null;
		if (node.data < x) {
			//将结点插入before 链表
			if (beforeStart == null) {
				beforeStart = node;
				beforeEnd = beforeStart;
			} else {
				beforeEnd.next = node;
				beforeEnd = node;
			}
		} else {
			//将结点插入after链表
			if (afterStart == null) {
				afterStart = node;
				afterEnd = afterStart;
			} else {
				afterEnd.next = node;
				afterEnd = node;
			}
		}
		node = next;
	}
	if (beforeStart = null) {
		return afterStart;
	}
	//合并链表
	beforeEnd.next = afterStart;
	return beforeStart;
}
```
<h5 id='2.5'>2.5 给定两个用链表表示的整数，每个结点包含一个数位。这些数位是反向存放的，也就是个位排在链表首部。编写函数对这两个整数求和，并用链 表形式返回结果。(假设这些位数正向存放，请在做一遍)</h5>
>假设链表
><pre>
>	7 -> 1 -> 6
>+ 5 -> 9 -> 2
></pre>
>即 求 617 + 295
>步骤如下
>
>1. 7 + 5 = 12 , 则2成为结果链表的第一个结点，并将1进位给下一求和运算 链表 2->?
>2. 1 + 9 + 1 = 11, 链表 2 -> 1 -> ?
>3. 6 + 2 + 1 = 9， 链表 2 -> 1 -> 9

```java
LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2, int carry) {
	if (l1 == null && l2 == null && carry = 0) {
		return null;
	}
	LinkedListNode result = new LinkedListNode();
	int value = carry;
	if (l1 != null) {
		value += l1.data;
	}
	if (l2 != null) {
		value += l2.data;
	}
	result.data = value % 10; //有何结果为各位
	递归
	LinkedListNode more = addLists(l1 == null ? null : l1.next,
									l2 == null ? null : l2.next,
									value >= 10 ? 1 : 0);
	result.setNext(more);
	return result;
}

```
>进阶 
>假设链表
>	1 -> 2 -> 3 -> 4
>+ 5 -> 6 -> 7
>即 1234 + 0567

```java 
public class PartialSum {
	public LinkedListNode sum = null;
	public int carry = 0;
}

LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2) {
	int len1 = length(l1);
	int len2 = length(l2);
	
	//用零填充较短的链表
	if (len1 < len2) {
		l1 = padList(l1, len2 - len1);
	} else {
		l2 = padList(l2, len1 -len2);
	}
	
	//对两个链表求和
	PartialSum sum = addListsHelper(l1, l2);
	//如有进位，则插入链表首部，否则，直接返回整个链表
	if (sum.carry == 0) {
		return sum.sum;
	} else {
		LinkedListNode result = insertBefore(sum.sum, sum.carry);
		return result;
	}
}

PartialSum addListsHelper(LinkedListNode l1, LinkedListNode l2) {
	if (l1 == null && l2 == null) {
		PartialSum sum = new PartialSum();
		return sum;
	}
	//对较小的数字递归求和
	PartialSum sum = addListsHelper(l1.next, l2.next);
	//将进位和当前数据相加
	int val = sum.carry + l1.data + l2.data;
	//插入当前数字的求和结果
	LinkedListNode full_result = insertBefore(sum.sum, val % 10);
	//返回求和结果和进位值
	sum.sum = full_result;
	sum.carry = val / 10;
	return sum;
}

//用零填充链表
LinkedListNode padList(LinkedListNode l, int padding) {
	LinkedListNode head = l; 
	for (int i = 0; i < padding; i++) {
		LinkedListNode n = new LinkedListNode(0, null, null);
		head.prev = n;
		n.next = head;
		head = n;
	}
	return head;
}

//辅助函数，将结点插入链表首部
LinkedListNode insertBefore(LinkedListNode list, int data) {
	LinkedListNode node = new LinkedListNode(data, null, null);
	if (list != null) {
		list.prev = node;
		node.next = list;
	}
	return node;
}
```

<h5 id='2.6'>2.6 给定一个有环链表，实现一个算法返回环路的开头结点。</h5>
>检测链表环路，简单的做法叫 FastRunner/SlowRunner法。FastRunner 一次移动2步，SlowRunner一次移动一步。
>
>FastRunner会不会刚好“越过”SlowRunner，而不发生碰撞呢？绝无可能。假设FastRunner真的越过了SlowRunner，且SlowRunner处于位置i，FastRunner处于位置i + 1。那么，在前一步，SlowRunner 就处于位置 i - 1，FastRunner处于位置((i + 1)- 2)或i - 1。也就是说，两者碰在一起了。
>
>步骤如下
>
>1. 创建两个指针：FastRunner 和 SlowRunner
>2. SlowRunner 每走一步， FastRunner走两步，则当SlowRunner进入环路时，FastRunner已经进入环路K个结点后，且这时他们相距LOOP\_SIZE-K的结点；此时，SlowRunner每走一个结点，FastRunner就每走两个结点，由于这是一个环路，所以每走一次两者的距离就会更近一个结点。因此在走了LOOP\_SIZE-K个结点后两者会碰在一起，而且这时他们距离环路起始处刚好有K个结点。
>3. 两者碰在一起时，他们距离环路起始处还有K个结点，将SlowRunner指向LinkedListHead， FastRunner保持不变，这时SlowRunner和FastRunner距离环路起始处均有K个结点
>4. 以相同速度移动SlowRunner和FastRunner，一次一步，这两个指针会在此碰到一起，这是在K步之后，而新的碰撞出就是环路的起始结点。然后返回新的碰撞处。



```java

public LinkedListNode FindBeginging(LinkedListNode head){
	LinkedListNode slow = head;
	LinkedListNode fast = head;
	
	while( fast != null && fast.next!= null ) {
		slow = slow.next;
		fast = fast.next.next;
		if (slow == fast) {
			break;
		}
	}
	
	//no collision, no loop
	if (fast == null || fast.next == null) {
		return null;
	}
	
	//slow points to head, fast points to collision spot， 两者以相同速度移动，则必定会碰撞在环路开始处
	slow = head;
	while (slow != fast) {
		slow = slow.next;
		fast = fast.next;
	}
	
	return fast;
}
```

<h5 id='2.7'>2.7 编写一个函数，检车链表是否为回文</h5>
解法1: 反转并比较

> 反转整个链表，然后比较反转链表和原始链表，若2者相同，则该链表为回文。在比较时，只需要比较链表的前办部分。如果前半部分相同，那么，两者后半部分肯定相同

解法2: 迭代法

```java
public boolean isPalinddrome(LinkedListNode head) {
	LinkedListNode fast = head;
	LinkedListNode slow = head;
	
	Stack<Integer> stack = new Stack<Integer>();
	
	while( fast != null && fast.next != null ) {
		stack.push(slow.data);
		slow = slow.next;
		fast = fast.next.next;
	}
	
	//如果链表有奇数个元素，那么fast这时不为空，则比较后半段时跳过中间元素
	if ( fast != null ) {
		slow = slow.next;
	}
	
	while (slow != null) {
		int top = stack.pop().intValue();
		//如果不相同，则不是回文
		if (top != slow.data) {
			return false;
		}
		slow= slow.next;
	}
	return true;
}
```
解法3 递归法

```java
class Result{
	public LinkedListNode node;
	public boolean result;
}

Result isPalindromeRecurse(LinkedListNode head, int length) {
	if (head == null || length == 0) {
		return new Result(null, true);
	}
	else if(length == 1) {
		return new Result(head.next,true);
	}
	else if(length == 2) {
		return new Result(head.next.next, head.data == head.next.data);
	}
	Result res = isPalindromeRecurse(head.next, length -2);
	if(!res.result || res.node == null){
		return res;
	}
	else{
		res.result = head.data == res.node.data;
		res.node = res.node.next;
		return res;
	}
}

boolean isPalinddrome(LinkedListNode head) {
	Result p = isPalindromeRecurse(head, listSize(head));
	return p.result;
}
```

<h5 id='3.1'>3.1 描述如何只用一个数组来实现三个栈</h5>
方法1 固定分割
将数组划分为3等份，将每个栈的增长限制在各自的空间里。 "["包含端点 "(" 表示不含端点

* 栈1， 使用 [0, n/3)
* 栈2， 使用 [n/3, 2n/3)
* 栈3， 使用 [2n/3, n)

```java
int stackSize = 100;
int[] buffer = new int[stackSize * 3];
int[] stackPointer = {-1, -1, -1};

void push(int stackNum, int value) throws Exception {
	if (stackPointer[stackNum] + 1 >= stackSize) {
		throw new Exception("Out of space.");
	}
	//栈指针自增，更新栈顶元素的值
	stackPointer[stackNum]++;
	buffer[absTopOfStack(stackNum)] = value;
}

int pop(int stackNum) throws Exception {
	if (stackPointer[stackNum] == -1) {
		throw new Exception("Trying to pop an empty stack.");
	}
	int value = buffer[absTopOfStack(stackNum)]; //获取栈顶元素值
	buffer[absTopOfStack(stackNum)] = 0; //清零指定索引元素的值
	stackPointer[stackNum]--;	//指针自减
	return value;
}

int peek(int stackNum) {
	int index = absTopOfStack(stackNum);
	return buffer[index];
}

boolean isEmpty(int stackNum) {
	return stackPointer[stackNum] == -1;
}

int absTopOfStack(int stackNum) {
	return stackNum * stackSize + stackPointer[stackNum];
}
```

