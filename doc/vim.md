## 四种模式

### 正常模式



### 插入模式



### 命令模式



### 可视模式







## 基础操作

### 打开历史文件

```bash
:browse oldfiles  # 历史文件记录
```

vim将以前的十个文件存储到文件`~/.viminfo`标记部分。你可以使用`'0`，`'1`，`'2`，... `'9`它们之间跳跃。

### Move

```bash
h   move one character left
j   move one row down
k   move one row up
l   move one character right
w   move to beginning of next word
b   move to previous beginning of word
e   move to end of word
W   move to beginning of next word after a whitespace
B   move to beginning of previous word before a whitespace
E   move to end of word before a whitespace

0   move to beginning of line
$   move to end of line
_   move to first non-blank character of the line
g_  move to last non-blank character of the line

gg  move to first line
G   move to last line
ngg move to n'th line of file (n is a number; 12gg moves to line 12)
nG  move to n'th line of file (n is a number; 12G moves to line 12)
H   move to top of screen
M   move to middle of screen
L   move to bottom of screen

zz  scroll the line with the cursor to the center of the screen
zt  scroll the line with the cursor to the top
zb  scroll the line with the cursor to the bottom

Ctrl-D  move half-page down
Ctrl-U  move half-page up
Ctrl-B  page up
Ctrl-F  page down
Ctrl-O  jump to last (older) cursor position
Ctrl-I  jump to next cursor position (after Ctrl-O)
Ctrl-Y  move view pane up
Ctrl-E  move view pane down

n   next matching search pattern
N   previous matching search pattern
*   next whole word under cursor
#   previous whole word under cursor
g*  next matching search (not whole word) pattern under cursor
g#  previous matching search (not whole word) pattern under cursor
gd  go to definition/first occurrence of the word under cursor
%   jump to matching bracket { } [ ] ( )

fX  to next 'X' after cursor, in the same line (X is any character)
FX  to previous 'X' before cursor (f and F put the cursor on X)
tX  til next 'X' (similar to above, but cursor is before X)
TX  til previous 'X'
;   repeat above, in same direction
,   repeat above, in reverse direction
```

### 左右移动

```bash
shift+</>
:80,89> # 把80，89行左移动，右移动<
```

### 记录操作

在normal模式下，按q，再按下一个字母或数字，来标识将记录保存的位置。这时编辑器下边就显示“recording”了，再按一下q就完成记录了。使用@数字或者字母来播放刚才的记录



### Tabs

#### Opening and closing tabs

```bash
vim -p first.txt second.txt
:tabedit {file}   # edit specified file in a new tab
:tabfind {file}   # open a new tab with filename given, searching the 'path' to find it
:tabclose         # close current tab
:tabclose {i}     # close i-th tab
:tabonly          # close all other tabs (show only the current tab)
:tab ball         # show each buffer in a tab (up to 'tabpagemax' tabs)
:tab help         # open a new help window in its own tab page
:tab drop {file}  # open {file} in a new tab, or jump to a window/tab containing the file if there is one
:tab split        # copy the current window to a new tab of its own
```

#### Navigation

```bash
:tabs         # list all tabs including their displayed windows
:tabm 0       # move current tab to first
:tabm         # move current tab to last
:tabm {i}     # move current tab to position i+1

:tabn         # go to next tab
:tabp         # go to previous tab
:tabfirst     # go to first tab
:tablast      # go to last tab

# In normal mode
gt            # go to next tab
gT            # go to previous tab
{i}gt         # go to tab in position i
# Using recent vim versions, in normal mode and in insert mode
Ctrl-PgDn     # go to next tab
Ctrl-PgUp     # go to previous tab
```

```
:set number   #→ 设置行号 简写set nu  
:set nonu   #→ 取消行号  
gg  #→ 到第一行  
G   #→ 到最后一行  
nG  #→ 到第n行  
:n  #→ 到第n行  
S   #→ 移至行尾  
0   #→ 移至行尾  
hjkl #→ 前下上后  

w   #→ 到下一个单词的开头  
b   #→ 与w相反  
e   #→ 到下一个单词的结尾。  
ge  #→ 与e相反  

0   #→ 到行头  
^   #→ 到本行的第一个非blank字符  
$   #→ 到行尾  
g_  #→ 到本行最后一个不是blank字符的位置。  
fa  #→ 到下一个为a的字符处，你也可以fs到下一个为s的字符。  
t,  #→ 到逗号前的第一个字符。逗号可以变成其它字符。  
3fa #→ 在当前行查找第三个出现的a。  
F 和 T → 和 f 和 t 一样，只不过是相反方向。  

zz # 将当前行置于屏幕中间（不是转载…）  
zt # 将当前行置于屏幕顶端（不是猪头~）  
zb # 底端啦~ 
```







#### 动词

```
d 表示删除（delete） 
r 表示替换（replace） 
c 表示修改（change）
y 表示复制（yank）
v 表示选取（visual select）
```
#### 名词 （文本对象）

```
w 表示一个单词（word）
s 表示一个句子（sentence）
p 表示一个段落（paragraph）
t 表示一个 HTML 标签（tag）
引号或者各种括号所包含的文本称作一个文本块。
```
#### 介词

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

### HEX编辑
```bash
# Open the file in Vim.
:% ! xxd   # Run the command
:% ! xxd -r # Save
```

Replace

```bash
:1,$ s/old/new #替换全部
```



### Fold折叠

```bash
:help folding  # 查看帮助

:set foldcolumn=N  # N是一个0-12的整数，用于指定显示折叠标识列的宽度，分别用“-”和“+”而表示打开和关闭的折叠
```

