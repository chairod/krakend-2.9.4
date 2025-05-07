## KRAKEND Playground  
ใช้ Image ที่เป็น Commercial เพราะตัว Enterprise จะต้องมี License ถึงจะสามารถเปิดรันได้  
A. Pull commercial krakend image จาก dockerhub   
```
docker pull krakend:2.9.4  
```
B. รัน Krakend จาก Image ที่ Pull จาก dockerhub ในข้อ A <font color="red">ก่อนจะใช้งาน Gateway ได้ ให้รัน docker compose ในขั้นตอน **โครงสร้างของโครงการ > ข้อ D ด้วย**</font>  
> **[Step 1] แก้ไข IP Address ที่อยู่ในไฟล์ configs/krakend/krakend.json**  
 **Step 1.1.** ให้หา IP Address ของเครื่อง ณ ปัจจุบันก่อน ซึ่งจากภาพด้านล่าง NB ที่ทดสอบต่อกับ WIFI จึงได้ IP **192.168.1140**  
    ![image](https://github.com/user-attachments/assets/ab3d9937-8147-45e9-b18f-5032f4bcc96b)  
 **Step 1.2.** ให้นำ IP **192.168.1.140** ไปแทนที่ในไฟล์ **configs/krakend/krakend.json**  (ให้แทนที่เฉพาะ **192.168.1.21** ในส่วนของ Port ให้คงไว้ตามเดิม)  
    ![image](https://github.com/user-attachments/assets/2727c1db-d4b4-408b-8413-31612d9bec27)  
 Note: ถ้ามีการเปลี่ยนแปลง Port จาก 8001, 8002, 8003 ให้เป็น Port อื่นๆจะทำให้ Krakend ไม่สามารถต่อไปกับ backend service ได้, หรือหากต้องการเปลี่ยนจริงๆ ให้เข้าไปแก้ไขไฟล์ **springboot/services/docker-compose.yml**  
![image](https://github.com/user-attachments/assets/03e46023-7697-4cee-a02e-2167a9e9d53f)




> **[Step 2] เปลี่ยนตำแหน่ง Current Directory ไปอยู่ที่  configs/krakend**  
```
cd configs/krakend
```
> **[Step 3] รันคำสั่งด้านล่างนี้เพื่อเปิด Krakend API Gateway**  
```
docker run --rm -p 8080:8080 --name docker-commu -v .:/etc/krakend krakend:2.9.4 run --config /etc/krakend/krakend.json
```
#### Note:  
> A. __-v .:/etc/krakend__  หมายถึง ต้องการจะ Binding Volumn ในตำแหน่ง Folder ปัจจุบันเข้าไปที่ /etc/krakend ซึ่งเป็น Default Directory ที่ Krakend จะเข้าไปอ่าน Configuration file  
> B. ถ้าต้องการรันแบบ Background ให้เติม Argument __-d__ นี้เข้าไป  
> C. หลังจากรัน Krakend แล้วสามารถตรวจสอบผลการรันได้จากการเรียก endpoint **http://[host:post]/__debug** หรือ **http://[host:port]/__echo**  
> D. **--rm** หลังจากที่ Stop docker container แล้วจะให้ลบ container name **docker-commu** ออกไปด้วย


> **[Step 4] เปิด backend service เพื่อให้ krakend เชื่อมต่อไปยัง API หลังบ้านได้**  
```
cd springboot/services
docker-compose up
```

> **[Step 5] หลังรันเสร็จแล้วให้เปิด Browser เพื่อทดสอบ**  
```
http://localhost:8080/api/v1/get_user_with_array
http://localhost:8080/api/v1/get_user_mapping
```

### เครื่องมือที่แนะนำก่อนการใช้งาน  
A. Visual Studio code  https://code.visualstudio.com/download  
 Install Extensions  
> Live Server - ใช้สำหรับ Click ขวาเพื่อรัน ไฟล์ <font color="red">**demoClientApp/index.html**</font>  
> spring Boot tools  - ใช้สำหรับแก้ไขโปรแกรมเพื่อสร้าง Service เพิ่มเติม <font color="red">**springboot/krakend-api**</font>  
> Spring Boot Dashboard  
> Spring Initializr Java Support  

B. Docker Desktop  

C. Apapache JMeter เป็นเครื่องมือใช้สำหรับ Simulate จำนวน Access User เพื่อใช้เทส Krakend ในเรื่องของ **Rate Limit, DDOS, Load Balance**  
https://jmeter.apache.org/download_jmeter.cgi  ให้โหลดที่เป็น Binaries (apache-jmeter-5.6.3.zip) หลังจากโหลดเสร็จแล้วให้กำหนด Path ชี้ไปยัง Folder ที่ Extract เพื่อที่จะสามารถสั่งรัน jmeter ที่ Command line ได้  
```
cd xxx/xx/xx/apache-jmeter-5.6.3/bin  
jmeter
```

E. JDK 21  
  เนื่องจาก Project Spring boot พัฒนาด้วย Java Version 21  

### โครงสร้างของโครงการ  
A. FOLDER: **configs/krakend** เป็น Configuration file สำหรับใช้ในการรัน krakend  
ก่อนการใช้คำสั่งด้านล่างให้ cd เข้ามาที่พาร์ท **configs/krakend** ก่อน  
```
docker run --rm --name krakend-playground -p 8088:8080 -v .:/etc/krakend krakend:2.9.4 run --config /etc/krakend/krakend.json
```

B. FOLDER: **demoClientApp** เป็นตัวอย่าง Client Application สำหรับเรียกใช้งาน krakend api gateway เพื่อตรวจสอบการทำงานของ CORS (Cross Original resource sharing)  
```
"extra_config": {
    "security/cors": {
      "allow_methods": ["POST","GET"],
      "allow_origins": ["localhost", "http://127.0.0.1:5500"],
      "allow_headers": [
        "Origin", "Authorization", "Content-Type"
      ],
      "max_age": "12h"
    }
  }
```

C. FOLDER: **springboot/krakend-api** เป็น Demo Service ที่พัฒนาด้วย  spring boot, Maven สำหรับจำรอง Service ง่ายๆเพื่อใช้ในการต่อเข้ากับ krakenD api gateway  
> พัฒนาด้วย Java Version 21  
> Apapache maven จัดการ Package (pom.xml)  
> ยังไม่มีการเชื่อมต่อกับฐานข้อมูล เป็นการ Generate ข้อมูลและ Response กลับ   

หากมีการแก้ไขและต้องการ Build โครงการนี้ไปใช้ใน ข้อ D ให้ cd เข้าไปยังพาร์ท **springboot/krakend-api** และรันคำสั่งด้านล่างนี้เพื่อให้ได้ jar file  
```
maven clean package
```  
jar file จะไปอยู่ที่ **targets/krakend-api-0.0.1.jar** และให้คัดลอกไปไว้ที่ **springboot/services/jar**  

D. FOLDER: **springboot/services** เป็น docker-compose ที่สร้าง Service แยกเอาไว้ เพื่อทดสอบการทำงานของ Load balance  ซึ่งโครงการนี้จะทดสอบ Krakend Community รองรับ **Load balance** เฉพาะ Round-Robin และ Stragtegy อื่นๆ เช่น weighted, circuit breaker, และ health checks  จะอยู่ใน Enterpise  
<font color="red">**Service นี้จะถูก build มาจาก โครงการ C. springboot/krakend-api**</font>  
<font color="red">cd เข้าไปที่ springboot/services แล้วสั่งรันคำสั่งด้านล่าง เพื่อเปิด Service</font>  
```
docker-compose up
```


E. KrakenD_integration_FullGuide.ppt  เป็นเอกสารการนำเสนอการทดสอบ Feature บางส่วนของ Krakend ซึ่งประกอบด้วย  
> A. วิธีการ Install  
> B. Configuration  
> C. Security Section  
> D. Traffic Management  
> E. Monitoring, Log, Analysis   


F. jmeter-concurrency.jmx  
Jmeter file template ที่สร้างเอาไว้เพื่อทดสอบ Krakend Gateway ในส่วนของ Rate limit, Load Balance  
เปิด Command-line ขึ้นมาแล้วพิมพ์ คำสั่งด้านล่างเพื่อเปิด JMeter และโหลด Template ในโครงการนี้เข้าไป เพื่อยิงทดสอบ  
```
jmeter
```
ให้เลือกไฟล์ **่jmeter-concurrency.jmx** ตามภาพ  
![image](https://github.com/user-attachments/assets/8f32f121-0049-4012-9022-9d4b1d3f8c71)   
กำหนดจำนวน User ที่ต้องการสร้าง เพื่อยิงเข้าไปใน API gateway **จากภาพใน 1 วินาทีมีจำนวน 100 User เข้าไปทำงานพร้อมกัน**
![image](https://github.com/user-attachments/assets/84c1b67d-1642-4847-b7bc-11d6f006224d)






