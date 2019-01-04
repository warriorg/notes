## 安装
### osx
```bash
brew install redis
brew services start redis
redis-cli 							#进去cli客户端
```
### Centos 
```bash
wget http://download.redis.io/releases/redis-5.0.2.tar.gz
tar zxf redis-5.0.2.tar.gz
make PREFIX=/usr/local/redis install
ln -s /usr/local/redis/bin/redis-cli /usr/local/bin/redis-cli   # 建立软连接
```

解决错误

```bash
make
cd src && make all
make[1]: Entering directory `/usr/local/redis-5.0.2/src'
    CC adlist.o
In file included from adlist.c:34:0:
zmalloc.h:50:31: fatal error: jemalloc/jemalloc.h: No such file or directory
 #include <jemalloc/jemalloc.h>
                               ^
compilation terminated.
make[1]: *** [adlist.o] Error 1
make[1]: Leaving directory `/usr/local/redis-5.0-rc3/src'
make: *** [all] Error 2
```

最后解决方案如下：
```bash
cd deps
make hiredis lua jemalloc linenoise
```


## 命令工具

* redis-server	服务端
* redis-cli    客户端

### redis.conf
```
requirepass 123456  # 开启密码验证
```

### 常用命令
```bash
redis-cli -h host -p port -a password   # 连接指定服务器的数据库
redis-cli -a 123456  # 登录验证

config set requirepass 123456   # 设置密码

auth 123456     # 登录验证

info Keyspace 	#列出数据库
KEYS pattern 	# 列出所有KEYS
keys *       	# 列出所有KEYS

expire key seconds #设置超时时间
ttl key 		# 返回具有超时的键的剩余生存时间。
del key 		# 删除一个key

flushdb     	# 清空当前数据库中的所有 key
flushall    	# 清空所有数据的所有key

dbsize 		 	# 数据库的 key 数量
ping 		 	# 检测 redis 服务是否启动


```


## Redis的数据结构

### 1. STRING
> 可以是字符串、整数或者浮点数          
> 对整个字符串的其中一部分执行操作，对整数和浮点数执行自增(increment)或者自减(decrement)操作
> 
```bash
127.0.0.1:6379> set hello world
OK
127.0.0.1:6379> get hello
"world"
127.0.0.1:6379> del hello
(integer) 1
127.0.0.1:6379> get hello
(nil)
```

应用场景 自增分布式主键生成

推特的雪花算法也可以完成主键生成



### 2. LIST

> 一个链表，链表上的每个节点都包含一个字符串    
> 从链表的两端推入或者弹出元素；根据偏移量对链表进行修剪(trim)；读取单个或者多个元素；根据值查找或者移除元素
>
```bash
#将给定的值推入列表的右端
rpush key value [value ...]
#获取列表在给点范围上的所有值
lrang key start stop
#获取列表在给定位置上的单个元素
lindex key index
#从列表的左端弹出一个值，并返回被弹出的值
lpop key
```



### 3. SET

> 包含字符串的无序收集器，并且被包含的每一个字符串都是独一无二， 各不相同           
> 添加、获取、移除单个元素；检查一个元素是否存在于集合中；计算交集、并集、差集；从集合里面随机获取元素
> 
```bash
#将给定元素添加到集合
sadd key member [member ...]
#返回集合包含的所有元素
smembers  key
# 检查给点的元素是否存在于集合中
sismember key member
#如果给定的元素存在于集合中，那么移除这个元素
srem key member [member ...]
```
### 4. HASH
> 包含键值对的无序散列表            
> 添加、获取、移除单个键值对；获取所有的键值对
> 
```bash
#在散列里面关联起给定的键值对
hset key field value
#获取指定散列键的值
hget key field
#获取散列包含的说有键值对
hgetall key
#如果给定键值存在于散列里面，那么移除这个键
hdel key field [field ...]
```
### 5. ZSET (有序集合)
> 字符串成员与浮点数分值之间的有序映射，元素的排列顺序由分值的大小决定      
> 添加、获取、删除单个元素；根据分值范围(range)或者成员来获取元素
> 
```bash
# 将一个给定分值的成员添加到有序集合里面
zadd key [NX|XX] [CH] [INCR] score member [score member ...]
# 根据元素在有序排列中所处的位置，从有序集合里面获取多个元素
zrange key start stop [WITHSCORES]
# 获取有序集合在给定分值范围内的所有元素
zrangebyscore key min max [WITHSCORES] [LIMIT offset count]
# 如果给定成员存在于有序集合，那么移除这个成员
zrem key member [member ...]
```

### 6. Other 命令
1. expirs. (有效期)

## 事务



## 持久化	

1. 快照 (snapshotting)
2. 追加文件 (append-only file, AOF)



## 集群



# 补充
redis 集群服务器推荐3主3从
