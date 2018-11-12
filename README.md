# [**Andy**](http://lbzh.xyz)

## Build

#### environment
 
Maven 3.5.3+  
Java 11+

#### clone from git

    git clone https://github.com/lbzhello/andy.git
    
#### cd the root directory

    cd andy
    
#### run maven

    mvn assembly:assembly
    
#### cd output directory

    cd target
    
#### run

    java -jar andy-1.0-SNAPSHOT-jar-with-dependencies.jar ../docs/examples/andy.test


## 1. Delimiter, Number, Symbol, String  

#### Delimiter  

There are only the following symbols can be use as delimiter  

    , ; . : ( ) { } [ ] " '
  
#### Number  
 
    123, -12, +12, 3.14

#### Symbol  

All characters except delimiter can be use as symbol  

    var, num, str+, a-b ...

#### String  

Quote by '"' or '`'

    str1 = "this is a string"
    str2 = `this is another string`

## 2. Expression  

In here everything is expression and will return a value  

#### Round bracket expression  

    (f a b)
    
> *f* is name of the expression, other is parameters, just like lisp  

#### Square bracket expression

    [1 2 3]

> also called array

#### Curly bracket expression

    {expr1 expr2 expr3}

> curly bracket expression will return value of last expression as it's result

#### Binary expression   

see next chapter
  
#### Bnary expression  

see next chapter

## 3. Operator

operator is just a kind of round bracket expression  

#### Binary  

*expr1 op expr2*  
just like other scripts  

    a = 3
    b = 4
    c = a + b  //c = 7
    d = b || "nil value"  //d = 4
    ...

e.g.  *=  +  -  *  /  >  <  >=  <=  !=  &&  || -> ...*  

belows are equals  

*expr1 op expr2* => *(op expr1 expr2)*  

    3 + 2 => (+ 3 2)  
    3 + 2 * 5  => (+ 3 (* 2 5))  

#### Unary  

*op expr1 expr2 ...*  

    a = 13
    b = 14
    rst = nil
    if (a < b) {
      rst = "it's true" 
    } else { 
      rst = "it's false"
    }
    print(rst)  //"it's true
    ...

e.g.  *true  false  nil  if  for  return ...*  
   
belows are equals  

*op expr1 expr2 ...* => *(op expr1 expr2 ...)*  
  
*if* expression accepts 2 args  

    if (3 > 2) "it's true" => (if (> 3 2) "it's true") 
    