| 命令 | 作用                       |
| ---- | -------------------------- |
| `za` | 打开/关闭当前的折叠        |
| `zc` | 关闭当前打开的折叠         |
| `zo` | 打开当前的折叠             |
| `zm` | 关闭所有折叠               |
| `zM` | 关闭所有折叠及其嵌套的折叠 |
| `zr` | 打开所有折叠               |
| `zR` | 打开所有折叠及其嵌套的折叠 |
| `zd` | 删除当前折叠               |
| `zE` | 删除所有折叠               |
| `zj` | 移动至下一个折叠           |
| `zk` | 移动至上一个折叠           |
| `zn` | 禁用折叠                   |
| `zN` | 启用折叠                   |

#### Manual（手工折叠）

```bash
:set foldmethod=manual  # 启用手工折叠
zf  			# 折叠选中的文本
zfa(  		# 折叠括号（比如()、[]、{}、><等）包围的区域
:mkview  	# 保存当前的折叠状态
:loadview # 载入记忆的折叠信息
:help fold-manual  # 查看关于手工折叠的帮助信息
```

#### Indent（缩进折叠）

```bash
:set foldmethod=indent 	# 启用缩进折叠
:set foldlevel=1        # 指定折叠缩进的级别
:help fold-indent       # help
```

#### Marker（标记折叠）

```bash
:set foldmethod=marker   # 启用标记折叠。所有文本将按照特定标记（默认为{{{和}}}）自动折叠。
:help fold-marker
```


#### Syntax（语法折叠

```bash
:set foldmethod=syntax   # 按照语法结构自动折叠
:help fold-syntax
```

## 剪贴板


```bash
reg [register_name]  # 剪贴板的历史 
"+yy    # 复制当前行到剪切板
"+p     # 将剪切板内容粘贴到光标后面
"ayy    # 复制当前行到寄存器 a
"ap     # 将寄存器 a 中的内容粘贴到光标后面

```


## 执行Shell

### :!cmd

`:!{cmd}` 是 Vim 命令，用来执行一条 Shell 命令，命令完成后按任意键回到 Vim。 可通过`:!zsh ls`来指定不同的 Shell。适合只执行一条命令的场景，比如编译、运行测试、查看 IP 等，例如：

```
:!ifconfig en0
:!!
```

`:!!` 为重复执行上一条命令（就像`@@`重复执行上一个宏一样），这在重复地编辑/编译时很方便。

### c-z

`<Ctrl+Z>` 是最基础的 Shell 快捷键，用来立即挂起当前进程（比如当前的 Vim）并进入 Shell。 在完成一系列的命令后，使用`fg`来切换回 Vim。适合暂时离开 Vim 但需要执行多条命令的场景。 例如暂时挂起 Vim 去创建一个新的文件：

```
vim index.html
<Ctrl-Z>
touch index.js
fg
```

如果使用`<Ctrl+Z>`挂起了多个任务怎么办？可以 `fg %1` 来恢复第一个，`fg %2` 来恢复第二个，以此类推。 如果你配置了[oh my zsh](https://github.com/robbyrussell/oh-my-zsh)，在输入 `fg `后按下 `<Tab>` 便会提示当前所有的挂起任务。

> 如果只是操作文件和目录，在 Vim 也可以做到。 详见：[在 Vim 中进行文件目录操作](https://harttle.land/2016/10/14/vim-file-and-directory.html)

### :shell

`:sh[ell]` 是 Vim 命令，可以从 Vim 中运行一个 Shell 出来。在完成一系列的命令后， 按下`Ctrl-D`来结束当前 Shell 并回到 Vim。其行为相当于`<Ctrl-Z>`，但由于是 Vim 主动地启动 Shell 进程， 可以通过 `shell` 选项来设置不同的 Shell：

```
:set shell=\"c:\program\ files\unix\sh.exe\"\ -f
```

在 [StackOverflow上提到](http://stackoverflow.com/questions/1236563/how-do-i-run-a-terminal-inside-of-vim) 可以将 `Ctrl+D` 映射到 `:sh`， 这样可以使用 `Ctrl+D` 来切换 Vim/Shell：

```
:noremap <c-d> :sh<cr>
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

```
xp                       # 交换前后两个字符的位置
ddp                      # 上下两行的位置交换
```

[NERDTree](https://github.com/scrooloose/nerdtree)
>==m 显示文件系统菜单==

```bash 
:w !sudo tee %   # 强行保存文件
```

## vimdiff

### 使用vimdiff作为git mergetool

#### 设置 git mergetool 为 vimdiff

```bash
git config --global merge.tool vimdiff
# git has an option to display merge conflicts in diff3 format (by default it only displays the two files to be merged). You can enable it like so:
git config --global merge.conflictstyle diff3
git config --global mergetool.prompt false
```

#### 用vimdiff解决合并冲突

运行`git mergetool`，vim将展示如下

```
+--------------------------------+
| LOCAL  |     BASE     | REMOTE |
+--------------------------------+
|             MERGED             |
+--------------------------------+
```

![git_vimdiff](./assets/images/git_vimdiff.png)

移动光标到不同的split

```
Ctrl w + h   # move to the split on the left 
Ctrl w + j   # move to the split below
Ctrl w + k   # move to the split on top
Ctrl w + l   # move to the split on the right
```

移动到`MERGED`文件上（Ctrl + w, j）,移动光标到一个合并冲突的区域(`[c` 或`]c`)，然后：

```
:diffg RE  " get from REMOTE
:diffg BA  " get from BASE
:diffg LO  " get from LOCAL
```

最后使用:wqa保存更改并关闭所有的分割

## 源码阅读

### ctags

```bash
# .vimrc  亲测可以不需要设置
set tags=./tags,./TAGS,tags;~,TAGS;~
```

进入代码目录执行

```bash
ctags -R 					# 要阅读的代码目录
```

