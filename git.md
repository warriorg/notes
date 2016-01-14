导出不带版本的代码

```bash
git archive master | bzip2 >source-tree.tar.bz2
#ZIP archive:
git archive --format zip --output /full/path/to/zipfile.zip master 
#tag
git archive v1.0 gzip > source-tree.tgz 
```

迁出远程分支

>```bash
>git checkout -b <branch-name> <origin/branch_name>
>```

提交tag到服务器

>```bash
>git push --tags  
>```
>*push a single tag*
>
>```
>git push origin <tag_name> 
>```

git如何删除本地所有未提交的更改（都可以使用
>```bash
>git reset
>git checkout .
>git clean -fdx
>```	
>```bash
> git checkout -- . #这条命令不会删除新增的文件
> git checkout -f #这条命令不会删除新增的文件
> git stash #新加的文件还在，但所有的修改都会抹去
> git add . && git stash && git stash drop #至少不会影响 .gitignore 里面的不跟踪的文件
>```

修改最后一次提交的注释
>```bas
git commit --amend 
```

取消对文件的修改。还原到最近的版本，废弃本地做的修改
>```bash 
>git checkout -- <file>
>```

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
>```bash
git revert HEAD
```