#功能：新建用户文件夹 转到该用户文件目录 
#
#!/bin/bash
cd `dirname $0`
path=`pwd`
echo "（*******转到文件目录$path下*******）"
cd ..
path=`pwd`
echo "（*******转到文件目录$path下*******）"
mkdir User
cd User
path=`pwd`
echo "（*******转到文件目录$path下*******）"
mkdir $1
cd $1
path=`pwd`
echo "（*******转到文件目录$path下*******）"

#chmod 777 docker-compose.sh
#./docker-compose.sh
