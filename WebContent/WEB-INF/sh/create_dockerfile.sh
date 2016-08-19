#$1 username
#$2 sourcename
#$3 usermail
#$4 hscnimages/tomcat7_jre7
#!/bin/bash
FROM="FROM $4"
MAINTAINER="MAINTAINER $1 <$3>"
ADD="ADD $2.war /usr/local/tomcat/webapps/"

cd `dirname $0`
path=`pwd`
echo "（*******转到文件目录$path下*******）"
cd ..
path=`pwd`
echo "（*******转到文件目录$path下*******）"
cd User
path=`pwd`
echo "（*******转到文件目录$path下*******）"
cd $1
path=`pwd`
echo "（*******转到文件目录$path下*******）"

if [ -f Dockerfile ];then
   rm -r Dockerfile
fi
echo "*创建Dockerfile*"
touch Dockerfile
echo "*$FROM>>Dockerfile*"
echo $FROM >> Dockerfile
echo "*$MAINTAINER>>Dockerfile*"
echo $MAINTAINER >> Dockerfile

echo "*$ADD>>Dockerfile*"
echo $ADD >> Dockerfile


echo "*build start hscnimages/$1/$2 *"
echo "hscn123" |sudo -S docker build -t 192.168.1.89:5000/$1/$2 .
echo "*build end hscnimages/$1/$2 *"

echo "hscn123" |sudo -S docker push 192.168.1.89:5000/$1/$2

