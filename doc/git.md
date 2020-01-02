

## 配置

```bash
git config --global user.name 'your name'
git config --global user.email 'your email'
```

### config 作用域

* `--config`  项目级
* `--global` 用户级
* `--system` 系统级

```bash
git config --list 				# 查看配置
```



## 常用命令

### clone

```bash
# 支持的所有协议
git clone http[s]://example.com/path/to/repo.git/
git clone ssh://example.com/path/to/repo.git/
git clone git://example.com/path/to/repo.git/
git clone /opt/git/project.git 
git clone file:///opt/git/project.git
git clone ftp[s]://example.com/path/to/repo.git/
git clone rsync://example.com/path/to/repo.git/
```

### add/delete

```bash
# 添加指定文件到暂存区
$ git add [file1] [file2] ...

# 添加指定目录到暂存区，包括子目录
$ git add [dir]

# 添加当前目录的所有文件到暂存区
$ git add .

# 添加每个变化前，都会要求确认
# 对于同一个文件的多处变化，可以实现分次提交
$ git add -p

# 删除工作区文件，并且将这次删除放入暂存区
$ git rm [file1] [file2] ...

# 停止追踪指定文件，但该文件会保留在工作区
$ git rm --cached [file]

# 改名文件，并且将这个改名放入暂存区
$ git mv [file-original] [file-renamed]
```

### commit

```bash
# 提交暂存区到仓库区
$ git commit -m [message]

# 提交暂存区的指定文件到仓库区
$ git commit [file1] [file2] ... -m [message]

# 提交工作区自上次commit之后的变化，直接到仓库区
$ git commit -a

# 提交时显示所有diff信息
$ git commit -v

# 使用一次新的commit，替代上一次提交
# 如果代码没有任何新变化，则用来改写上一次commit的提交信息
$ git commit --amend -m [message]

# 重做上一次commit，并包括指定文件的新变化
$ git commit --amend [file1] [file2] ...
```

### remote

远程主机自动被Git命名为`origin`。如果想用其他的主机名，需要用`git clone`命令的`-o`选项指定。

```bash
git remote -v  #查看远程主机的网址。
git remote show <主机名>  #查看该主机的详细信息
git remote add <主机名> <网址> #添加远程主机
git remote rm <主机名>  #删除远程主机
git remote rename <原主机名> <新主机名> #修改远程主机名
```

### fetch
通常用来查看其他人的进程，因为它取回的代码对你本地的开发代码没有影响。默认情况下，取回所有分支（branch）的更新。
```bash
git fetch <远程主机名> # 将远程主机的更新，全部取回本地
git fetch <远程主机名> <分支名> # 取回特定分支的更新
git fetch origin master #取回origin主机的master分支

```

### pull
取回远程主机某个分支的更新，再与本地的指定分支合并
```bash
# 如果远程分支是与当前分支合并，则冒号后面的部分可以省略
git pull <远程主机名> <远程分支名>:<本地分支名>
```

### push
```bash
git push <远程主机名> <本地分支名>:<远程分支名>
git push --force origin # 用本地版本覆盖远程主机的更新
git push origin --tags  # 推送标签
git push --tags 	# 推送标签
git push origin <tag_name>  # 推送一个tag
```

### checkout
```bash
# 查看指定文件的历史版本
git log <filename>
# 回滚到指定commitID
git checkout <commitID> <filename>
```

### branch
* **-r** 查看远程分支

* **-a** 查看所有分支

  
```bash
# 删除远程分支
git branch --delete --remotes <remote>/<branch>	
git branch -dr <remote>/<branch> # Shorter

git push origin test:test  #提交本地test分支作为远程的test分支
```





### Submodule		

```bash
git submodule add 
git submodule foreach git pull  			# 更新所有submodule

git submodule update --init --recursive	 	# 下载子模块
```
### revert
撤销某次操作，此次操作之前和之后的 commit 和 history 都会保留，并且把这次撤销作为一次最新的提交。git revert是提交一个新的版本，将需要revert的版本的内容再反向修改回去，版本会递增，不影响之前提交的内容。

