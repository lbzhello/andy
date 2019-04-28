# [**Andy**](http://lius.xyz/andy)

## Build

#### Environment
 
Maven 3.5.3+  
Java 11+

#### Step

    //clone from git
    $ git clone https://github.com/lbzhello/andy.git
    
    //cd the root directory
    $ cd andy
    
    //run maven
    $ mvn assembly:assembly
    
    //cd output directory
    $ cd target
    
    //run
    $ java -jar andy-1.0-SNAPSHOT-jar-with-dependencies.jar ../docs/examples/andy.test


## Examples

#### Keyword
    
    nil, true, false, new, return, if, else, for, import, print
    
    a = 1
    b = 5
    a > b //false
    a > b || b > a  //true
    rst = nil || 100 //rst = 100
    
    //if
    rst = if (a > b) {
        return "a is bigger than b"
    } else {
        return "a is smaller than b"
    }
    print(rst) //"a is smaller than b"
    
    //for
    acc = 0
    i = 0
    rst = for (i < 10) {
        acc = acc + i
        i = i + 1
        print(acc)
        acc 
    }
    rst //[0 1 3 6 10 15 21 28 36 45]
    
    //new
    str = new java.lang.StringBuffer
    str.append("hello")
    str.append(" ")
    str.append("world")
    (print str)  //"hello world"
    
#### Operator

    =, ||, &&, >, <, ==, >=, <=, +, -, *, /, .
    
#### Function
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
  
    // single line
    f4(x, y) = x + y
  
    // ->
    f5 = (x, y) -> x + y
  
    //use $n as parameters
    f6 = {
      $0 + $1
    }
    f6(2, 5) //7
 
 #### Object
    book = {
      unit: "$"
      price: 22
      
      getPrice(){
        return price + unit
      }
    }
  
    book.getPrice()  //"22$"
    book.unit = "￥"
    book.price = 23
    book.getPrice() //"23￥"
    
#### Json
    book = {
        "name": "abc"
        "price": 22
        "info": {
            "author": "andy"
            "time": 10
        }
    }
    
    book."name" //"abc"
    book."info"."author" //"andy"
    p = "price"
    book.(p) //22

#### Array     

    arr = [1 2 3 4 5]

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
    arr2.mapValues(x -> x + 1)    //arr2 = [[1 2] [2 2] [3 2] [4 2] [5 2]]

  
*    reduceByKey(expr)  

    arr3 = [12 12 14 15 15]
    arr4 = arr3.map(x -> (x, 1)) //arr4 = [[12 1] [12 1] [14 1] [15 1] [15 1]]
    arr5 = arr4.reduceByKey((x,y) -> x + y) //arr5 = [[12 2] [14 1] [15 2]]

  
*    groupBy(expr)
  
    arr.groupBy(x -> if (x > 2) "bigger" else "smaller") //[["smaller" 1 2] ["bigger" 3 4 5]]

  
*    groupByKey()  

    arr4 = [[12 1] [12 1] [14 1] [15 1] [15 1]]
    arr4.groupByKey()  //[12 1 1] [14 1] [15 1 1]]

  
*    word count  

    words = ["abc" "rng" "xyz" "ig" "ig" "rng" "ig"]
    words.map(x -> (x, 1)).reduceByKey((x, y) -> x + y).map(x -> x.reverse())
    //[[1 "abc"] [2 "rng"] [1 "xyz"] [3 "ig"]]


#### Xml
> 括号表达式用于插值

    xml(tag, prise) = {
        <book>
          <(tag)>inception</(tag)>
          <price>(value</price>
        </book>
    }
    
    rst = xml("name", 7)
    //rst 
    <book>
      <name>inception</name>
      <prise>7</prise>
    </book>
    

## Template
> 括号表达式用于插值

    greeting = "hello world"
    tmpl = `this is kangkang (greeting)`  //tmpl = "this is kangkang hello world"

    //using '|' as delimiter
    tmpl = `
        first line
        second line
    `
    //rst = 
        first line
        second line
        
     tmpl = `
         |first line
         |second line
         
     //rst = 
     first line
     second line
     
     //using '\\' as escaped char
     greet = "abc"
     str = `hello \(greet)`  //str = "hello (greet)"