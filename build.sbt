ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

lazy val root = (project in file("."))
  .aggregate(core, app)
  .settings(
    name := "functional-graphs",
    scalaVersion := "3.3.0"
  )

lazy val core = (project in file("core"))
  .settings(
    name := "core",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.14" % Test,
      "dev.zio" %% "zio" % "2.0.15",
      "dev.zio" %% "zio-json" % "0.3.0"
    )
  )

lazy val app = (project in file("app"))
  .dependsOn(core)
  .settings(
    name := "app"
  )