// TO DO: Add Markdown doc processing back in.

// ---------------------------------------------------------------------------
// Basic settings

name := "scalasti"

organization := "org.clapper"

version := "1.0.0"

licenses := Seq(
  "BSD" -> url("http://software.clapper.org/scalasti/license.html")
)

homepage := Some(url("http://software.clapper.org/scalasti/"))

description := (
  "A Scala-friendly wrapper for Terence Parr's StringTemplate library"
)

scalaVersion := "2.10.0-RC1"

// ---------------------------------------------------------------------------
// Additional compiler options and plugins

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

crossScalaVersions := Seq("2.10.0-RC1")

seq(lsSettings :_*)

(LsKeys.tags in LsKeys.lsync) := Seq(
  "template", "string template", "StringTemplate"
)

(description in LsKeys.lsync) <<= description(d => d)

// ---------------------------------------------------------------------------
// Additional repositories

resolvers ++= Seq(
    "Java.net Maven 2 Repo" at "http://download.java.net/maven/2"
)

// ---------------------------------------------------------------------------
// ScalaTest dependendency


libraryDependencies <<= (scalaVersion, libraryDependencies) { (sv, deps) =>
    // Select ScalaTest version based on Scala version
    val scalatestVersionMap = Map(
      "2.10.0-RC1" -> ("scalatest_2.10.0-RC1", "2.0.M4-2.10.0-RC1-B1")
    )
    val (scalatestArtifact, scalatestVersion) = scalatestVersionMap.getOrElse(
        sv, error("Unsupported Scala version for ScalaTest: " + scalaVersion)
    )
    deps :+ "org.scalatest" % scalatestArtifact % scalatestVersion % "test"
}

libraryDependencies <<= (scalaVersion, libraryDependencies) { (sv, deps) =>
  // ScalaTest still uses the (deprecated) scala.actors API.
  deps :+ "org.scala-lang" % "scala-actors" % sv % "test"
}

// ---------------------------------------------------------------------------
// Other dependendencies

libraryDependencies ++= Seq(
    "org.clapper" % "grizzled-scala_2.10" % "1.1.2",
    "org.clapper" % "classutil_2.10" % "1.0.1",
    "org.antlr" % "stringtemplate" % "3.2.1"
)

// ---------------------------------------------------------------------------
// Publishing criteria

publishTo <<= version { v: String =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <scm>
    <url>git@github.com:bmc/scalasti.git/</url>
    <connection>scm:git:git@github.com:bmc/scalasti.git</connection>
  </scm>
  <developers>
    <developer>
      <id>bmc</id>
      <name>Brian Clapper</name>
      <url>http://www.clapper.org/bmc</url>
    </developer>
  </developers>
)
