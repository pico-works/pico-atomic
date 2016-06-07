import sbt.Keys._
import sbt._

object Build extends sbt.Build {  
  val specs2_core               = "org.specs2"      %%  "specs2-core"               % "3.7.2"
  val shapeless                 = "com.chuusai"     %% "shapeless"                  % "2.3.1"

  implicit class ProjectOps(self: Project) {
    def standard(theDescription: String) = {
      self
          .settings(scalacOptions in Test ++= Seq("-Yrangepos"))
          .settings(publishTo := Some("Releases" at "s3://dl.john-ky.io/maven/releases"))
          .settings(description := theDescription)
          .settings(isSnapshot := true)
    }

    def notPublished = self.settings(publish := {}).settings(publishArtifact := false)

    def libs(modules: ModuleID*) = self.settings(libraryDependencies ++= modules)

    def testLibs(modules: ModuleID*) = self.libs(modules.map(_ % "test"): _*)
  }

  lazy val `pico-fake` = Project(id = "pico-fake", base = file("pico-fake"))
      .standard("Fake project").notPublished
      .testLibs(specs2_core)

  lazy val `pico-atomic` = Project(id = "pico-atomic", base = file("pico-atomic"))
      .standard("Tiny atomic value syntax support library")
      .libs(shapeless)
      .testLibs(specs2_core)

  lazy val all = Project(id = "pico-atomic-project", base = file("."))
      .notPublished
      .aggregate(`pico-atomic`, `pico-fake`)
}
