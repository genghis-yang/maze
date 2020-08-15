ThisBuild / scalaVersion := "2.13.3"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "info.genghis.game"

val IndigoVersion = "0.2.0"
val Specs2Version = "4.10.0"

lazy val root = (project in file("."))
  .enablePlugins(ScalaJSPlugin, SbtIndigo) // Enable the Scala.js and Indigo plugins
  .settings(
    name := "maze",
    libraryDependencies ++= Seq(
      "io.indigoengine" %%% "indigo"             % IndigoVersion,
      "io.indigoengine" %%% "indigo-json-circe"  % IndigoVersion, 
      "org.specs2"      %%  "specs2-core"        % Specs2Version % "test",
    ),
    // Indigo Special Settings
    showCursor := true,
    title := "Maze",
    gameAssetsDirectory := "src/main/resources/assets"
  )

addCommandAlias("buildGame", ";compile;fastOptJS;indigoBuildJS")
addCommandAlias("publishGame", ";compile;fullOptJS;indigoPublishJS")
