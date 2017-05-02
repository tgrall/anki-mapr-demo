package com.mapr.demo;


import io.vertx.core.Vertx;

public class WebServer {

  public static void main(String[] args) throws Exception {

    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle( new AnkiDriveDemoVerticle() );

  }

}
