package com.mapr.demo.anki.consumer;

import com.mapr.db.MapRDB;
import com.mapr.db.Table;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class DbConsumer {

  public static void main(String[] args) {

    Table table = getTable("/apps/anki_position");


    KafkaConsumer<String, String> consumer;
    Properties properties = new Properties();
    properties.setProperty("group","anki_db_consumer_data");
    properties.setProperty("enable.auto.commit","true");
    properties.setProperty("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
    properties.setProperty("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
    //properties.setProperty("auto.offset.reset", "earliest");

    consumer = new KafkaConsumer<>(properties);

    Pattern pattern = Pattern.compile("/apps/anki:iot-position.*");
    consumer.subscribe(pattern, new SimpleRebalanceListener());

    int i=0;

    while (true) {
      // read records with a short timeout. If we time out, we don't really care.
      ConsumerRecords<String, String> records = consumer.poll(200);
      for (ConsumerRecord<String, String> record : records) {
        System.out.println(record.topic() +":"+  record.value());
        table.insertOrReplace(UUID.randomUUID().toString(), MapRDB.newDocument( record.value() ) );
        i = i++;
        if (i==10) {
          table.flush();
          i=0;
        }
      }
    }

  }



  public static Table getTable(String tableName) {

    if (MapRDB.tableExists(tableName)) {
      return MapRDB.getTable(tableName);
    } else {
      return MapRDB.createTable(tableName);
    }


  }
}
