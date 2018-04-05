#include <ESP8266HTTPClient.h>
#include <SocketIOClient.h>
#include <ArduinoJson.h>
#include <ESP8266WiFiMulti.h>
 
SocketIOClient client;
ESP8266WiFiMulti wifimulti;
HTTPClient httpGet;

int yellow = D8;
int green = D7;
int red = D6;

int LEDs[] = {D8, D7, D6, D5, D4, D3, D2, D1, D0};

char host[] = "192.168.43.131";  
int port = 3000;                  

extern String RID; // tên sự kiện
extern String Rfull; // json
String GET_packet = "";
 
unsigned long prevTime = 0;
long interval = 5000;

void setupNetwork() {
    WiFi.persistent(false);
    WiFi.mode(WIFI_STA);
//    wifimulti.addAP("BON FPT", "kemtuchon19k");
    wifimulti.addAP("BON VNPT", "kemtuchon19k");
    wifimulti.addAP("FPT Telecom", "dongthap");
    wifimulti.addAP("AnhTraiMua", "meochonhe");
//    wifimulti.addAP("HoangPhat_Pro", "20052010");

    uint8_t i = 0;
    while (wifimulti.run() != WL_CONNECTED && i++ < 20) delay(500);
    if (i == 21) {
        Serial.println("Can not connect wifi!");
        while (1) delay(500);
    }
    Serial.print("Wifi connected to ");    Serial.println(WiFi.SSID());
    
    Serial.print("ESP8266 IP: ");    Serial.println(WiFi.localIP());
}

void changeState(int pin, boolean st)
{
    if (st) {
      digitalWrite(pin, HIGH);
      Serial.println("Light on!");
    } 
    else {
      digitalWrite(pin, LOW);
      Serial.println("Light off !");
    }
}

void Light(String device, boolean st)
{
  if (device == "Light 1")
        changeState(LEDs[0], st);
  if (device == "Light 2")
        changeState(LEDs[1], st);
  if (device == "Light 3")
        changeState(LEDs[2], st);
  if (device == "Light 4")
        changeState(LEDs[3], st);
  if (device == "Light 5")
        changeState(LEDs[4], st);
  if (device == "Light 6")
        changeState(LEDs[5], st);
  if (device == "Light 7")
        changeState(LEDs[6], st);
  if (device == "Light 8")
        changeState(LEDs[7], st);
  if (device == "Light 9")
        changeState(LEDs[8], st);
}

void Filter(JsonObject& obj)
{
    const char* deviceName = obj["deviceName"];
    boolean state = obj["state"];
        Light(deviceName, state);
}

void parseJsonArray(String s)
{
  DynamicJsonBuffer bufferred(512);
  JsonArray& arr = bufferred.parseArray(s);
  if (arr.success())
  {
    for (int i = 0; i < arr.size(); i++)
    {
      JsonObject& object = arr[i];
      Filter(object);
    }
  }
  else Serial.println("parsing failed!!!");
}

void checkMSG()
{
  if (RID != "")
  {        
      StaticJsonBuffer<512> jsonBuffer;
      JsonObject& obj = jsonBuffer.parseObject(Rfull);
      if (obj.success())
      {
          client.send("d2s-ledchange",Rfull);
          Filter(obj);
      }
      else Serial.println("Parsing failed!!!");
  }
  else 
  {
    Serial.println("Parsing...");
  }
}

bool GETfromServer(String field)
{
    bool check = false;
    String serverAdd = host;
    httpGet.begin("http://" + serverAdd + ":3000/device/" + field);
    if (httpGet.GET())
      {
        GET_packet = httpGet.getString();   
        Serial.println(GET_packet); 
        parseJsonArray(GET_packet);
        check = true;
      }
    GET_packet = "";
    httpGet.end();
    return check;
}

void LEDsetup()
{
  for (int i = 0; i < 9; i++)
    pinMode(LEDs[i], OUTPUT);
}

void setup()
{
    Serial.begin(115200);
    LEDsetup();
    setupNetwork();
    client.connect(host, port);
    Serial.println("connected to server!");
//    client.send("c2s-ledchange","{\"id\":\"5ac5c52688102a039f6b925e\",\"deviceName\":\"Light 3\",\"position\":\"LIVINGROOM\",\"state\":1,\"connect\":false}");
//    delay(1000);
//    client.send("c2s-ledchange","{\"id\":\"5ac5c52688102a039f6b925e\",\"deviceName\":\"Light 3\",\"position\":\"LIVINGROOM\",\"state\":0,\"connect\":false}");
    GETfromServer("lights");
    GETfromServer("doors");
}

void loop()
{
  if (client.monitor())
  {
    Serial.println(RID);
    Serial.println(Rfull);
    checkMSG();
    if (!client.connected())
    {
      client.reconnect(host, port);
    }
  }
}
