CATALINA_BASE=/Users/mrithyunjayanm/Projects/eclip/.metadata/.plugins/org.eclipse.wst.server.core/tmp0 \
CATALINA_HOME=/Users/mrithyunjayanm/Projects/eclip/apache-tomcat-9.0.120 \
/Users/mrithyunjayanm/Projects/eclip/apache-tomcat-9.0.120/bin/catalina.sh run

clg cmds - 

cd "C:\Users\CSE\1033-Main\web-tech"

$mysql = "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
$java = "C:\Users\CSE\1033\runtime\jdk\jdk-17.0.20.1+1"
$tomcat = "$PWD\apache-tomcat-9.0.121"

# Enter the MySQL root password when prompted
& $mysql -u root -p < "$PWD\src\main\webapp\setup.sql"
& $mysql -u root -p < "$PWD\src\main\webapp\grocery.sql"

# Compile Java
$sources = Get-ChildItem "$PWD\src\main\java" -Recurse -Filter "*.java"
& "$java\bin\javac.exe" `
  -cp "$tomcat\lib\*;$PWD\src\main\webapp\WEB-INF\lib\mysql-connector-j-9.7.0.jar" `
  -d "$PWD\build\classes" `
  $sources.FullName

# Deploy the application
Copy-Item "$PWD\src\main\webapp\*" "$tomcat\webapps\ROOT" -Recurse -Force
Copy-Item "$PWD\build\classes\*" "$tomcat\webapps\ROOT\WEB-INF\classes" -Recurse -Force

# Start Tomcat
$env:JAVA_HOME = $java
$env:JRE_HOME = $java
$env:CATALINA_HOME = $tomcat
$env:CATALINA_BASE = $tomcat

& "$tomcat\bin\startup.bat"

---------------------------------

Set-Location "C:\Users\CSE\1033-Main\web-tech"

$env:JAVA_HOME = "C:\Users\CSE\1033\runtime\jdk\jdk-17.0.20.1+1"

& "$env:JAVA_HOME\bin\javac.exe" `
  -cp "selenium\selenium-server-4.36.0.jar" `
  -d "selenium\classes" `
  "selenium\GroceryApplicationTest.java"

& "$env:JAVA_HOME\bin\java.exe" `
  -cp "selenium\classes;selenium\selenium-server-4.36.0.jar" `
  com.grocery.test.GroceryApplicationTest