####动词

```
d 表示删除（delete） 
r 表示替换（replace） 
c 表示修改（change）
y 表示复制（yank）
v 表示选取（visual select）
```
####名词 （文本对象）

```
w 表示一个单词（word）
s 表示一个句子（sentence）
p 表示一个段落（paragraph）
t 表示一个 HTML 标签（tag）
引号或者各种括号所包含的文本称作一个文本块。
```
####介词

```
i 表示“在...之内”（inside）
a 表示“环绕...”（around）
t 表示“到...位置前”（to）
f 表示“到...位置上”（forward）
```
>基本的语法为：动词 介词 名词  
>删除一个段落: （delete inside paragraph）dip  
选取一个句子: （visual select inside sentence） vis  
修改一个单词: （change inside word） ciw；（change around word） caw  
删除文本直到字符“x”（不包括字符“x”）: （delete to x） dtx  
删除文本直到字符“x”（包括字符“x”）: （delete forward x） dfx  
修改三个单词：（change three words） c3w  



Replace

```bash
:1,$ s/old/new #替换全部
```

快捷键

```
<C-o> jump back
<C-i> jump forward
<C-]> go to definition

% 跳转到相配对的括号  
gD 跳转到局部变量的定义处  
'' 跳转到光标上次停靠的地方 (是两个 ', 而不是一个")
mx 设置书签,x只能是a-z的26个字母  
`x 跳转到书签处("`"是1左边的键)  
> 增加缩进,"x>"表示增加以下x行的缩进  
< 减少缩进,"x<"表示减少以下x行的缩进  

  
{ 跳到上一段的开头  
} 跳到下一段的的开头  
( 移到这个句子的开头  
) 移到下一个句子的开头  
  
[[ 跳转至上一个函数(要求代码块中'{'必须单独占一行)  
]] 跳转至下一个函数(要求代码块中'{'必须单独占一行)  
  
C-] 跳转至函数或变量定义处  
C-O 返回跳转前位置   
C-T 同上   
nC-T 返回跳转 n 次  
  
0 数字0,跳转至行首   
^ 跳转至行第一个非空字符   
$ 跳转至行尾  
```


[NERDTree](https://github.com/scrooloose/nerdtree)
>==m 显示文件系统菜单==