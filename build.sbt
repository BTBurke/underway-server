name := "core"

organization := "underway"

version := "0.1.0-SNAPSHOT"

homepage := Some(url("https://github.com/BTBurke/underway-server"))

startYear := Some(2013)

scmInfo := Some(
  ScmInfo(
    url("https://github.com/BTBurke/underway-core"),
    "scm:git:https://github.com/BTBurke/underway-server.git",
    Some("scm:git:git@github.com:BTBurke/underway-server.git")
  )
)

/* scala versions and options */
scalaVersion := "2.10.2"

crossScalaVersions := Seq(
/*  "2.9.3-RC1",
  "2.9.2",
  "2.9.1", "2.9.1-1",
  "2.9.0", "2.9.0-1",
  "2.8.0", "2.8.1", "2.8.2" */
)

// These options will be used for *all* versions.
scalacOptions ++= Seq(
  "-deprecation"
  ,"-unchecked"
  ,"-encoding", "UTF-8"
  ,"-target:jvm-1.7"
  // "-optimise"   // this option will slow your build
)

scalacOptions ++= Seq(
  "-Yclosure-elim",
  "-Yinline"
)

// These language flags will be used only for 2.10.x.
// Uncomment those you need, or if you hate SIP-18, all of them.
scalacOptions <++= scalaVersion map { sv =>
  if (sv startsWith "2.10") List(
    "-Xverify"
    ,"-Ywarn-all"
    ,"-feature"
    ,"-language:postfixOps"
    // "-language:reflectiveCalls",
    ,"-language:implicitConversions"
    // "-language:higherKinds",
    // "-language:existentials",
    // "-language:experimental.macros",
    // "-language:experimental.dynamics"
  )
  else Nil
}

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

/* dependencies */
libraryDependencies ++= Seq (
  "org.scala-lang.virtualized" % "scala-reflect" % "2.10.2-RC1"
  ,"com.github.nscala-time" %% "nscala-time" % "0.4.2"
  // -- network --
  //,"net.databinder.dispatch" %% "dispatch-core" % "0.10.1"
  // -- testing --
  , "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test"
  , "org.scalamock" %% "scalamock-scalatest-support" % "3.0.1" % "test" 
  // -- Logging --
  ,"ch.qos.logback" % "logback-classic" % "1.0.13"
  // -- Akka --
  ,"com.typesafe.akka" %% "akka-testkit" % "2.2.0-RC2" % "test"
  ,"com.typesafe.akka" %% "akka-actor" % "2.2.0-RC2"
  ,"com.typesafe.akka" %% "akka-slf4j" % "2.2.0-RC2"
  // -- Sql --
  ,"com.typesafe.slick" %% "slick" % "1.0.1"
  // -- Libraries --
  ,"io.argonaut" %% "argonaut" % "6.0-RC3"
  ,"com.amazonaws" % "aws-java-sdk" % "1.5.3"
  ,"org.yaml" % "snakeyaml" % "1.13"
)

/* you may need these repos */
resolvers ++= Seq(
  // Resolver.sonatypeRepo("snapshots")
  // Resolver.typesafeRepo("releases")
  // "spray repo" at "http://repo.spray.io"
)

/* assembly plugin */
mainClass in AssemblyKeys.assembly := Some("underway.core.Main")

assemblySettings

test in AssemblyKeys.assembly := {}
