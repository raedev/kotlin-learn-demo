package com.github.raedev.kotlin.grammar

import org.junit.Test

/**
 * =========================
 * 第五章：扩展
 * 下一章：[KtEnum] 枚举
 * 上一章：[KtDataClass] 数据类
 * =========================
 * Kotlin 可以对一个类的属性和方法进行扩展，这是Java中没有的特性。
 * 扩展是一种静态行为，对被扩展的类代码本身不会造成任何影响。
 * 扩展的作用域：通常扩展函数或属性定义在顶级包下。要在其他包之外使用通过import导入函数名进行使用。
 */
open class User(var name: String)

// 1、扩展函数
fun String.toLink(): String {
    // this 指向当前类型
    return "<a href='$this'>这是一个链接</a>"
}

// 2、扩展一个空对象
fun Any?.hello(): String {
    return if (this == null) "null" else "hello: $this"
}

// 3、扩展属性
val User.fullName: String
    get() {
        return "full name + " + this.name
    }

// 4、扩展类方法
fun User.show() {
    println("我的名字是" + this.fullName)
}

// 扩展系统的函数
fun <T> Array<T>.join(text: String): String {
    val sb = StringBuilder()
    this.forEachIndexed { index, it ->
        if (index != 0) sb.append(text)
        sb.append(it)
    }
    return sb.toString()
}

/**
 * ==================================================
 * 同名扩展
 * 若扩展函数和成员函数一致，则使用该函数时，会优先使用成员函数。
 * ==================================================
 */
open class C
class D : C()

fun C.foo() = "c"   // 扩展函数 foo
fun D.foo() = "d"   // 扩展函数 foo
fun printFoo(c: C) {
    println(c.foo())  // 类型是 C 类
}

/**
 * ==================================================
 * 伴生对象扩展
 * ==================================================
 */
class CompanionClass {
    companion object {}
}

// 扩展伴生对象
val CompanionClass.Companion.name: String
    get() = "扩展伴生对象属性字段"

fun CompanionClass.Companion.hello() {
    println("Hello Companion")
}

// 扩展声明为成员
class ClassA {
    fun methodA() = println("ClassA ... methodA ...")
}

class ClassB {
    fun methodA() = println("ClassB ... methodA ...")
    fun methodB() = println("ClassB ... methodB ...")

    // 扩展ClassA，作用域在类内部
    fun ClassA.methodExtend() {
        methodA() // ClassA，当存在同名函数时，优先使用扩展类的。
        methodB() // ClassB
        this@ClassB.methodA() // 通过this@ClassB明确调用B的同名函数
    }

    fun test(a: ClassA) {
        a.methodExtend() // 内部才可以访问扩展方法
    }
}

/**
 * ==================================================
 * 扩展函数的重载
 * 声明为成员的扩展可以被子类重载
 * ==================================================
 */
open class Bird(var name: String) {} // 鸟类
class Swallow : Bird("我是燕子") {}

open class BirdDemo {
    // 扩展鸟类飞行方法
    open fun Bird.fly() = println("鸟类的飞行")
    open fun Swallow.fly() = println("燕子的飞行")

    // 测试方法
    fun test(bird: Bird) {
        bird.fly()
    }
}

// 扩展重载演示
class BirdDemoB : BirdDemo() {
    override fun Bird.fly() = println("扩展重载了鸟类的飞行")
    override fun Swallow.fly() = println("扩展重载了燕子的飞行")
}

/**
 * ==================================================
 * 综合演示
 * ==================================================
 */
class KtExtend {

    @Test
    fun testMain() {
        val text = "www.baidu.com"
        println(text.toLink())
        println(text.hello())
        println(null.hello())
        User("RAE").show()
        println(arrayOf(1, 2, 3, 4, 5, 6).join(","))
        printFoo(D())
//        ClassA().methodExtend(); // 外部无法访问声明为成员的扩展
        ClassB().test(ClassA())

        // 测试扩展重载
        BirdDemo().test(Bird("海鸥"))
        BirdDemoB().test(Bird("大鹏"))
        BirdDemo().test(Swallow()) // 输出：鸟类的飞行 （实际调用的是Bird的扩展函数）
    }
}

