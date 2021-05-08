package com.afecioru.study.fp

import scala.collection.mutable.{Map => MMap, Set => MSet}

object SocialNet extends App {
  case class SocialNetException(msg: String) extends RuntimeException(msg)

  case class Person(name: String)

  val andrei = Person("Andrei")
  val mircea = Person("Mircea")
  val matei = Person("Matei")
  val ion = Person("Ion")
  val radu = Person("Radu")
  val petre = Person("Petre")
  val mihai = Person("Mihai")
  val george = Person("George")
  val dumitru = Person("Dumitru")
  val dragos = Person("Dragos")

  //noinspection DuplicatedCode
  case object SocialNet {
    private val graph = MMap[Person, MSet[Person]]()

    def build(): Unit = {
      List(andrei, mircea, matei, ion, radu, petre, mihai, george, dumitru, dragos).foreach(add)

      linkAsFriends(andrei, mircea)
      linkAsFriends(andrei, petre)

      linkAsFriends(mircea, ion)
      linkAsFriends(mircea, radu)

      linkAsFriends(radu, petre)
      linkAsFriends(radu, matei)

      linkAsFriends(matei, mihai)
      linkAsFriends(matei, george)
      linkAsFriends(matei, dumitru)
    }

    def add(person: Person): Unit =
      if (!graph.contains(person)) graph.addOne(person -> MSet.empty[Person])

    def remove(person: Person): Unit =
      if (graph.contains(person)) {
        // 1st remove the target person from the list of friends of other persons in the graph
        graph.view.mapValues(_.remove(person))
        // ... then remove the target person from the graph
        graph.remove(person)
      }

    def linkAsFriends(p1: Person, p2: Person): Unit = {
      if (!graph.contains(p1)) throw SocialNetException(s"Person not found: ${p1.name}")
      if (!graph.contains(p2)) throw SocialNetException(s"Person not found: ${p2.name}")

      graph(p1).addOne(p2)
      graph(p2).addOne(p1)
    }

    def unlinkAsFriends(p1: Person, p2: Person): Unit = {
      if (!graph.contains(p1)) throw SocialNetException(s"Person not found: ${p1.name}")
      if (!graph.contains(p2)) throw SocialNetException(s"Person not found: ${p2.name}")

      graph(p1).remove(p2)
      graph(p1).remove(p1)
    }

    def friendCount(person: Person): Int = {
      if (!graph.contains(person)) throw SocialNetException(s"Person not found: ${person.name}")

      graph(person).size
    }

    def mostPopular(): (Person, Int) = {
      graph.view.mapValues(_.size).maxBy(_._2)
    }

    def peopleWithNoFriends: Int = graph.view.count(_._2.isEmpty)

    def arePeopleConnected(p1: Person, p2: Person): Boolean = {
      if (!graph.contains(p1)) throw SocialNetException(s"Person not found: ${p1.name}")
      if (!graph.contains(p2)) throw SocialNetException(s"Person not found: ${p2.name}")

      def search(start: Person, target: Person, visited: Set[Person] = Set.empty): Boolean = {
        if (start == target) true
        else graph(start).view
          .filter(neighbour => !visited.contains(neighbour))
          .exists(neighbour => search(neighbour, target, visited + start))
      }

      search(p1, p2)
    }
  }

  SocialNet.build()

  println(s"${petre.name} has ${SocialNet.friendCount(petre)} friends.")

  val mostPopular = SocialNet.mostPopular()
  println(s"Most popular person is ${mostPopular._1.name} with ${mostPopular._2} friends.")

  println(s"There are ${SocialNet.peopleWithNoFriends} people with no friends.")

  println(s"Andrei <-> Dumitru ${SocialNet.arePeopleConnected(andrei, dumitru)}")
  println(s"Andrei <-> Dragos ${SocialNet.arePeopleConnected(andrei, dragos)}")
}
