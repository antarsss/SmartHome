#include <SocketIOClient.h>
#include <ESP8266HTTPClient.h>
#include <ArduinoJson.h>
#include <ESP8266WiFiMulti.h>
 
SocketIOClient client;
ESP8266WiFiMulti wifimulti;
HTTPClient httpClient;

int yellow = D8;
int green = D7;
int red = D6;

int LEDs[] = {D8, D7, D6, D5, D4, D3, D2, D1, D0};

//char host[] = "172.16.199.170"; 
char host[] = "rpi-chuna.strangled.net";   
int port = 3000;                  

extern String RID; // tên sự kiện
extern String Rfull; // json
 
unsigned long prevTime = 0;
long interval = 5000;

void setupNetwork()
{
    WiFi.persistent(false);
    WiFi.mode(WIFI_STA);
//    wifimulti.addAP("BON FPT", "kemtuchon19k");
//    wifimulti.addAP("BON VNPT", "kemtuchon19k");
//    wifimulti.addAP("FPT Telecom", "dongthap");
    wifimulti.addAP("AnhTraiMua", "meochonhe");
    wifimulti.addAP("HoangPhat_Pro", "20052010");
    wifimulti.addAP("ANDY", "01666988779");

    uint8_t i = 0;
    while (wifimulti.run() != WL_CONNECTED && i++ < 20) delay(500);
    if (i == 21) {
        Serial.println("Can not connect wifi!");
        while (1) delay(500);
    }
    Serial.print("Wifi connected to ");    
    Serial.println(WiFi.SSID());
    
    Serial.print("ESP8266 IP: ");    
    Serial.println(WiFi.localIP());
}

void changeState(int pin, boolean st)
{
    if (st) 
    {
        digitalWrite(pin, HIGH);
        Serial.println("Light on!");
    } 
    else 
    {
        digitalWrite(pin, LOW);
        Serial.println("Light off !");
    }
    client.send("d2s-change",Rfull);
    
}

void Light(String device, String position, boolean st)
{   
    Serial.println(device);
    Serial.println(position);
    Serial.println(st);
    if(position == "LIVINGROOM")
    {
        if (device == "Light 1")
            changeState(LEDs[0], st);
        else if (device == "Light 2")
            changeState(LEDs[1], st);
    }
    else if(position == "BEDROOM")  
    {
        if (device == "Light 1")
            changeState(LEDs[0], st);
        else if (device == "Light 2")
            changeState(LEDs[1], st);
    }
    else if(position == "DININGROOM") 
    {
        if (device == "Light 1")
            changeState(LEDs[0], st);
        else if (device == "Light 2")
            changeState(LEDs[1], st);
    }
    else if(position == "BATHROOM") 
    {
        if (device == "Light")
        {
            changeState(LEDs[0], st);
        }
    }
}

void parseJsonArray(String s)
{
    DynamicJsonBuffer bufferred(512);
    JsonObject& object = bufferred.parseObject(s);
    JsonArray& arr = object["devices"];
    if (arr.success())
        for (int i = 0; i < arr.size(); i++)
        {
            JsonObject& device = arr[i];
            Filter(device);
        }
    else Serial.println("parsing failed!!!");
}

void Filter(JsonObject& obj)
{
    String deviceName = obj["deviceName"];
    String deviceType = obj["deviceType"];
    String position = obj["position"];
    boolean state = obj["state"];
    if(deviceType == "LIGHT")
        Light(deviceName, position, state);
}


void listenSocketIO()
{
    if (RID != "")
    {        
        StaticJsonBuffer<512> jsonBuffer;
        JsonObject& obj = jsonBuffer.parseObject(Rfull);
        if (obj.success())
        {
            Filter(obj);         
        }
        else Serial.println("Parsing failed!!!");
    }
    else Serial.println("Parsing...");
}

void loadDeviceByType(String deviceType)
{ 
    String serverAdd = host;
    Serial.println("Get POST from http://" + serverAdd + ":3000/devices/"); 
    httpClient.begin("http://" + serverAdd + ":3000/devices/");
    
    httpClient.addHeader("Content-Type", "application/json");

    String query = "{\"deviceType\":\"" + deviceType + "\"}";
    Serial.println("With query " + query);   
     
    int httpCode = httpClient.POST(query);
//    Serial.println(httpCode);   //Print HTTP return code
    
    if(httpCode == 200){
        String payload = httpClient.getString();
        Serial.println(payload);   
        parseJsonArray(payload);
    }
    httpClient.end();
}

void LEDsetup()
{
    for (int i = 0; i < 9; i++){
        pinMode(LEDs[i], OUTPUT);
    }
}

void setup()
{
    Serial.begin(115200);
    LEDsetup();
    setupNetwork();
    client.connect(host, port);
    Serial.println("connected to server!");
    loadDeviceByType("DOOR");
    loadDeviceByType("LIGHT");
}

void loop()
{
    if (client.monitor())
    {
        listenSocketIO(); 
    }
    if (!client.connected())
    {
        client.reconnect(host, port);
    }
}
