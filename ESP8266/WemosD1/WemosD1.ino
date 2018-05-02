#include <SoftwareSerial.h>
#include <SerialCommand.h>
#include <SocketIOClient.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WiFiMulti.h>
#include <ArduinoJson.h>

ESP8266WiFiMulti wifimulti;
SocketIOClient socketClient;
HTTPClient httpClient;

const byte rx = D1;
const byte tx = D2;

SoftwareSerial mySerial(rx, tx, false, 256);
SerialCommand sCmd(mySerial);

char host[] = "172.16.199.170";
//char host[] = "192.168.1.4";
int port = 3000;

extern String RID; // tên sự kiện
extern String Rfull; // json

boolean authenticated = false;
String md5hash = "45be731e36db60bbc596c63cff413a7c";

void setupNetwork()
{
  WiFi.persistent(false);
  // access to a wifi
  WiFi.mode(WIFI_STA);
  wifimulti.addAP("HoangPhat_Pro", "20052010");
  wifimulti.addAP("FPT Telecom", "dongthap");
  wifimulti.addAP("AnhTraiMua", "meochonhe");
  wifimulti.addAP("ANDY", "01666988779");

  // connecting
  uint8_t i = 0;
  while (wifimulti.run() != WL_CONNECTED && i++ < 20) delay(500);
  if (i == 21) {
    Serial.println("Can not connect wifi!");
    while (1) delay(500);
  }
  //connected
  Serial.print("\nWifi connected to ");
  Serial.println(WiFi.SSID());
  Serial.print("ESP8266 IP: ");
  Serial.println(WiFi.localIP());
}


void listenSocketIO()
{
  if (RID == "authenticated") {
    DynamicJsonBuffer bufferred;
    JsonObject& auth = bufferred.parseObject(Rfull);
    if (auth.success()) {
      authenticated = auth["auth"];
      loadDevices();
      Serial.println("connected to server!");
    }
  }
  else Serial.println("Waiting for imcoming data...");

  if (authenticated)
  {
    if (RID == "s2d-change")
    {
      mySerial.print("E2A-S");
      mySerial.print('\r');
      mySerial.print(Rfull);
      mySerial.print('\r');
      delay(100);
    }
    else Serial.println("Waiting for imcoming data...");
  }

}
void loadDevices()
{
  String hoststr = host;
  Serial.println("Get POST from http://" + hoststr + ":3000/devices/");
  httpClient.begin("http://" + hoststr + ":3000/devices/");
  httpClient.addHeader("Content-Type", "application/json");
  String query = "{}";
  int httpCode = httpClient.POST(query);

  if (httpCode == 200)
  {
    String payload = httpClient.getString();
    Serial.println(payload);
    parseJsonArray(payload);
  }
  httpClient.end();
}
void parseJsonArray(String s)
{
  DynamicJsonBuffer bufferred;
  JsonObject& deviceArr  = bufferred.parseObject(s);
  JsonArray& arr = deviceArr["devices"];
  if (arr.success()) {
    for (int i = 0; i < arr.size(); i++)
    {
      JsonObject& device = arr[i];
      JsonArray& modules = device["modules"];
      sendToArduino(modules);
    }
  }
  else {
    Serial.println("parsing failed!!!");
    loadDevices();
  }
}

String createJsonString(String type, int pin, boolean state)
{
  DynamicJsonBuffer buff;
  JsonObject& data = buff.createObject();
  data["type"] = type;
  data["pin"] = pin;
  data["state"] = state;
  String json;
  data.printTo(json);
  return json;
}

void sendToArduino(JsonArray& modules) {
  for (int i = 0; i < modules.size(); i++)
  {
    JsonObject& m = modules[i];
    String type = m["type"];
    int pin = m["pin"];
    boolean state = m["state"];
    String json = createJsonString(type, pin, state);
    Serial.println("Send:" + json);
    mySerial.print("E2A");
    mySerial.print('\r');
    mySerial.print(json);
    mySerial.print('\r');
    delay(200);
  }
}

void readfromArduino()
{
  String s = sCmd.next();
  Serial.println("Receive Arduino: " + s);
  DynamicJsonBuffer bufferred(512);
  JsonObject& object = bufferred.parseObject(s);
  if (object.success())
  {
    String type = object["type"];
    int pin = object["pin"];
    boolean state = object["state"];
    socketClient.send("d2s-change", createJsonString(type, pin, state));
    delay(200);
  }
  else
  {
    mySerial.print("E2A-ERROR");
    mySerial.print('\r');
    mySerial.print(s);
    mySerial.print('\r');
    Serial.println("Parsing failed!");
  }
  sCmd.clearBuffer();
}

void listenSensorArduino()
{
  String s = sCmd.next();
  //  Serial.println("Sensor send: " + s);
  DynamicJsonBuffer bufferred(512);
  JsonObject& object = bufferred.parseObject(s);
  if (object.success())
  {
    String type = object["type"];
    int pin = object["pin"];
    boolean state = object["state"];

    socketClient.send("d2s-sensor", createJsonString(type, pin, state));
    //    delay(300);
  }
  sCmd.clearBuffer();
}
void connectArduino() {
  String s = sCmd.next();
  Serial.println("Connect Arduino: " + s);
}


void connectServer() {
  DynamicJsonBuffer buff;
  JsonObject& authen = buff.createObject();
  authen["token"] = md5hash;
  String json;
  authen.printTo(json);
  socketClient.send("authorization", json);
}

void setup()
{
  Serial.begin(19200);
  mySerial.begin(19200);
  setupNetwork();
  socketClient.connect(host, port);
  connectServer();
  sCmd.addCommand("connect", connectArduino);
  sCmd.addCommand("A2E", readfromArduino);
  sCmd.addCommand("SEN-A2E", listenSensorArduino);
}

void loop()
{
  if (!socketClient.connected())
  {
    socketClient.reconnect(host, port);
    connectServer();
  }

  if (socketClient.monitor())
  {
    listenSocketIO();
  }

  sCmd.readSerial();
}
