package com.mapr.demo;

import com.mapr.demo.streams.consumer.SimpleRebalanceListener;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

public class AnkiDriveDemoVerticle extends AbstractVerticle {

  String ankiController;
  WebClient client = null;
  String ankiControllerHost;
  int ankiControllerPort;


  @Override
  public void start(Future fut) throws MalformedURLException {

    // Configuration
    ankiController = config().getString("anki.controller","http://localhost:7877/");
    // extract host & port
    URL ankiControllerURL = new URL( ankiController );
    ankiControllerHost = ankiControllerURL.getHost();
    ankiControllerPort = ankiControllerURL.getPort();


    HttpServer httpServer = vertx.createHttpServer();
    HttpClient httpClient = vertx.createHttpClient();

    Router router = Router.router(vertx);
    client = WebClient.create(vertx);

    // REST routes
    router.get("/api/startdemo").handler(this::startDemo);



    BridgeOptions options = new BridgeOptions();
    options.setOutboundPermitted(Collections.singletonList(new PermittedOptions().setAddress("dashboard")));


    router.route("/eventbus/*").handler(SockJSHandler.create(vertx).bridge(options));
    router.route("/*").handler(StaticHandler.create().setCachingEnabled(false));
    router.route().handler(BodyHandler.create());



    vertx
            .createHttpServer()
            .requestHandler(router::accept)
            .listen(
                    config().getInteger("http.port", 8080),
                    result -> {
                      if (result.succeeded()) {
                        fut.complete();
                      } else {
                        fut.fail(result.cause());
                      }
                    });

    // Create a MapR Streams Consumer
    KafkaConsumer<String, String> consumer;
    Properties properties = new Properties();
    properties.setProperty("group","vertx_dashboard");
    properties.setProperty("enable.auto.commit","true");
    properties.setProperty("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
    properties.setProperty("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

    consumer = new KafkaConsumer<>(properties);"/apps/anki:iot.*");
    consumer.subscribe(pattern, new SimpleRebalanceList

    Pattern pattern = Pattern.compile(ener());

    EventBus eb = vertx.eventBus();

     new Thread( new Runnable() {

       @Override
       public void run() {
         while (true) {
           // read records with a short timeout. If we time out, we don't really care.
           ConsumerRecords<String, String> records = consumer.poll(200);
           for (ConsumerRecord<String, String> record : records) {
             System.out.println(record.topic() +":"+  record.value());
             eb.send("dashboard",  record.value() );
           }
         }

       }


     }).start();


  }


  private void startDemo(RoutingContext routingContext) {
    System.out.println( ankiController );

    client.get( ankiControllerPort, ankiControllerHost, "/startDemoGo"  ).send( ar -> {
      if (ar.succeeded()) {
        HttpResponse<Buffer> response = ar.result();

        routingContext.response()
                .putHeader("content-type", "text/html; charset=utf-8")
                .end(response.body());


        System.out.println("Got HTTP response with status " + response.statusCode());
      } else {
        ar.cause().printStackTrace();
      }
            }
    );


  }

}
