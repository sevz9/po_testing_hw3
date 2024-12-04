package org.example

import org.jetbrains.kotlinx.lincheck.LinChecker
import org.jetbrains.kotlinx.lincheck.annotations.Operation
import org.jetbrains.kotlinx.lincheck.check
import org.jetbrains.kotlinx.lincheck.strategy.managed.modelchecking.ModelCheckingOptions
import org.jetbrains.kotlinx.lincheck.strategy.stress.StressCTest
import org.jetbrains.kotlinx.lincheck.strategy.stress.StressOptions
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.atomic.AtomicReference



class TreiberStack<T> {
    private val top = AtomicReference<Node<T>?>(null)

    private class Node<T>(val value: T, var next: Node<T>?)

    fun push(value: T) {
        val newHead = Node(value, null)
        do {
            val oldHead = top.get()
            newHead.next = oldHead
        } while (!top.compareAndSet(oldHead, newHead))
    }

    fun pop(): T? {
        var oldHead: Node<T>?
        var newHead: Node<T>?
        do {
            oldHead = top.get()
            if (oldHead == null) {
                return null // Стек пуст
            }
            newHead = oldHead.next
        } while (!top.compareAndSet(oldHead, newHead))
        return oldHead?.value
    }
}



class TreiberStackSequentialTest {
    private val stack = TreiberStack<Int>()

    @Operation
    fun push(x: Int) {
        stack.push(x)
    }

    @Operation
    fun pop(): Int? {
        return stack.pop()
    }

    @Test
    fun test() {
        StressOptions()
            .threads(1)
            .check(TreiberStackSequentialTest::class.java)
    }
}