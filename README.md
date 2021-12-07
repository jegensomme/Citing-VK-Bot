<h2>Citing VK Chat Bot</h2>
***

**This bot cites the text sent to it**

![img.png](example.png)

<h3>Instructions for launching the application</h3>

**1) Clone repository**

```git clone https://github.com/jegensomme/Citing-VK-Bot.git```

**2) Build project with maven wrapper**

**For Unix system:** ```./mvnw clean package -Dmaven.test.skip=true```

**For Windows:** ```mvnw.cmd clean package -Dmaven.test.skip=true```

**3) Set the configuration settings in config/callback.properties**

| Property key          | Description         |
| ----------------------| ------------------- |
| callback.access-token | Access Token        |
| callback.confirmation | Confirmation string |            | 
| callback.secret       | Secret key          |

**4) Run application**

```java -DAPP_ROOT=? -Dfile.encoding=UTF8 -jar target\citing-vk-bot-1.0.jar```

**Insert your path to application root instead of ? in ```-DAPP_ROOT=?```**