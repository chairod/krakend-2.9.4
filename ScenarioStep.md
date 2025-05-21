![image](https://github.com/user-attachments/assets/119a9034-ec37-486e-883e-10499f8da927)### Prov Krakend Step  
ในโครงการนี้จะเป็นการ Prov การทำงานของ Krakend สามารถรองรับการทำงานในหัวข้อต่างๆได้หรือไม่ โดยแบ่งออกเป็นหัวข้อดังนี้   
1. CORS (Cross Origin Resource Sharing)  เป็นการ Allow ให้สามารถเข้าถึง API Gateway ได้จากเครื่องอื่นๆ ซึ่ง Modern browser ปัจจุบันป้องกันเรื่องนี้เอาไว้  
2. ป้องกันในด้าน Security  
    2.1 DDOS  
    2.2 Rate limit
3. Traffic Management   
  3.1 Load Balance  
  3.2 Circuit Breaker
  3.3 Cluster


### ก่อนเริ่มใช้งานโครงการนี้ให้ทำตามขั้นตอนดังต่อไปนี้   
**เตรียมเครื่องมือดังนี้**  
1. Docker Desktop   
2. Visual Studio Code  
3. Apache JMeter (ใช้ในการรันทดสอบ Concurrent)  
4. Git ใช้ในการ Clone Project ลงที่เครื่อง

#### หลังจากที่ได้เตรียมเครื่องมือตามข้อด้านบนแล้ว ให้ทำตามขั้นตอนต่อไปนี้   
A. โครงการนี้จะใช้ Krakend Image ที่เป็น Community เนื่องจากว่า Enterpise จะต้องมี License file ในการ Activate เพื่อเข้าใช้งาน   
```
docker pull krakend:2.9.4  
```  
B. โครงสร้างเบื้องต้นของ โครงการที่ทำเอาไว้เพื่อ Prov แต่ละหัวข้อก่อนหน้า  ซึ่งประกอบไปด้วย   
 1. ใช้ Krakend Community เวอร์ชั่น 2.9.4  
 2. ทำ Service อย่างง่ายด้วย SpringBoot รันด้วย JDK 21 (ไม่มีการเชื่อมกับฐานข้อมูล)  


### เริ่มการทดสอบในแต่ละหัวข้อ ให้ทำตามขั้นตอนที่กำหนดและอธิบายไว้  
Clone โครงการนี้ลงไปที่เครื่องที่จะทำการทดสอบ  
```
git clone https://github.com/chairod/krakend-2.9.4.git
```  
สั่งเปิด Krakend API Service (จะต้องเปิด Docker Desktop ขึ้นมาก่อน)  
```
cd krakend-playground
docker-compose up
```
ให้เปิดโครงการนี้ด้วย Visual Studio Code เพื่อทำการทดสอบ และจะต้อง Install Extension ดังนี้  
> Live Server  
> spring Boot tools  
> Spring Boot Dashboard    
> Spring Initializr Java Support  

## Security Section
  **CORs (Cross Original Resource Sharing)**  
  Script ในการทดสอบ CORs ของ Krakend อยู่ภายใน Folder **demoClientApp** ให้คลิกขวาที่ไฟล์ index.html แล้วเลือก **Open with Live Server** จะเปิดหน้าต่าง Web Browser ขึ้นมา  
  ![image](https://github.com/user-attachments/assets/9c9ecb2a-379b-4982-886c-148e53a820ae)


  **DDOS & Rate Limit**  
  กำหนดจำนวน Request สูงสุดที่สามารถรับโหลดได้ในช่วงเวลาหนึ่ง เช่น ใน 1 วินาทีจะรับจำนวนคำขอสูงสุดที่ 100 คำขอ เป็นต้น  
จะใช้เครื่องมือ JMeter ในการทดสอบ  
ให้ดาวน์โหลด Apache JMeter https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.6.3.zip  และกำหนด Environment Variable ชี้มายังพาร์ท Bin  
![image](https://github.com/user-attachments/assets/b0544a70-a038-439a-8a45-2dddf344f61f)  
พิมพ์คำสั่งเพื่อเปิด Jmeter (เปิด Window Command line)   
```
Jmeter
```  
ให้เลือกไฟล์ **jmeter-concurrency.jmx** เข้าไปในโปรแกรม Jmeter  

ยิงทดสอบ 200 Request/1 วินาที ตอบ 503-Service Unavailable มาจำนวน 68 ครั้ง คิดเป็น 34%
![image](https://github.com/user-attachments/assets/5b88a14a-7ca2-4071-9623-573c8e865866)  
![image](https://github.com/user-attachments/assets/36de866d-0932-427f-806a-f4c8f7a93a98)  



## Trafic Management Section
 **Load Balance**  
 เปิด Web Browser แล้วนำลิ้งต่อไปนี้ไปวาง และให้กด F5 ย้ำไปเรื่อยๆ และตรวจสอบตรงค่า  **dockerName** ค่าที่ตอบกลับมาจะเปลี่ยนแปลงไป เนื่องจากการใช้ Round-Robin Strategy
```
http://localhost:8080/api/v1/load_balance_round_robin
```

**Circuit Breaker**  
เพื่อป้องกันไม่ให้ Backend ทำงานหนักจนเกินไป ซึ่งค่ากำหนดไว้คือ  
![image](https://github.com/user-attachments/assets/0e371ff2-065d-4ff5-80dc-2a000595b82d)  

**คำอธิบาย:** หากมีข้อผิดพลาดเกิดขึ้นจำนวน 5 ครั้งภายใน 10 วินาที krakend จะเปิด Circuit Break เพื่อหยุดการเรียก Backend พร้อมตอบกลับ 500 - Internal Server Error 
และกำหนดระยะเวลาที่ Krakend จะทดสอบนับจำนวน Error ที่เกิดขึ้นอีกครั้งในอีก 5 วินาที  
![image](https://github.com/user-attachments/assets/97efc412-6114-4187-b7ef-124d6ecbacc3)

จะใช้ JMeter ในการทดสอบให้เปิด Command-Line แล้วพิมพ์คำสั่งด้านล่าง  
```
JMeter
```
เลือกไฟล์ **jmeter-concurrency.jmx**  
จะเห็นว่ามีบางช่วงเวลาที่ API ตอบกลับทั้ง 200 - OK และ 500 - Internal Server Error
![image](https://github.com/user-attachments/assets/381f9058-1c4c-4124-856e-c4ae50a505be)  
เปอร์เซ็นต์ Error จะอยู่ที่ 24%
![image](https://github.com/user-attachments/assets/cb02e842-fa1e-4cef-b84f-806db9b438a2)  

หลังจากใช้ Jmeter ยิงจะเห็นว่ามีการเปิด Circuit break  
![image](https://github.com/user-attachments/assets/d3b04728-bda6-4a9a-87b5-9264c6d0b535)   


**การทำ Cluster**  
การรัน Cluster ของ Krakend ไม่มีความจำเป็นต้องลง Software ใดๆเพิ่มเติม ซึ่งใช้หลักการเพียง 2 ข้อ ได้แก่  
1. มี Font-End Load balancer ซึ่งจากตัวอย่างนี้จะใช้ nginx ในการทำ Load balance ที่อยู่ส่วนหน้า  
2. ในแต่ละ Instance ของ Krakend ให้รัน Configuration file เดียวกัน

การทดสอบนี้จะใช้ nginx (Font-end load balancer) ทำ Load balance และสร้าง Node ของ Krakend ด้วย Docker Container แยกออกเป็น 3 Nodes และในแต่ละ Node จะใช้ Configuration file เดียวกัน  
![image](https://github.com/user-attachments/assets/b317a147-aecf-40bf-a197-9b51519d226a)  

   

```
http://localhost:8998/api/v1/cluster
```
เข้าลิ้งด้านบนแล้วกด F5 ไปเรื่อยๆค่า **krakendNode** จะเปลี่ยนแปลงไปทุกครั้งที่มีการ Refresh  
![image](https://github.com/user-attachments/assets/cb820a0a-f83c-4cba-b46a-897b831287d9)  
![image](https://github.com/user-attachments/assets/3060efc8-4c43-47ac-bfd0-ab639f7f0e4b)  







  

