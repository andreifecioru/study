name := "exercises"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick"           % "3.3.1",
  "com.h2database"      % "h2"              % "1.4.197",
  "ch.qos.logback"      % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
)


