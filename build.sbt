enablePlugins(PackPlugin)

name := "nl-auto-converter"
organization := "ERSUCC"
scalaVersion := "3.7.0"

scalacOptions ++= Seq("-release", "17", "-deprecation", "-unchecked", "-Wunused:linted", "-Werror")

Compile / scalaSource := baseDirectory.value / "src" / "scala"

resolvers ++= Seq(
  "typesafe" at "https://repo.typesafe.com/typesafe/releases/",
  "netlogo" at "https://dl.cloudsmith.io/public/netlogo/netlogo/maven/"
)

libraryDependencies ++= Seq(
  "org.nlogo" % "netlogo" % "7.0.3",
  "org.jogamp.jogl" % "jogl-all" % "2.4.0" from "https://s3.amazonaws.com/ccl-artifacts/jogl-all-2.4.0.jar",
  "org.jogamp.gluegen" % "gluegen-rt" % "2.4.0" from "https://s3.amazonaws.com/ccl-artifacts/gluegen-rt-2.4.0.jar"
)
