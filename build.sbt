name := """play-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(JavaAppPackaging, PlayScala, ConductRPlugin)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "com.typesafe.conductr" %% "play24-conductr-bundle-lib" % "1.0.0"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "typesafe-releases" at "http://repo.typesafe.com/typesafe/maven-releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := true

// ConductR

import ByteConversions._

BundleKeys.nrOfCpus := 1.0
BundleKeys.memory := 128.MiB
BundleKeys.diskSpace := 50.MiB
BundleKeys.roles := Set("front-end")

BundleKeys.endpoints := Map("seed" -> Endpoint("http", services = Set(URI("http://:8080/seed"))))

BundleKeys.startCommand += "-Dhttp.port=$SEED_BIND_PORT -Dhttp.address=$SEED_BIND_IP"