```bash
# 删除最后一次提交
git revert HEAD			
git push origin master

git revert commitID  # 回滚到commitID
```



### reset

撤销某次提交，但是此次之后的修改都会被退回到暂存区。除了默认的 mixed 模式，还有 soft 和 hard 模式

```bash
# 删除最后一次提交
git reset --hard HEAD^
git push origin master -f
```



### Stash

**Stash**可以获取你工作目录的中间状态——也就是你修改过的被追踪的文件和暂存的变更——并将它保存到一个未完结变更的堆栈中，随时可以重新应

```bash
# 从储藏中创建分支
git stash branch testchanges
```



### log

```bash
git log -L start,end:file   # 使用 log 来查看某一行的所有操作
```



### blame

- 方便的 blame 一行代码是谁改的
- blame 一行代码的改动历史（可能有多个人改过多次，有时候只看最后的改动不够）

```bash
git blame <filename>
```



### archive 

```bash
# 导出不带版本的代码
git archive master | bzip2 >source-tree.tar.bz2
#ZIP archive:
git archive --format zip --output /full/path/to/zipfile.zip master
#tag
git archive v1.0 gzip > source-tree.tgz
# -------------------
```








git 切换远程代码库
```bash
git remote rm origin #方法1
git remote set-url origin URL  #方法2
.git/config	#方法3
```

创建库
```bash
touch README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin https://github.com/warriorg/Estates.git
git push -u origin master
```

修改git仓库地址
```bash
git remote set-url origin http://ip:port/jinyi/bole-parking.git
```

git 分支
```bash
git checkout -b iss53

```

