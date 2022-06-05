package com.github.raedev.kotlin.grammar

/**
 * =========================
 * 第三章：接口
 * 下一章：[KtDataClass] 数据类
 * 上一章：[KtClass] 数据类
 * =========================
 * 接口语法基本上Java写法一致
 */

interface KtInterfaceA {
    fun methodA()
    fun methodB() {
        println("接口方法可以有默认实现")
    }
}

interface KtInterfaceB {
    // 接口可以有属性
    var name: String
    fun methodB() {
        println("KtInterfaceB接口方默认实现")
    }

    fun methodC()
}

class KtInterface(override var name: String) : KtInterfaceA, KtInterfaceB {

    override fun methodA() {
    }

    override fun methodB() {
        // 同样可以指定父类的实现
        super<KtInterfaceA>.methodB();
        super<KtInterfaceB>.methodB();
    }

    override fun methodC() {
    }
}


