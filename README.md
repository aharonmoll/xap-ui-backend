The monitor project is devided to 3 basic modules:
1. backend - war file which contains few basic examples, the spring-angularjs.war can be deploy to XAP and tested.
2. gs-ui-libs - can be run as junit test for retrieving platform statistics usind XAP admin API.
3. website - the main component of the monitoring tool, the website-1.0-SNAPSHOT.war file should be deployed in the Grid with a suitable locatores and by using the following URL:
http://127.0.0.1:8081/website-1.0-SNAPSHOT/xapstatistics receiving all necessary info in an xml format.
