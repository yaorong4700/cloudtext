#!/bin/bash
#$1 username
#$2 sourcename
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

echo "*run image 192.168.1.89:8080/v2/apps*start"
curl -i -H 'Content-Type: application/json' --request PUT -d@marathon_m.json 192.168.1.89:8080/v2/apps/$1/$2
echo "*run image 192.168.1.89:8080/v2/apps*end"
