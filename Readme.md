# JavaFX chat
Create jfx clients who can send messages to server.
More than three clients can't connect to server, and they would wait until server will be available.
Uses Socket for connecting Server and Clients.
## How to run
First, create executable jar for client application:
```
mvn assembly:assembly
```
Then start Server JavaFX application:
```
mvn javafx:run
```
After creation clients will be asked for password. Password is ```ssau```.