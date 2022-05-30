package com.github.raedev.kotlin.grammar;

import org.junit.Test

/**
 * 基础语法示例
 * 1、包名跟Java一致，但kotlin源文件不需要相匹配的目录和包，源文件可以放在任何文件目录。文件名也不需要和类名一致。
 * 2、可以省略分号不写
 * @author RAE
 * @date 2022/05/30
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
class Grammar1 {

    // region 变量定义

    // 定义变量，变量定义后可以修改
    // 类型自动推断
    var var1 = 1;

    // 指定变量类型
    var var2: String = "hello";

    // 定义常量，定义后不可以修改，Java写法：final int val1=2;
    val val1 = 2;

    @Test
    fun valTest() {
        val a: Int // 定义时可以不赋值，但使用的时候一定要有值，由编译器自动推断
        // println(a) // 编译器报错
        a = 1
        println(a) // 编译器正常
    }

    // 字符串模板
    private val hello = "Hello";
    private val word = "Kotlin"

    // 使用关键字$使用变量，两种方法$word直接引用变量值，${(1 + 1)}可以进行任意表达式的调用
    private val helloWord = "${hello}, welcome to $word! 1+1=${(1 + 1)}"

    // endregion

    // region 函数定义

    // 定义函数1，使用fun关键字，格式为：(参数：类型): 返回值
    // 以下Java写法为： int sum1(int a, int b){ ... }
    fun sum1(a: Int, b: Int): Int {
        return a + b
    }

    // 定义函数2，简写
    fun sum2(a: Int, b: Int): Int = a + b;

    // 定义函数3，编译器自动推断返回类型
    fun sum3(a: Int, b: Int) = a + b;

    // 无返回值
    fun void1(a: Int, b: Int): Unit {}

    // 无返回值，默认省略Unit
    fun void2(a: Int, b: Int) {}

    // 可变参数，使用关键字：vararg
    // Java写法为： void vars1(int... value){}
    fun vars1(vararg value: Int) {
        for (vt in value) {
            print(vt)
        }
    }

    // lambda表达式（匿名函数）
    fun lambda1() {
        // 方法内部可以定义方法
        val sum: (Int, Int) -> Int = { a, b -> a + b }
        println(sum(1, 2))
    }

    // endregion

    // region 空指针安全

    // 空指针安全
    // 类型? 表示可以为空的类型
    private val valNull1: String? = null

    @Test
    fun testNull1() {
        // 空指针安全设计使用?.，如果编译器自动返回空
        val a = valNull1?.toInt()
        println("a is null? ${a == null}")
        // 为空默认值处理
        val b = valNull1?.toInt() ?: -1
        println("b default value is $b")
        // 强制使用，如果你确定这个变量值一定不为空时候可以使用!!转换，一旦为空抛出异常
        val c = a!!.toInt() // 运行报错

        // 接下来请查看下面testNull2方法进行延伸
    }

    fun parseInt(a: String): Int? {
        return a.toIntOrNull()
    }

    @Test
    fun testNull2() {
        val a = parseInt("1")
        val b = parseInt("a")
        // var sum = a + b; // 不能直接使用为空的值，编译器报错
        // 必须先判断为空再使用
        if (a != null && b != null) {
            val sum = a + b
        }
        // 或者强制转换，后果由开发人员自负，这里当然是报错了
        // 或者你可以给一个默认值就不会报错  val b = parseInt("a")?:0
        val r = a!! + b!!
    }


    // endregion

    // 综合测试
    @Test
    fun mainTest() {
        println(this.helloWord)
        vars1(1, 2, 3, 4)
        lambda1()
    }

}

/**
 * 一个文件可以定义多个类，这个跟Java是不一样的地方
 */
class Grammar2 {
    // region 自动类型推断

    //  Any表示任意类型，类似Java的Object父类
    fun testAutoType(obj: Any) {
        if (obj is String) {
            val length = obj.length
            println("类型推断后变量自动推断为String了：$length")
        }
        if (obj is Int) {
            val a: Int = obj; // 自动转换为Int类型
            println("类型推断后变量自动推断为Int了：${a + 1}")
        }

        // val a: Int = obj; // 没有判断，这里编译器报错
    }
    // endregion
}