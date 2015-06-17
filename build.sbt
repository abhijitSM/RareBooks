name := "RareBooks"

version := "1.0"

organization := "com.rarebooks"

scalaVersion := "2.11.6"

updateOptions := updateOptions.value.withCachedResolution(true)

scalacOptions in Compile ++= Seq("-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation", "-unchecked",
  "-Ywarn-dead-code", "-Xfatal-warnings", "-feature", "-language:postfixOps")

resolvers ++=
  Seq("Typesafe Repository" at
    "http://repo.typesafe.com/typesafe/releases/",
    "Spray Repository" at "http://repo.spray.io")

libraryDependencies ++= {
  val akkaVersion = "2.3.11"
  val sprayVersion = "1.3.3"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "org.scalatest" %% "scalatest" % "2.2.5" % "test"
  )
}