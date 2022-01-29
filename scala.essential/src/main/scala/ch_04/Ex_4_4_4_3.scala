package org.afecioru.study
package ch_04

trait WaterSource
case object Well extends WaterSource
case object Spring extends WaterSource
case object Tap extends WaterSource

final case class BottledWater(size: Int, source: WaterSource, carbonated: Boolean)

class Ex_4_4_4_3 extends BaseTestSuite {

}
