package com.github.raedev.kotlin.grammar

import org.junit.Test
import java.util.*

/**
 * =========================
 * 第二章：类和对象
 * 下一章：[KtInterface] 接口
 * 上一章：[Grammar] 基础语法
 * =========================
 * 类的修饰符：
 * abstract    // 抽象类
 * final       // 类不可继承，默认属性
 * enum        // 枚举类
 * open        // 类可继承，类默认是final的，注意：open替代了Java的public修饰
 * annotation  // 注解类
 * -------------------------------
 * 访问修饰符：
 * private     // 仅在同一个文件中可见
 * protected   // 同一个文件中或子类可见
 * public      // 所有调用的地方都可见
 * internal    // 同一个模块中可见
 *
 * ==================================================
 * 类的声明
 * ==================================================
 */
// 定义一个空的类
class Empty

// 快速定义一个类
class Token(var accessToken: String, var refreshToken: String)

/**
 * 类和对象
 * Kotlin 类可以包含：构造函数和初始化代码块、函数、属性、内部类、对象声明。
 * @author RAE
 * @date 2022/06/01
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
class KtClass {

    @Test
    fun testClass() {
        val china = KtPerson("张", "三") // 实例化，Java写法：new KtPerson()
        println(china.fullName)
        china.hair = "白头发"
        println(china.name)
        china.englishName = "raedev"
        println(china.englishName)
    }
}

/**
 * ==================================================
 * 类的构造函数、属性、方法声明
 * 分为主构造函数和可定义多个次构造函数
 * ==================================================
 */

/*
* 人类
* 这里的构造器constructor：如果主构造器没有任何注解，也没有任何可见度修饰符，那么constructor关键字可以省略。
* 构造函数的参数定义后，可以直接在类内部使用，可以带访问修饰符，也可以设置为变量或者常量。
* Kotlin 中类不能有字段
* */
class KtPerson constructor(private val firstName: String, private val lastName: String) {

    init {
        // 主构造函数初始化代码
        val myName = firstName + lastName
        print(myName)
    }

    // 次构造函数，其实理解为Java的构造函数重载比较好理解
    // 这里Java写法为：
    // KtPerson(String firstName, String lastName, Int age){
    //   this(firstName, lastName);
    //   print(age);
    // }
    constructor(firstName: String, lastName: String, age: Int) : this(firstName, lastName) {
        print(age)
    }

    // 不设置访问修饰符默认是public
    var skin: String = "黄种人"
    var name: String = "张三"
    private var age: Int = 18
    val fullName = firstName + lastName

    // GET/SET 属性
    var englishName: String = "rae"
        get() {
            // 英文文字大写处理
            return name + "的英文名为：" + field.uppercase(Locale.getDefault())
        }

    var hair: String = "黑色" // 默认值
        set(value) { // 关键字field为当前属性
            field = value
            // 这里显示属性设置的更改name字段值
            // SET 方法相当多了一层回调处理
            // 应用场景：可以对数据进行处理，比如：非法字符、范围限定、过滤、联动更新
            name = name + "_" + value
        }


    var money = 0
        private set // 只能类内部设置


    // 延迟初始化，属性在构造的时候可以不赋默认值，在程序运行时操作
    lateinit var body: String

}

/**
 * ==================================================
 * 抽象类
 * ==================================================
 */

// 水果抽象类
abstract class Fruit {

    // 抽象属性
    abstract var name: String;

    // 抽象方法
    abstract fun plant()
}

// 构造函数重写抽象属性
open class Apple(override var name: String) : Fruit() {

    override fun plant() {
        print("Apple plant function.")
    }

}

open class Banana : Fruit() {
    override var name: String = "Banana"
    override fun plant() {
        print("Banana plant function.")
    }
}

/**
 * ==================================================
 * 内部类
 * 内部类，用来解决嵌套类无法访问外部类
 * class Parent{ class Child{ ...这里无法访问Parent... }}
 * ==================================================
 */
class World {
    val name: String = "世界";

    inner class China : ISay {
        private val name: String = "中国"

        override fun say() {
            val parent = this@World
            println("${name}是${parent.name}的，我爱你$name")
        }
    }

    fun say(iSay: ISay) {
        iSay.say()
    }

}

class InnerTest {

    @Test
    fun testInner() {
        // 调用内部类
        World().China().say()
    }
}

