ThisBuild / name := "scala-with-cats"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(settings ++ Seq(name := "scala-with-cats"))
  .disablePlugins(AssemblyPlugin)
  .aggregate(
    type_classes,
    playground
  )

lazy val type_classes = project
  .settings(
    name := "type-classes",
    settings,
    libraryDependencies ++= commonDependencies
  )
  .disablePlugins(AssemblyPlugin)

lazy val playground = project
  .settings(
    name := "playground",
    settings,
    libraryDependencies ++= commonDependencies
  )
  .disablePlugins(AssemblyPlugin)

// ---------------------[ DEPENDENCIES ]-----------------------

lazy val dependencies =
  new {
    val catsCoreV = "2.10.0"
    val catsCore = "org.typelevel" %% "cats-core" % catsCoreV
  }

lazy val commonDependencies = Seq(
  dependencies.catsCore
)

// ------------------------[ SETTINGS ]--------------------------

lazy val settings = commonSettings

lazy val compilerOptions = Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8"
)


lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions,
  resolvers ++= Seq(
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
  )
  ++ Resolver.sonatypeOssRepos("releases")
  ++ Resolver.sonatypeOssRepos("snapshots")
)
