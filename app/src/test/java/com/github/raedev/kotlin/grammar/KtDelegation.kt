package com.github.raedev.kotlin.grammar

import org.junit.Test
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * =========================
 * 第四章：委托
 * 下一章：[KtExtend] 扩展
 * 上一章：[KtInterface] 接口
 * =========================
 *  一个对象将消息委托给另一个对象来处理。也可以理解代理。
 *  Kotlin 通过 by 关键字可以更加优雅地实现委托。
 *  ------------------------
 *  举个委托的例子：
 *  -------------
 *  张三要去买瓶酒喝(fun buy()方法)，但现在张三没有空就委托李四帮忙去买，那么至于怎么买，是李四的具体实现。张三只要知道结果就是得到一瓶酒。代码实现如下：
 *  -------------
 *  interface Buy{
 *    Liquor buy(); // 买酒方法
 *  }
 *  class LiSi extends Buy{
 *      Liquor buy(){
 *          println("李四去买酒了")
 *          return new Liquor("贵州茅台")
 *      }
 *  }
 *  class ZhangSan extends Buy{
 *      private LiSi ls = new LiSi(); // 李四
 *      Liquor buy(){
 *        return ls.buy()
 *      }
 *  }
 */
interface MyBase {
    fun print()
}

// 实现此接口的被委托的类
class MyBaseImpl(val x: Int) : MyBase {
    override fun print() {
        print(x)
    }
}

// 通过关键字 by 建立委托类
// 在 Derived 声明中，by 子句表示，将 b 保存在 Derived 的对象实例内部，而且编译器将会生成继承自 Base 接口的所有方法, 并将调用转发给 b。
class Derived(b: MyBase) : MyBase by b

/**
 * ==================================================
 * 属性委托
 * 属性委托指的是一个类的某个属性值不是在类中直接进行定义，而是将其托付给一个代理类，从而实现对该类的属性统一管理。
 * 属性委托语法格式：val/var <属性名>: <类型> by <表达式>
 * ==================================================
 */
class MyPersonA {
    var name: String by MyDelegation()
    val lazyValue: String by lazy { // 懒加载
        println("computed!")     // 第一次调用输出，第二次调用不执行
        "Hello" // 返回值
    }

    // 可观察的属性
    var title: String by Delegates.observable("初始值") { prop, old, new ->
        println("旧值：$old -> 新值：$new")
    }

    // 不为空
    var age: Int by Delegates.notNull()
}

class MyDelegation {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, 这里委托了 ${property.name} 属性"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$thisRef 的 ${property.name} 属性赋值为 $value")
    }
}


// 把属性储存在映射中
class Site(map: Map<String, Any?>) {
    val name: String by map
    val url: String by map
}

// 自定义委托（提供委托provideDelegate的使用）
class MyPersonB {
    val name: String by BindResourceDelegation(0x10)
}

/*
* 这里确实比较难理解。这样理解比较简点：MyPersonB.name属性是get方法，封装一层代理类返回了。对应Java代码为：
* class MyPersonB {
*       private BindResourceDelegation delegate = new BindResourceDelegation();
*       private String getName(){
*          final int id = 0x10;
*          return new BindResourceDelegation(id).provideDelegate(this, property); // property的值是被Kotlin的KProperty系统赋值
*       }
* }
* */
class BindResourceDelegation<T>(val id: Int) : ReadOnlyProperty<MyPersonB, T> {
    // 这里方法名固定为provideDelegate
    operator fun provideDelegate(
        thisRef: MyPersonB, // 委托所在的对象
        property: KProperty<*> // 属性字段
    ): ReadOnlyProperty<MyPersonB, T> { // 委托的返回值
        println("abc")
        return this
    }

    override fun getValue(thisRef: MyPersonB, property: KProperty<*>): T {
        println("111")
        return "$id -> 委托中赋的值" as T
    }
}


class KtDelegation {

    @Test
    fun testMain() {
        val b = MyBaseImpl(10)
        Derived(b).print() // 输出 10
        MyPersonA().name = "123"

        // 构造函数接受一个映射参数
        val site = Site(
            mapOf(
                "name" to "菜鸟教程",
                "url" to "www.runoob.com"
            )
        )

        // 读取映射值
        println(site.name)
        println(site.url)

        println(MyPersonB().name)
    }

}