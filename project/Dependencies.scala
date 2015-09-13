import sbt._
import Keys._

object Dependencies {

  val resolutionRepos = Seq(
    "Typesafe releases"       at "http://repo.typesafe.com/typesafe/releases",
    "Sonatype OSS Snapshots"  at "https://oss.sonatype.org/content/repositories/snapshots"
  )

  def compile   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  def provided  (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def test      (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
  def it        (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "it")
  def runtime   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  def container (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  // Akka
  val akkaVersion   = "2.3.12"
  val akkaActor     = "com.typesafe.akka" %% "akka-actor"   % akkaVersion
  val akkaTestKit   = "com.typesafe.akka" %% "akka-testkit" % akkaVersion

  // Testing
  val scalaTest     = "org.scalatest" %% "scalatest" % "2.2.4"
}
