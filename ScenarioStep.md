### Prov Krakend Step  
ในโครงการนี้จะเป็นการ Prov การทำงานของ Krakend สามารถรองรับการทำงานในหัวข้อต่างๆได้หรือไม่ โดยแบ่งออกเป็นหัวข้อดังนี้   
1. CORS (Cross Original Resource Sharing)  เป็นการ Allow ให้สามารถเข้าถึง API Gateway ได้จากเครื่องอื่นๆ ซึ่ง Modern browser ปัจจุบันป้องกันเรื่องนี้เอาไว้  
2. ป้องกันในด้าน Security  
    2.1 DDOS  
    2.2 Rate limit
3. Traffic Management   
  3.1 Rate Limit  
  3.2 Load Balance  
  3.3 Circuit Breaker   


### ก่อนเริ่มใช้งานโครงการนี้ให้ทำตามขั้นตอนดังต่อไปนี้   
**ก่อนเริ่มต้องเตรียมเครื่องมือดังนี้**  
1. Docker Desktop   
2. Visual Studio Code  
3. Apache JMeter (ใช้ในการรันทดสอบ Concurrent)  
4. Git ใช้ในการ Clone Project ลงที่เครื่อง

#### หลังจากที่ได้เตรียมเครื่องมือ 2 ข้อด้านบนแล้ว ให้ทำตามขั้นตอนต่อไปนี้   
A. โครงการนี้จะใช้ Docker Image ที่เป็น Community เนื่องจากว่า Enterpise จะต้องมี License file ในการ Activate เพื่อเข้าใช้งาน   
```
docker pull krakend:2.9.4  
```  
B. ก่อนการรันให้ทราบโครงสร้างเบื้องต้นของ โครงการที่ทำเอาไว้เพื่อ Prov หัวข้อด้านบนก่อน  ซึ่งประกอบไปด้วย   
 1. ใช้ Krakend Community เวอร์ชั่น 2.9.4  
 2. ทำ Service อย่างง่ายด้วย SpringBoot รันด้วย JDK 21 (ไม่มีการเชื่อมกับฐานข้อมูล)  


### เริ่มการทดสอบในแต่ละหัวข้อ ให้ทำตามขั้นตอนที่กำหนดและอธิบายไว้  
Clone โครงการนี้ลงไปที่เครื่องที่จะทำการทดสอบ  
```
git clone https://github.com/chairod/krakend-2.9.4.git
```  
สั่งเปิด Krakend API Service  
```
cd krakend-playground
docker-compose up
```
ให้เปิดโครงการนี้ด้วย Visual Studio Code เพื่อทำการทดสอบ และจะต้อง Install Extension ดังนี้  
> Live Server
> spring Boot tools
> Spring Boot Dashboard  
> Spring Initializr Java Support  


## Seucirity Section
  **CORs (Cross Original Resource Sharing)**  
  Script ในการทดสอบ CORs ของ Krakend อยู่ภายใน Folder **demoClientApp** ให้คลิกขวาที่ไฟล์ index.html แล้วเลือก **Open with Live Server** จะเปิดหน้าต่าง Web Browser ขึ้นมา  
  

  **DDOS & Rate Limit**  
  