# poc-transfer-dashboard
School POC project

## Tech Talk

My proposed tech talk will cover Kafka Streams. My project most likely does not needs the full power
of Apache Spark and, while Spark is very promising, Kafka Streams should fit the project's needs.


## PROPOSAL

### Overview:

My project is a Proof-of-Concept (POC) for an append log based dashboard system using a kakfa as the append log.
The system would include stream processing for real-time dashboards as well as persisting the data for further
reporting and analytics.  There would be a REST component to interact with the system.

This is based off a real word issue we are having on with one of our productions systems, for which the system's
throughput and performance are affected by its event logging.  I would like to do this POC to show we can offload
the system's event logging onto a different system, which should help throughput.


### Technologies:

* Kafka
* Kafka Streaming
* Cassandra
* Akka Actors
* JSON
* HOCON


### Plan of attack:

The following plan would be used.

1.  Build a mock kafka producer.  The feed I'm considering is from one of our production systems so I don't know if I could do the project
with a live  feed.  Rather I'd build a mock kafka producer. The mock producer would add more data to the topic.  The system would be able
to replay the topic too

2.  Build Akka Stream components with Kafka Source and ActorRef Sink.  There is a design decision here whether to have one Kafka consumer
and process everything within the Actor system, but I think I want two distinct Kafka consumers which can run independently.

    1.  Cassandra loader component
    2.  Dashboard processor component with Kafka Streams

3.  Build REST component to interact with Cassandra

I haven't nailed down the exact rolled up information I'd like to capture, as well as nailed down the exact REST API.

