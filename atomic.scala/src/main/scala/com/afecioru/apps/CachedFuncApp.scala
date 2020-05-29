package com.afecioru.apps

class CacheWrapper(func: Int => Int) {
  import scala.collection.mutable.{ Map => MMap }

  private[this] var cache: MMap[Int, Int] = MMap()

  // make the instance callable
  def apply(arg: Int): Int = {
    println(s"Cache: $cache")
    cache.getOrElseUpdate(arg, func(arg))
  }
}

object CachedFuncApp extends App {
  def expensiveFunc(arg: Int): Int = {
    println(s"Computing for: $arg")
    // Wait 5 secs to emulate a long computation
    Thread.sleep(5000L)
    arg
  }

  val cachedFunc = new CacheWrapper(expensiveFunc)

  // run multiple computations for the same arg:
  cachedFunc(10)
  cachedFunc(10)
  cachedFunc(10)
}
