#$1 username
#$2 jobname
#!/bin/bash
cd `dirname $0`
path=`pwd`
#echo "当前目录为$path"
cd ..
path1=`pwd`
#echo "当前目录为$path1"
cd User
path=`pwd`
#echo "当前目录为$path"
cd $1
path=`pwd`

#echo "转到该用户目录下，当前目录为$path，开始获得jenkins-cli.jar   cp $path1/jenkins-cli.jar jenkins-cli.jar"
cp $path1/jenkins-cli.jar jenkins-cli.jar
#echo "获取完毕 该目录下的文件如下："
#ls
#echo "login......start"
#java -jar jenkins-cli.jar -s http://192.168.1.89:8082/ login --username hscn123 --password hscn123
#echo "login......end"
#echo "console $1_$2 -f -n 1.....start"
java -jar jenkins-cli.jar -s http://10.97.144.84:8081/ console $1_$2 -f -n 1
#echo "console $1_$2 -f -n 1......end"
