package com.github.raedev.kotlin.grammar

import org.junit.Test


/**
 * =========================
 * 第六章：密封类
 * 下一章：[KtGenerics] 泛型
 * 上一章：[KtEnum] 枚举
 * =========================
 * 密封类其实是一个特殊的抽象类，也是对枚举类的扩展（可以理解为带参数的枚举类）。
 * 1、密封类用来表示受限的类继承结构：当一个值为有限几种的类型, 而不能有任何其他类型时。
 * 2、在某种意义上，他们是枚举类的扩展：枚举类型的值集合 也是受限的，但每个枚举常量只存在一个实例，而密封类 的一个子类可以有可包含状态的多个实例。
 * 3、声明一个密封类，使用 sealed 修饰类，密封类可以有子类，但是所有的子类都必须要内嵌在密封类中。一般搭配when表达式使用。
 * 4、sealed 不能修饰 interface ,abstract class(会报 warning,但是不会出现编译错误)
 * 5、密封类所有的构造方法都是private的
 */
// 这里使用上一篇的枚举类颜色作为例子
// 这里实际编译成Java代码为：public abstract class SealedColor {...}
sealed class SealedColor {
    // Java代码为：public static final class BLACK extends SealedColor {...}
    object BLACK : SealedColor()
    object RED : SealedColor()

    // 其他颜色，这里就比枚举类多了一个动态参数的声明
    class OTHER(private val depth: Int) : SealedColor() {
        fun show() {
            println("其他颜色，颜色深度为${this.depth}位")
        }
    }

}

class KtSealed {

    @Test
    fun testMain() {
        showColor(SealedColor.BLACK)
        showColor(SealedColor.RED)
        showColor(SealedColor.OTHER(32))
    }

    private fun showColor(sealedColor: SealedColor) {
        when (sealedColor) {
            is SealedColor.BLACK -> println("黑色")
            is SealedColor.RED -> println("红色")
            is SealedColor.OTHER -> sealedColor.show()
        }
    }
}