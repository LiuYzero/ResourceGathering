ps -ef | grep $(ls *.jar) | grep -v "grep" | awk '{print $2}' | xargs kill

nohup java -Xmx128m -Xms128m -jar ./$(ls *.jar)  > /dev/null 2>&1 &