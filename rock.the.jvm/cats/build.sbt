ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = project
  .in(file("."))
  .settings(
    name := "cats",
    settings,
    libraryDependencies ++= dependencies
  )

// ----------------------[ DEPENDENCIES ]------------------------

lazy val deps =
  new {
    private val catsCoreV = "2.10.0"

    val catsCore = "org.typelevel" %% "cats-core" % catsCoreV
  }

lazy val dependencies = Seq(
  deps.catsCore
)



// ----------------------[ SETTINGS ]------------------------


lazy val settings = Seq(
  scalacOptions ++= compilerOptions,
  resolvers ++= Seq(
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
  )
  ++ Resolver.sonatypeOssRepos("releases")
  ++ Resolver.sonatypeOssRepos("snapshots")
)

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
