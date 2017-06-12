



## Anki Controller

This is a Node.js application located in `./anki-controller`.


## MapR Confguration

Create MapR Streams:

```
maprcli stream create -path /apps/anki -produceperm p -consumeperm p -topicperm p

maprcli stream topic create -path /apps/anki -topic iot-position-GroundShock

maprcli stream topic create -path /apps/anki -topic iot-battery-GroundShock

maprcli stream topic create -path /apps/anki -topic iot-position-Skull 

maprcli stream topic create -path /apps/anki -topic iot-battery-Skull

maprcli stream topic create -path /apps/anki -topic iot-position-Thermo 

maprcli stream topic create -path /apps/anki -topic iot-battery-Thermo

maprcli stream topic create -path /apps/anki -topic iot-position-Nuke 

maprcli stream topic create -path /apps/anki -topic iot-battery-Nuke



```


Where you will be running the Web Server (VertX/Java application) configure the MapR Client

```
echo "my.cluster.com secure=false your_server:7222" > /opt/mapr/conf/mapr-clusters.conf 
```


