# How-to-run
## Downloading .jar
- Download actual version of project
- Write down command `-java -jar x.x.x-RELEASE.jar` whehre __x.x.x__ is actual version. You can check in in __Releases__
## Cloning project
- Clone project in your directory.
- After downloading configure server port in __application.properties__ file. By default it is 80.
- Download __maven__ if you havn't it.
- In directory where is located __pon.xml__ write down command `mvn clean package`
- If ok, would be created new directory __Target__, Open it.
- Write down command `-java -jar x.x.x-RELEASE.jar` whehre __x.x.x__ is actual version. You can check in in __Releases__
