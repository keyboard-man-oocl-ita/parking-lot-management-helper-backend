APP_NAME=parking-lot-manager-helper-backend-0.0.1-SNAPSHOT.jar
pid=`ps fax | grep java | grep $APP_NAME|awk '{print $1}'`
if [ -z "${pid}" ]; then
  kill -9 $pid
fi
sleep 10
nohup java -jar ./build/libs/$APP_NAME > /dev/null 2>&1 &
echo $! > pid.txt
ps fax | grep java | grep $APP_NAME|awk '{print $1}'
sleep 20
