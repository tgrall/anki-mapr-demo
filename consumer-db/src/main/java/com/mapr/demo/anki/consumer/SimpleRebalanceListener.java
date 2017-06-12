package com.mapr.demo.anki.consumer;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;

public class SimpleRebalanceListener implements ConsumerRebalanceListener {
  @Override
  public void onPartitionsRevoked(Collection<TopicPartition> collection) {

  }

  @Override
  public void onPartitionsAssigned(Collection<TopicPartition> collection) {

  }
}
