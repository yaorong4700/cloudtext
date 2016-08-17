#$1 username
#$2 jobname
#!/bin/bash
cd `dirname $0`
path=`pwd`
echo "当前目录为$path"
cd ..
path1=`pwd`
echo "当前目录为$path1"
cd User
path=`pwd`
echo "当前目录为$path"
cd $1
path=`pwd`

echo "转到该用户目录下，当前目录为$path，开始获得jenkins-cli.jar   cp $path1/jenkins-cli.jar jenkins-cli.jar"
cp $path1/jenkins-cli.jar jenkins-cli.jar
echo "获取完毕 该目录下的文件如下："
ls
echo "login......start"
java -jar jenkins-cli.jar -s http://10.97.144.84:8081/ login --username hscn123 --password hscn123
echo "login......end"

echo "create-job.$1_$2.....start"
java -jar jenkins-cli.jar -s http://10.97.144.84:8081/ create-job $1_$2 < $2.xml
echo "create-job......end"

echo "build.$1_$2.....start"
java -jar jenkins-cli.jar -s http://10.97.144.84:8081/ build $1_$2
echo "build......end"
echo "curl http://10.97.144.84:8081/job/$2/lastBuild/consoleText"
#curl http://192.168.1.89:8082/job/$2/lastBuild/consoleText
