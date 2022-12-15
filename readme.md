$ create database e_commerce_saga_user;
$ GRANT ALL PRIVILEGES ON e_commerce_saga_user.* TO 'apu'@'localhost';

$ create database e_commerce_saga_order;
$ GRANT ALL PRIVILEGES ON e_commerce_saga_order.* TO 'apu'@'localhost';

$ create database e_commerce_saga_payment;
 
$ GRANT ALL PRIVILEGES ON e_commerce_saga_payment.* TO 'apu'@'localhost';

This is a multi modules e-commerce management system project using microservices in SpringBoot based on saga pattern. \
Steps to create multi modules project in intellijIdea:
File -->   New --> Project --> Spring Initializer -- Default -- Next --> Settings -- type -- Maven POM --> Add dependencies
-- Lombok, MySQL Driver, Cloud stream, Spring for Apache kafka, Spring data jpa, and Spring Reactive web 


Centralize logging:
Add log path to the application.yml file
Start the elasticsearch
check http://localhost:9200
check http://localhost:9200/_cat/indices

Then download and start the Kiabana:
Download from url: https://www.elastic.co/downloads/past-releases/kibana-7-15-0
Check running or not: http://localhost:5601

Execute kiabana.bat under bin folder

Create an index using kibana devtools:
PUT /apu_microservices_elk_logging
{
    "settings":{
        "index":{
            "number_of_shards": 3,
            "number_of_replicas": 2
        }
    }
}
Response:
{
  "acknowledged" : true,
  "shards_acknowledged" : true,
  "index" : "apu_microservices_elk_logging"
}

then add documents to this index

Insert dummy data:
POST /apu_microservices_elk_logging/default/
{
    "name": "event processing test",
    "instructor": {
        "firstName": "apu",
        "lastName": "microservices"
    }
}
Response:
{
  "_index" : "apu_microservices_elk_logging",
  "_type" : "default",
  "_id" : "S74bqYQBK1nzuyB1_XMv",
  "_version" : 1,
  "result" : "created",
  "_shards" : {
    "total" : 3,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 0,
  "_primary_term" : 1
}


Then provide the log path tot the logtash:
Download the logstash: https://www.elastic.co/downloads/past-releases/logstash-7-15-2

Update the logstash-sample.conf file:
input {
	file {
	path => "D:/elk_logs/microservice.log"
	start_position => "beginning"
	}
  beats {
    port => 5044
  }
}

output {
  elasticsearch {
    hosts => ["http://localhost:9200"]
	index => "apu_microservices_elk_logging-%{+YYYY.MM.dd}"
    #index => "%{[@metadata][beat]}-%{[@metadata][version]}-%{+YYYY.MM.dd}"
    #user => "elastic"
    #password => "changeme"
  }
}

Then go to the logstash-7.6 bin folder create file logstash.conf file by copying from config folder logstash-sample.conf file
# Sample Logstash configuration for creating a simple
# Beats -> Logstash -> Elasticsearch pipeline.

input {
	file {
		path => "D:/elk_logs/microservice.log"
		start_position => "beginning"
	}
}

output {
  stdout {
	codec => rubydebug
  }
  elasticsearch {
    hosts => ["http://localhost:9200"]
	index => "apu_microservices_elk_logging-%{+YYYY.MM.dd}"
    #index => "%{[@metadata][beat]}-%{[@metadata][version]}-%{+YYYY.MM.dd}"
    #user => "elastic"
    #password => "changeme"
  }
}

tHen run the logstash server:

Go to the bin folder version: 7.15 or 7.6.2:
.\logstash.bat -f logstash.conf 


From kibana management: create index pattern 
apu_microservices_elk_logging*
select timestamp
then from discover page see the log details from kibana


