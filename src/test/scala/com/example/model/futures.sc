
import com.paulgoldbaum.influxdbclient.Parameter.Precision
import com.paulgoldbaum.influxdbclient.{InfluxDB, Point}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

val a = Future {Thread.sleep(1); 50}

val map_a = a.map(_ + 5)

val map_b =
  for (
    b <- map_a
  ) yield { b + 10}

val influxdb = InfluxDB.connect("localhost", 8086)
val database = influxdb.selectDatabase("my_database")


val foo = database.exists().map(x =>
  if (x) {
    Future.successful(database)
  }
  else {
    for (
      d <- database.create()
    ) yield { database }
  })

val y = foo.flatMap(identity)

val point = Point("readings", System.currentTimeMillis())
  .addTag("sensor", "8")
  .addField("temp", 26.7)

y.map(_.write(point, precision = Precision.MILLISECONDS))

Await.result(y, 10.seconds)

database.close()
