package com.github.raedev.kotlin.grammar

import org.junit.Test

/**
 * =========================
 * 第四章：数据类
 * 下一章：[KtExtend] 扩展
 * 上一章：[KtInterface] 接口
 * =========================
 * 数据类: 使用关键字data 定义。
 * 编译器会自动的从主构造函数中根据所有声明的属性提取以下函数：
 * 1、equals() / hashCode()
 * 2、toString() 格式如 "User(name=John, age=42)"
 * 3、componentN() functions 对应于属性，按声明顺序排列
 * 3、copy() 函数
 */
data class UserInfo(val name: String, val age: Int)

class KtDataClass {

    @Test
    fun testMain() {
        val user = UserInfo("RAE", 18)
        val my = user.copy(name = "马云", age = 12) // 自动生成copy复制函数，输出：UserInfo(name=马云, age=12)
        val d = user.copy(name = "刘强东") // 通过参数指定新的属性，其他保持复制的值，输出：UserInfo(name=刘强东, age=18)
        val newUser = user.copy() // 全部一样
        println(user) // 自动生成toString()
        println(my)
        println(d)
        println(newUser)
        println("地址是一样：" + (user == newUser))

        // 数据类解构声明
        val (name, age) = user;
        println("解构后name=$name,age=$age")

    }
}