interface ISay {
    fun say() {
        println("ISay接口默认的say实现")
    }
}

/**
 * ==================================================
 * 匿名内部类
 * ==================================================
 */
class InnerTest2() {
    @Test
    fun testInner() {
        val w = World()
        // 匿名
        w.say(object : ISay {
            override fun say() {
                println("匿名表达对${w.name}的爱")
            }
        })
    }
}

/**
 * ==================================================
 * 类的继承
 * Kotlin 中所有类都继承该 Any 类，它是所有类的超类。它不是java.lang.Object
 * 父类，表示类可以继承需要使用open修饰，默认是final
 * ==================================================
 */
open class Base(val param: String) {
    var name = "我是爸爸"
        protected set

    // 属性重写
    open var value: String = "";

    fun fix() {
        // 这是一个不能重写的方法
    }

    open fun say() {
        println("我是父类的say方法")
    }

    // 函数可以重写必须使用open修饰
    open fun hello() {
        println("我是父类的方法")
    }

}

// 子类
// 在构造函数中必须初始化父类
class SubClass(param: String, var age: Int) : Base(param), ISay {
    init {
        name = "我是儿子"
    }

    // 属性重写
    override var value: String
        get() = super.value
        set(value) {}


    // 当父类有相同的方法时，可以选择性调用父类的实现
    override fun say() {
        super<Base>.say() // 调用Base的实现
        super<ISay>.say(); // 调用ISay接口的默认实现
    }
}

// 当没有主构造函数时，次构造函数都必须要初始化父类主构造函数
class SubClass2 : Base {
    constructor(param: String) : super(param) {
    }

    constructor(param: String, age: Int) : super(param) {
    }

    override fun hello() {
        println("我是子类的方法")
        super.hello()
    }

}

class BaseTest {
    @Test
    fun testMain() {
        val sub = SubClass("abc", 12)
        println("param = ${sub.param}; age = ${sub.age}; name = ${sub.name}")
        sub.say()

        SubClass2("abc", 12).hello()
    }
}

/**
 * ==================================================
 * 伴生对象
 * 伴生对象，对应Java的静态内部类 ClassA{ public static class Companion {...} }
 * ==================================================
 */
class Tank {
    // 这里的 YourName 是可以随便定义的，其实作用不大，一般可以省略
    companion object YourName {
        fun show() {
            println("tank show.")
        }
    }
}

class TankTest {

    @Test
    fun main() {
        Tank.show()
    }
}

/**
 * ==================================================
 * 对象表达式
 * 可以理解为匿名类或接口的实现
 * Kotlin 用对象表达式和对象声明来实现创建一个对某个类做了轻微改动的类的对象，且不需要去声明一个新的子类。
 * ==================================================
 */
interface OnKtClickListener {
    fun onClick(name: String);
}

class KtView {
    fun setOnKtClickListener(listener: OnKtClickListener) {
        listener.onClick("click from KtView")
    }
}

class ObjectTest {

    /**
     * ==================================================
     * 对象声明
     * Kotlin 使用 object 关键字来声明一个对象。
     * Kotlin 中我们可以方便的通过对象声明来获得一个单例。
     * ==================================================
     */
    object DataFactory { // 比如工厂模式是一个单例
        var name: String = ""
    }


    @Test
    fun objectTest() {
        // 匿名对象
        val obj = object {
            val name = "RAE"
            val age = 18
        }
        println("${obj.name} ${obj.age}")

        // 匿名类
        val view = KtView()
        view.setOnKtClickListener(object : OnKtClickListener {
            override fun onClick(name: String) {
                println(name)
            }
        })

        // 既可以继承类也可以继承接口
        val interfaceObj = object : Base("base"), ISay {
            override fun say() {
                println("接口实现")
            }
        };

        // 单例
        val d1 = DataFactory
        val d2 = DataFactory
        d1.name = "数据工厂"
        println("d1=${d1.name} d2=${d2.name} 是否为同一个对象：${d1 == d2}")

    }

    class C {
        // 私有函数，所以其返回类型是匿名对象类型
        private fun foo() = object {
            val x: String = "x"
        }

        // 公有函数，所以其返回类型是 Any
        fun publicFoo() = object {
            val x: String = "x"
        }

        fun bar() {
            val x1 = foo().x        // 没问题
//            val x2 = publicFoo().x  // 当在外部调用时错误：未能解析的引用“x”
        }
    }

}



