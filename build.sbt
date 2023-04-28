name := "akka-essentials-typed"

version := "0.1"

scalaVersion := "2.13.8"

val akkaVersion = "2.6.18"
val scalaTestVersion = "3.2.9"
val logbackVersion = "1.2.10"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
  "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "3.0.0",
  "com.typesafe.akka" %% "akka-stream" % akkaVersion ,
  "com.github.tototoshi" %% "scala-csv" % "1.3.10",
  "org.apache.commons" % "commons-csv" % "1.10.0"

)


//name := "akka-streams"
//
//version := "0.1"
//
//scalaVersion := "2.13.8"
//val akkaVersion = "2.6.18"
//val scalaTestVersion = "3.2.9"
//
////lazy val akkaVersion = "2.5.19"
////lazy val scalaTestVersion = "3.0.5"
//val logbackVersion = "1.2.10"
//
//libraryDependencies ++= Seq(
//
//  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
//  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion,
//  "org.scalatest" %% "scalatest" % scalaTestVersion,
//  "ch.qos.logback" % "logback-classic" % logbackVersion,
//  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
//  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
//  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
//  "org.scalatest" %% "scalatest" % scalaTestVersion
//)
//
//name := "udemy-akka-streams"
//
//version := "0.1"
//
//scalaVersion := "2.12.8"
//
//lazy val akkaVersion = "2.5.19"
//lazy val scalaTestVersion = "3.0.5"
//
//libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
//  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
//  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
//  "org.scalatest" %% "scalatest" % scalaTestVersion
//)
