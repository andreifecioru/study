package org.afecioru.study
package ch_04

sealed trait Result

case class Success(value: Int) extends Result
case class Failed(errorMessage: String) extends Result

class Ex_4_4_4_2 extends BaseTestSuite {

}
