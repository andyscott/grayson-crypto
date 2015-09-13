import sbt._
import Keys._

import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import scalariform.formatter.preferences._
import sbtbuildinfo._
import sbtbuildinfo.BuildInfoKeys._
import de.heikoseeberger.sbtheader._

object Build extends Build {
  import BuildSettings._
  import Dependencies._

  lazy val crypto = Project("crypto", file("."))
    .settings(basicSettings: _*)
    .settings(libraryDependencies ++=
      compile(akkaActor) ++
      test(akkaTestKit) ++
      test(scalaTest)
    )
    .enablePlugins(AutomateHeaderPlugin)

  // configure prompt to show current project
  override lazy val settings = super.settings :+ {
    shellPrompt := { s => Project.extract(s).currentProject.id + "> " }
  }

}

object BuildSettings {

  lazy val basicSettings = seq(
    version               := "0.0.0",
    organization          := "grayson",
    scalaVersion          := "2.11.6",
    resolvers            ++= Dependencies.resolutionRepos,
    HeaderKey.headers     <<= (name, version) { (name, version) => Map(
      "scala" -> (HeaderPattern.cStyleBlockComment,
        ("""|/*
            | *  """ + name + """ """ + version + """
            | *--------------------------------------------------------------------*/
            |
            |""").stripMargin
          )
    ) },
    scalacOptions         := Seq(
      "-encoding", "utf8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-target:jvm-1.6",
      "-language:_",
      "-Xlog-reflective-calls",
      "-Ywarn-dead-code",
      "-Ywarn-infer-any",
      "-Ywarn-unused-import"
    ),
    cancelable in Global  := true,
    fork                  := true,
    connectInput in run   := true,
    outputStrategy        := Some(StdoutOutput)
  ) ++ formatSettings

  lazy val formatSettings = SbtScalariform.scalariformSettings ++ Seq(
    ScalariformKeys.preferences in Compile := formattingPreferences,
    ScalariformKeys.preferences in Test    := formattingPreferences
  )

  import scalariform.formatter.preferences._
  def formattingPreferences =
    FormattingPreferences()
      .setPreference(RewriteArrowSymbols,                         true)
      .setPreference(AlignParameters,                             true)
      .setPreference(AlignSingleLineCaseStatements,               true)
      .setPreference(DoubleIndentClassDeclaration,                true)
      .setPreference(PreserveDanglingCloseParenthesis,            true)
      .setPreference(MultilineScaladocCommentsStartOnFirstLine,   true)
      .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
}
