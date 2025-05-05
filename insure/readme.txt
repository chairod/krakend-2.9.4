A. กำหนดให้หน้าบ้านต้อง call ยังไง
 Done
 -> CORs(Allow CORs)
 
B. authen ยังไง - เพื่อเข้า api หลังบ้าน & C. มีหน้าที่แจกจ่าย authen ให้
  A. authentication token
    B. getOrder authen token

D. ป้องกันในด้าน security /ddos
  > กำหนด Server ที่เข้ามา ต่อ
  > Limit
 
E. monitoring, log ในการเข้าถึง
   > ดูจำนวน Req./Endpoint [ตอบที่ Grafana]
   > Check backend working?
     อยากให้แสดงตามลิ้งนี้ (กลมว่าน่าจะเป็น Grafana)
      https://grafana.com/api/dashboards/18998/images/14403/image

      ตัวอย่างการใช้ Datasource จากหลายๆแหล่ง Prometheus,influx
      https://grafana.com/solutions/kubernetes/?pg=dashboards&plcmt=featured-dashboard-1



F. ทำ cluster / loadbalance ได้



เพิ่มเติม:
 Mapping การเรียกใน docker compose
 เปรียบระหว่าง Comminity & Enterpise

 