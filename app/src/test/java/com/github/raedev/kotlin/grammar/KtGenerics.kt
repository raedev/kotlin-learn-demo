package com.github.raedev.kotlin.grammar

import org.junit.Test

/**
 * =========================
 * 第七章：泛型
 * 下一章：
 * 上一章：[KtSealed] 密封类
 * =========================
 * 泛型的定义与Java一致
 */

class Box<T>(val value: T)


class KtGenerics {

    // 泛型方法
    private fun <V> boxValue(box: V): V {
        return Box(box).value
    }

    // 结合when做类型推断
    private fun <M> printType(m: M) {
        when (m) {
            is String -> println("String类型的值：$m")
            is Int -> println("Int类型的值：${m.toByte()}")
            else -> println("未知类型的值：${m}")
        }
    }

    // 泛型约束
    private fun <T : ISay> mustType(obj: T) {
        obj.say()
    }

    // 多个泛型约束用where
    private fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String>
            where T : CharSequence,
                  T : Comparable<T> {
        return list.filter { it > threshold }.map { it.toString() }
    }

    /**
     * ==================================================
     * 型变
     * Kotlin 中没有通配符类型，它有两个其他的东西：声明处型变（declaration-site variance）与类型投影（type projections）。
     * 声明处的类型变异使用协变注解修饰符：in、out，消费者 in, 生产者 out。
     * ==================================================
     */
    class RunoobOut<out A>(val a: A) { // 定义一个支持协变的类
        fun foo(): A {
            return a
        }
    }

    class RunoobIn<in A>(a: A) { // 定义一个支持逆变的类
        fun foo(a: A) {
        }
    }

    @Test
    fun testMain() {
        val box1 = Box<String>("Box")
        val box2 = Box(1) // 编译器自动推断类型
        println(box1.value)
        println(box2.value)
        println(boxValue("abc"));
        println(boxValue(2));
        printType("a")
        printType(100)
        printType(3.14)
        // 泛型约束
//        mustType("abc") // 报错
        val sayImpl = object : ISay {
            override fun say() = println("自由翱翔")
        };
        mustType(sayImpl)
        val listOf = listOf("123", "456", "abc", "def")
        println(copyWhenGreater(listOf, "abc"));
        // 型变
        val strCo: RunoobOut<String> = RunoobOut("a")
        var anyCo: RunoobOut<Any> = RunoobOut<Any>(1)
        anyCo = strCo
        println(anyCo.foo())   // 输出 a

        var ri = RunoobIn("a")
        val anyRi = RunoobIn<Any>("b")
        ri = anyRi

    }
}