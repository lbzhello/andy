# [**Andy**](http://lius.xyz/andy)

## 运行

1. Git 下载版本库
```sh
git clone https://github.com/lbzhello/andy.git
```

2. Idea 或 Eclipse 导入 Maven 项目（打开 andy/pom.xml 文件）

3. 运行 main 下面的 xyz.lius.andy.Main.java 或者 test 下面的 xyz.lius.andy.MainTest.java 

## 语法

语法类似于 js, python, lisp

### Variable

**let** 声明一个变量

```js
    // Number
    let a = 2
    let b = 3
    print(a + b) // 5

    // ` 或 " 表示字符串
    let str1 = "this is a string"
    let str2 = `this is another string`

    // () 可以用于字符串插值
    let msg = "word"
    let str3 = `hello (msg)!` // hello world

    // true 表示肯定， false 表示否定, nil 表示零值
    let t = true
    let f = false
    let n = nil
```

### Function

**def** 定义一个函数

```js
// 定义
def sum(x, y) {
    // return 可以省略
    return x + y
}

// 单行函数可以省略 {}
def sum(x, y) x + y

// 无参函数可以省略 ()
def foo print("hello world")

// 函数作为值，调用时需要加括号，如 sum(2, 3)
let foo = sum
foo(2, 3) // 5

// 函数字面量，直接作为函数变量
let sum = (x, y){
    x + y
}

// 参数前置
let sum(x, y) = {
    x + y
}

// 同样可以省略括号
let sum(x, y) = x + y

// lambda
let sum = (x, y) -> x + y

// 函数调用
sum(2, 3) // 5

// lisp 风格
(sum 2 3) // 5

// 函数可以拥有属性和方法（也是函数）
def foo(x, y) {
    name: "foo"
    age: 0

    age = x + y

    // 方法
    def getAge() {
        return age
    }

    return `i am (name)`
}

let rst = foo(2, 3) // rst = i am foo
print(foo.getAge()) // 5

// 函数的名字可以是字符串, 即 json 格式 
def book(x, y) {
    "name": "Understanding Mysql"
    "price": 99
    "date": "2019-09-27"
    "author": {
        "name": "Bspzvt"
        "from": "American"
    }
}

print(book."name") // Understanding Mysql
```

**系统提供了一些常用函数**

```js
// print 打印字符
print("hello world")

// import 导入脚本文件
// 定义一个脚本文件 foo.andy, 其内容如下
def bar {
    print("hello world")
}

// 在同一个文件夹的另一个文件中
// 暂不支持 path
let foo = imoprt foo
foo.bar()

// new 创建一个 java 对象
str = new java.lang.StringBuffer
str.append("hello")
str.append(" ")
str.append("world")
(print str)  //"hello world"
```

## Array  

```js  
let arr1 = [1 2 3 4 5]
let arr2 = ["hello" "world" 1 2 3]
```

#### 数组方法

```js
let arr = [1 2 3 4 5]  
```

* first() other() count() reverse()

```js
arr.first()   //1
arr.other()   //[2 3 4 5]
arr.count()   //5
arr.reverse() //[5 4 3 2 1]
```

* map(expr)  

```js
arr.map(x -> x + 1)  //[2 3 4 5 6]
```

* filter(expr)  

```js
arr.filter(x -> x > 2)  //[3 4 5]
```

* each(expr)  

```js
arr.each(print x)  //nil  console: 1 2 3 4 5
```

* reduce(expr)  

```js
arr.reduce((x, y) -> x + y)  //15
```

* mapValues(expr)  

```js
arr2 = arr.map(x -> (x, 1))  //arr2 = [[1 1] [2 1] [3 1] [4 1] [5 1]]
arr.mapValues(x -> x + 1)    //arr2 = [[1 2] [2 2] [3 2] [4 2] [5 2]]
```

* reduceByKey(expr)  

```js
arr3 = [12 12 14 15 15]
arr4 = arr3.map(x -> (x, 1)) //arr4 = [[12 1] [12 1] [14 1] [15 1] [15 1]]
arr5 = arr4.reduceByKey((x, y) -> x + y) //arr5 = [[12 2] [14 1] [15 2]]
```

* groupBy(expr)

```js
arr.groupBy(x -> if x > 2 "bigger" else "smaller") //[["smaller" 1 2] ["bigger" 3 4 5]]
```

* groupByKey()  

```js
arr4.groupByKey()  //[12 1 1] [14 1] [15 1 1]]
```

* word count  

```js
words = ["abc" "rng" "xyz" "ig" "ig" "rng" "ig"]
words.map(x -> (x, 1)).reduceByKey((x, y) -> x + y).map(x -> x.reverse())
//[[1 "abc"] [2 "rng"] [1 "xyz"] [3 "ig"]]
```

### 流程控制

```js
let a = 5

if a > 0 {
    print("a > 0")
} else {
    print("a <= 0")
}

// if 作为表达式
let rst = if (a > 0) {
    return a
} else {
    return 0
}

// 括号可以省略
let rst = if a > 0 a else 0

// else if
if a > 0 {
    a
} else if a > 1 {
    1
} else {
    0
}

// 写在一行, 貌似不那么清晰
let rst = if a > 0 a else if a > 1 1 else 0
```

### 循环

for 以后可能改成 while, for 作为 for-in 语句

```js
let count = 10

for count > 0 {
    print(count)
    // 还不支持 count -= 1 或 count--
    count = count - 1
}

// for 返回一个数组
// rst = [9 8 7 6 5 4 3 2 1 0]
let rst = for count > 0 {
    count = count - 1
    return count
}

```

  
## Xml  

xml 中只有括号表达式会被计算, 其格式为：

<(expr)/(expr)>  
\<(expr)>(expr)</(expr)>  
\<(expr) />  

```js
a = 3 b = 4
tag = "name"
xml = {
    <book>
        <(tag)>inception</(tag)>
        <prise>(a + b)</prise>
    </book>
}
```

## Template

```js
greeting = "hello world"
tmpl = `this is kangkang (greeting)`  //"this is kangkang hello world"
```

#### Using '|' as delimiter

```js
tmpl = `
    first line
    second line
` 
//    first line
//    second line
    
tmpl = `
    |first line
    |second line
`

//first line
//second line
```

#### Using '\\' as escaped char
```js
greet = "abc"
str = `hello \(greet)`  //str = "hello (greet)"
```