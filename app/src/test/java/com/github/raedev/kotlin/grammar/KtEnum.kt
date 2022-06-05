package com.github.raedev.kotlin.grammar

import org.junit.Test

/**
 * =========================
 * 第六章：枚举
 * 下一章：[KtSealed] 密封类
 * 上一章：[KtExtend] 扩展
 * =========================
 * Kotlin 枚举，使用enum class关键字定义，语法基本上与Java差不多。
 */
enum class Color {
    BLACK,
    WHITE,
    RED
}

// 自定义参数的枚举
enum class Region(private val regionName: String) {
    CHINA("中国"),
    UK("英国");

    override fun toString(): String {
        return regionName;
    }
}

// 支持方法
enum class Book(val title: String) {

    MATCH("数学书") {
        override fun price(): Int {
            return 10
        }
    },

    STORY("故事书") {
        override fun price(): Int {
            return 100
        }
    };


    abstract fun price(): Int // 书的价格

    // 也可以定义方法
    fun show() = println("${this.title}售价${this.price()}人民币")


}

class KtEnum {

    @Test
    fun testMain() {
        println(Color.BLACK)
        println(Region.CHINA)
        Book.STORY.show()
    }
}