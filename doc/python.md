# syntax

## IPython

### 内省

* **?** 在一个变量名的前后使用问号(?)， 可以显示一些关于该对象的概要信息。
* **??**  在一个变量名的前后使用双问号(??)，可以显示源码



## 参考

https://docs.python.org/3/tutorial/



# NumPy

NumPy (Numerical Python)，目前Python数值计算中最为重要的基础包。

## ndarray

python中一个快速、灵活的大型数据集容器。数组允许你使用类似于标量的操作语法在整块数据上进行数学计算

一个ndarray是一个通用的多维同类数据容器，也就是说，它包含的每一个元素均为相同类型。每一个数组都有一个`shape`属性，用来表征数组每一个维度的数量；每一个数组都有一个`dtype`属性,用来描述数组的数据类型。任何在两个等尺寸数组之间的算术操作都应用了逐元素操作的方式。



### 数组算术

数组之所以重要是因为它允许进行批量操作而无须任何for循环。NumPy用户称这种特性为向量化。

### 索引与切片

数组的切片是原始数组的视图。这一点不同于python内建的列表

### 布尔索引

### 神奇索引

用于描述使用整数数组进行数据索引。

### 数组转置与换轴

转置是一种特殊的数据重组形式，可以返回底层数据的视图而不需要复制任何内容。

## 通用函数：快速的逐元素数组函数

### 一元通用函数

| 函数名                    | 描述                                              |
| ------------------------- | ------------------------------------------------- |
| `abs, fabs`               | 计算整数、浮点数或负数的绝对值                    |
| `sqrt`                    | 平方根 等同于 `arr**0.5`                          |
| `square`                  | 计算平方`arr**2`                                  |
| `exp`                     | 自然指数值 $e^x$                                  |
| `log、log10、log2、log1p` | 自然对数 (e为底)、对数10为底，对数2位底、log(1+x) |
| `sign`                    | 计算每个元素的符号之1（正数）,0 , -1(负数)        |
| `ceil`                    | 计算每个元素的最高整数值                          |
| `floor`                   | 计算每个元素的最小整数值                          |

## 二元通用函数

## 线性代数



# pandas

## 数据结构

### Series

Series 是一种一维的数组对象，它包含了一个值序列，并且包含了数据标签，成为索引。

### DataFrame

DataFrame表示的是矩阵的数据表，它包含已拍序的列集合，每一列可以是不同的值类型(数值、字符串、布尔值等)

## 基本功能

### 重建索引

Series调用reindex方法时，会将数据按照新的索引进行排列，如果某个索引值之前并不存在，则会引入缺失值

### 轴上删除条目

```python
data = pd.DataFrame(np.arange(16).reshape((4,4)), 
                    index=['Ohio', 'Colorado', 'Utah', 'New York'], 
                    columns=['one', 'two', 'three', 'four'])
data.drop(['Ohio', 'Utah'])
# 通过axis=1或axis='columns'来从列中删除值
data.drop('two', axis=1)
data.drop(['two', 'four'], axis='columns')
```

### 索引、选择与过滤

### 整数索引

### 算术与数据对齐

### 函数应用和映射

### 排序和排名

### 含有重复标签的轴索引



## 描述性统计的概述与计算

### 相关性和协方差

### 唯一值、计数和成员属性



## 数据载入、存储及文件格式



## 数据清洗与准备

### 处理缺失值

### 数据转换

## 数据规整：连接、联合与重塑

### 分层索引



## 绘图与可视化






# tools
## virtualenv

### install

```base 
python3 -m venv 	#创建虚拟环境
source venvname/bin/activate  #激活虚拟环境
deactivate #退出虚拟环境

```

### linux install package
```bash
python3.6 -m pip install
```

### requirements.txt
```bash
pip freeze > requirements.txt   	#生成requirements.txt
pip install -r requirements.txt		#安装依赖包
```

### setup.py

```python

```

