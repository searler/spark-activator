import sbt._

object Version {
  val akka      = "2.3.8"
  val hadoop    = "2.6.0"
  val logback   = "1.1.2"
  val mockito   = "1.10.17"
  val scala     = "2.11.4"
  val scalaTest = "2.2.3"
  val slf4j     = "1.7.6"
  val spark     = "1.2.0"
  val camel     = "2.10.3"
}

object Library {
  val akkaActor      = "com.typesafe.akka" %% "akka-actor"      % Version.akka
  val akkaCamel      = "com.typesafe.akka" %% "akka-camel"      % Version.akka
  val camelStream      = "org.apache.camel" % "camel-stream"    % Version.camel
  val akkaTestKit    = "com.typesafe.akka" %% "akka-testkit"    % Version.akka
  val hadoopClient   = "org.apache.hadoop" %  "hadoop-client"   % Version.hadoop
  val logbackClassic = "ch.qos.logback"    %  "logback-classic" % Version.logback
  val mockitoAll     = "org.mockito"       %  "mockito-all"     % Version.mockito
  val scalaTest      = "org.scalatest"     %% "scalatest"       % Version.scalaTest
  val slf4jApi       = "org.slf4j"         %  "slf4j-api"       % Version.slf4j
  val sparkStreaming = "org.apache.spark"  %% "spark-streaming" % Version.spark
}

object Dependencies {

  import Library._

  val sparkAkkaHadoop = Seq(
    sparkStreaming,
    akkaActor,
    akkaCamel,
    camelStream,
    akkaTestKit,
    hadoopClient,
    logbackClassic % "test",
    scalaTest      % "test",
    mockitoAll     % "test"
  )
}
