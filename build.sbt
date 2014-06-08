name := "nkkn"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.30",
  "org.seleniumhq.selenium" % "selenium-java" % "2.38.0" % "test"
)     

play.Project.playJavaSettings