```
关闭分支
​```bash
git checkout master
git merge <branch-name>
git branch -d <branch-name>
```

删除不在git管理下的文件
```bash
git clean -nd #测试删除
git clean -fd #真实删除 
```



git如何删除本地所有未提交的更改（都可以使用
```bash
git reset
git checkout .
git clean -fdx
```
```bash
git checkout -- . #这条命令不会删除新增的文件
git checkout -f #这条命令不会删除新增的文件
git stash #新加的文件还在，但所有的修改都会抹去
git add . && git stash && git stash drop #至少不会影响 .gitignore 里面的不跟踪的文件
```

修改最后一次提交的注释
```bas
git commit --amend
```

取消对文件的修改。还原到最近的版本，废弃本地做的修改
```bash
git checkout -- <file>
```

解决冲突

```bash
git mergetool -t diffmerge .
```



>安装diffmerge `osx`==brew cask install diffmerge==

取消已经暂存的文件。即，撤销先前"git add"的操作
`git reset HEAD <file>...`

回退所有内容到上一个版本
`git reset HEAD^`

回退a.py这个文件的版本到上一个版本  
`git reset HEAD^ a.py  `

向前回退到第3个版本  
`git reset –soft HEAD~3  `

将本地的状态回退到和远程的一样  
`git reset –hard origin/master `

回退到某个版本  
`git reset 057d `



回退到上一次提交的状态，按照某一次的commit完全反向的进行一次commit.(代码回滚到上个版本，并提交git)
```bash
git revert HEAD
```

#### 查看信息
```bash
git status  # 查看当前工作区状态(与暂存区对比，增加删除或修改)
git log  # 显示当前分支的版本历史
git log --stat  # 显示commit历史，以及每次commit发生变更的文件
git log -S [keyword]  # 根据关键字搜索提交历史
git log [tag] HEAD --pretty=format:%s  # 显示某个commit之后的变动，每个commit占据一行。我记得--pretty=online也行
git log -p [file]  # 显示指定文件相关的每一个diff
git log -5 --pretty --oneline  # 显示过去5次提交
git shortlog -sn  # 显示所有提交过的用户，按提交次数排序
git blame [file]  # 显示指定文件是什么人在什么时间修改过，这个blame很生动形象
git diff  # 显示暂存区和工作区的差异
git diff --cached [file]  # 显示暂存区和上一个commit的差异
git diff HEAD  # 显示工作区与当前分支最新commit之间的差异
git diff [first-branch]...[second-branch]  # 显示两次提交之间的差异
git diff --shortstat "@{0 day agp}"  # 显示今天你写了多少航代码
git show [commit]  # 显示某次提交的元数据和内容变化
git show --name-only [commit]  # 显示某次提交发生变化的文件
git show [commit]:[filename]  # 显示某次提交时，某个文件的内容
git reflog  # 显示当前分支的最近几次提交
```

#### 撤销
```bash
git reset --hrad HEAD  # 撤销工作目录中所有未提交文件的修改内容
git checkout HEAD <file>  # 撤销指定的未提交文件的修改内容
git revert <commit>  # 撤销指定的提交
git log --before="1 days"   # 退回到之前1天的版本
git checkout [file]  # 恢复暂存区的指定文件到工作区
git checkout [commit] [file]  # 恢复某个commit的指定文件到暂存区和工作区
git checkout .  # 恢复暂存区的所有文件到工作区
git reset [file]  # 重置暂存区的指定文件，与上一次commit保持一致，但工作区不变
git reset --hard  # 重置暂存区与工作区，与上一次commit保持一致
git reset [commit]  # 重置当前分支的指针未指定commit，同时重置暂存区，但工作区不变
git reset --hard [commit]  # 重置当前分支的HEAD未指定commit，同时重置暂存区和工作区，与指定commit一致
git reset --keep [commit]  # 重置当前HEAD未指定commit，但保持暂存区和工作区不变
```

解决git目录过大

```base
git gc --prune=now  #运行 gc ，生成 pack 文件 --prune=now 表示对之前的所有提交做修剪，有的时候仅仅 gc 一下.git 文件就会小很多
git verify-pack -v .git/objects/pack/*.idx | sort -k 3 -n | tail -3    #找出最大的三个文件 
git rev-list --objects --all | grep c43a8da		#查看那些大文件究竟是谁（c43a8da 是上面大文件的hash码）
git filter-branch --force --index-filter "git rm --cached --ignore-unmatch 'data/bigfile'"  --prune-empty --tag-name-filter cat -- --all  #移除对该文件的引用（也就是 data/bigfile）

#进行 repack 
git for-each-ref --format='delete %(refname)' refs/original | git update-ref --stdin
git reflog expire --expire=now --all
git gc --prune=now

git count-objects -v   #查看 pack 的空间使用情况
```

方法二2

```bash
git rev-list --objects --all | grep "$(git verify-pack -v .git/objects/pack/*.idx | sort -k 3 -n | tail -5 | awk '{print$1}')" > large-files.txt
cat large-files.txt| awk '{print $2}' | tr '\n' ' '  >  large-files-inline.txt
git filter-branch -f --prune-empty --index-filter "git rm -rf --cached --ignore-unmatch `cat large-files-inline.txt`" --tag-name-filter cat -- --all
git push origin --force --all
```



## 问题

### fatal: refusing to merge unrelated histories
```bash
git pull origin master --allow-unrelated-histories 
```

### git remote: HTTP Basic: Access denied

```bash
git config --system --unset credential.helper
```
### error: You have not concluded your merge (MERGE_HEAD exists)

```bash
git merge --abort
git reset --merge
git pull
```


### 解决每次都要输入密码

```bash
git config --global credential.helper store
```

### 忽略对已入库文件的修改

```bash
git update-index --assume-unchanged FILENAME			# 忽略对如果文件的修改
git update-index --no-assume-unchanged FILENAME		# 取消对入库文件的修改
```

### 删除 .DS_Store

```bash
find . -name .DS_Store -print0 | xargs -0 git rm -f --ignore-unmatc
```



## GitHub

### [帮助](https://help.github.com/)



### 搜索技巧

[search syntax](https://help.github.com/en/articles/understanding-the-search-syntax#query-for-values-greater-or-less-than-another-value)

## GitLabel