*else* expression is binary  

    if 3 > 2 "it's true" else "it's false" => (if (> 3 2) (else "it's true" "it's false")   
    
*return* expression accepts 1 args   

    return 2 => (return 2)
  
if the operator isn't a delimiter, and you write it like  

    a=3  

it will be parsed as a symbol like "a=3", so you should write like  

    a = 3

## 4. Function  

Function is a kind of round bracket expression

#### Define  

    f1(x, y){
      x + y
    }
  
    //lambda
    f2(x, y) = {
      x + y
    }
  
    //lambda
    f3 = (x, y){
      x + y
    }
  
    //single function can simplely write like this
    f4(x, y) = x + y
  
    //arrow function
    f5 = (x, y) -> x + y
  
    //use $n as parameters
    f6 = {
      $0 + $1
    }
  
    //function call
    f1(1, 2)

you can also define a function like:  

    (f x y) = x + y  

in lisp style:  

    (= (f x y) (+ x y))  
  
#### Function call

    f(1, 2)
    //or in lisp style
    (f 1 2)
 
#### Function with properties

    f(x, y) = {
      name: "abc"
      x + y
    }
  
    a = f(1, 2)  //a = 3
    b = f.name  //b = "abc"
    f.name = "xyz"
    b = f.name //b = "xyz"

above will be parsed as  

    (= (f x y) {(: name "abc") (+ x y)})

#### Function provide by system  

#### operators

    (+ 1 2), x -> x + 1, ...  
  
#### if

*if expr1 expr2* => *(if expr1 expr2)*

    rst = if 2 > 3 "i'm big"  //rst = nil

  
#### for 

*(for expr1 expr2)* => *(for expr1 expr2)*  
for will return an array

    i = 0
    rst = for i < 5 {
      i = i + 1
      i
    }
    //rst = [2 3 4]

  
#### return 

*return expr1* => *(return expr1)*

    a = 2
    if a > 0 return 0  //rst = 0

#### other

    ....


## 5. Array  

#### Introduce  

    arr1 = [1 2 3 4 5]
    arr2 = ["hello" "world" 1 2 3]

different of bracket expression  

* round: (expr1 expr2 expr3 ...) t expr1(expr2, expr3 ...)  
圆括号表达式是一个算子，计算单元，如果用逗号隔开会计算每个表达式的值然后返回一个array，例：  
(expr1, expr2, expr3, ...) => \[(expr1) (expr2) (expr3)]

* square: \[expr1 expr2 expr3] => \[expr1 expr2 expr3]  
方括号不会计算表达式的值，直接当作字面量返回  

* curly: {expr1 expr2 expr3} => (expr3)  
花括号会计算每个表达式的值，然后将最后一个表达式的值作为结果返回  
  
例如：  

    a = 1 b = 2
    rst1 = (1, 2, a + b)  //rst1 = [1 2 3]
    rst2 = [1 2 a + b]    //rst2 = [1 2 a + b]
    rst3 = {1 2 a + b}    //rst3 = 3

#### Method on array  

> arr = \[1 2 3 4 5]  

*    first() other() count() reverse()

    arr.first()   //1
    arr.other()   //[2 3 4 5]
    arr.count()   //5
    arr.reverse() //[5 4 3 2 1]
  
  
*    map(expr)  

    arr.map(x -> x + 1)  //[2 3 4 5 6]


*    filter(expr)  
  
    arr.filter(x -> x > 2)  //[3 4 5]

  
*    each(expr)  

    arr.each(print x)  //nil  console: 1 2 3 4 5

  
*    reduce(expr)  

    arr.reduce((x, y) -> x + y)  //15

  
*    mapValues(expr)  

    arr2 = arr.map(x -> (x, 1))  //arr2 = [[1 1] [2 1] [3 1] [4 1] [5 1]]
    arr.mapValues(x -> x + 1)    //arr2 = [[1, 2] [2 2] [3 2] [4 2] [5 2]]

  
*    reduceByKey(expr)  

    arr3 = [12 12 14 15 15]
    arr4 = arr3.map(x -> (x, 1)) //arr4 = [[12 1] [12 1] [14 1] [15 1] [15 1]]
    arr5 = arr4.reduceByKey((x,y) -> x + y) //arr5 = [[12 2] [14 1] [15 2]]

  
*    groupBy(expr)
  
根据expr表达式的值对元素进行分组

    arr.groupBy(x -> if x > 2 "bigger" else "smaller") //[["smaller" 1 2] ["bigger" 3 4 5]]

  
*    groupByKey()  

    arr4.groupByKey()  //[12 1 1] [14 1] [15 1 1]]

  
*    word count  

> words = \["abc" "rng" "xyz" "ig" "ig" "rng" "ig"]

    words.map(x -> (x, 1)).reduceByKey((x, y) -> x + y).map(x -> x.reverse())
    //[[1 "abc"] [2 "rng"] [1 "xyz"] [3 "ig"]]

  
## 6. Simple Xml

#### Grammar  

xml中只有括号表达式会被计算
> <(expr)/(expr)> matching  
> \<(expr)>(expr)</(expr)>  
> \<(expr) />

    a = 3 b = 4
    tag = "name"
    xml = {
        <book>
          <(tag)>inception</(tag)>
          <prise>(a + b)</prise>
        </book>
    }

## 7. Template

#### Grammar

    greeting = "hello world"
    tmpl = `this is kangkang (greeting)`
