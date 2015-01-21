import akka.actor.Actor.Receive
import akka.actor.{ Actor, ActorIdentity, Identify, Props }
import java.util.concurrent.Executors
import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.storage._
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream._
import org.apache.spark.streaming.receiver._
import scala.concurrent.Await
import akka.camel.Consumer
import akka.camel.CamelMessage

class Storer extends Actor with ActorHelper with Consumer {

  def endpointUri = "stream:in"

  def receive = {
    case cm: CamelMessage => cm.body.toString match {
      case "stop" => System.exit(0)
      case _ @ s => store(s) 
    }
  }
}

object CamelApp {
  def main(args: Array[String]) {
    // Configuration for a Spark application.
    // Used to set various Spark parameters as key-value pairs.
    val driverPort = 7777
    val driverHost = "localhost"
    val conf = new SparkConf(false) // skip loading external settings
      .setMaster("local[*]") // run locally with as many threads as needed
      .setAppName("Spark Streaming with Scala and Akka") // name in Spark web UI
      .set("spark.logConf", "true")
      .set("spark.driver.port", driverPort.toString)
      .set("spark.driver.host", driverHost)
      .set("spark.akka.logLifecycleEvents", "true")
    val ssc = new StreamingContext(conf, Seconds(1))

    val actorStream = ssc.actorStream[String](Props[Storer], "storer")

    // describe the computation on the input stream as a series of higher-level transformations
    actorStream.reduce(_ + "##" + _).print()

    // start the streaming context so the data can be processed
    // and the actor gets started
    ssc.start()

    import scala.concurrent.duration._

    ssc.awaitTermination()
  }
}