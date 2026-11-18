addSbtPlugin("org.scoverage"      % "sbt-scoverage" % "2.4.4")
addSbtPlugin("org.scoverage"      % "sbt-coveralls" % "1.3.1")
addSbtPlugin("io.stryker-mutator" % "sbt-stryker4s" % "1.0.0")

// Keep ScalaMeta compatible with Stryker4s
dependencyOverrides ++= Seq(
  "org.scalameta" %% "scalameta" % "4.17.2",
  "org.scalameta" %% "parsers"   % "4.17.2",
  "org.scalameta" %% "trees"     % "4.17.2",
  "org.scalameta" %% "trees2"    % "4.17.2",
  "org.scalameta" %% "common"    % "4.17.2",
  "org.scalameta" %% "common2"   % "4.17.2",
  "org.scalameta" %% "io"        % "4.17.2"
)
