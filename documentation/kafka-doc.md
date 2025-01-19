Map api : AIzaSyA64YnSfEC8vSF_LkN5VpTXVpHn5WmEKNM


# Start servers
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
.\bin\windows\kafka-server-start.bat .\config\server.properties

# Verify ports
netstat -ano | findstr :2181
netstat -ano | findstr :9092


# Create a topic
cd C:\kafka

.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic taxi-stations.raw

#verify
.\bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092

#lisst all topic
.\bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092

# Write in a topic
.\bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic test-topic




# Consume data in a topic
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic taxi-stations.raw


# Delete a topic
.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --delete --topic taxi-stations.raw
# Verify if deleted
.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list
