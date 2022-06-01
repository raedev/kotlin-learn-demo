package com.github.raedev.kotlin.grammar

import org.junit.Test
import java.util.*


// 定义一个空的类
class Empty

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


