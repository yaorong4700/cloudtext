FROM 192.168.1.89:5000/tomcat7_jre7
MAINTAINER hscn <hscn>
ADD hellospring.war /usr/local/tomcat/webapps/
