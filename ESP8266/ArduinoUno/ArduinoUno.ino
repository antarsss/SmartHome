#include <SoftwareSerial.h>
#include <SerialCommand.h>
#include <ArduinoJson.h>
#include <Servo.h>
const byte rx = 3, tx = 2;

SoftwareSerial mySerial(rx, tx);
SerialCommand sCmd(mySerial);
Servo servo;
int digitalPin[] = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
int servoPin = digitalPin[7];


// sử dụng cho cảm biến hồng ngoại A0 -> A3
int analogPin[] = {A0, A1, A2, A3, A4, A5};

// 0: mở, 1: đóng
boolean firstState[] = {false , false, false, false};
boolean lastState[] = {false , false, false, false};

void setState(int pin, boolean state) {
  if (state)
  {
    digitalWrite(pin, HIGH);
    Serial.println(pin + " - ON");
  }
  else
  {
    digitalWrite(pin, LOW);
    Serial.println(pin + " - OFF");
  }
}

void Light(int pin, boolean st)
{
  setState(pin, st);
  sendJson("A2E", "LIGHT", pin, st);
}

void Door(int pin, boolean st)
{
  servo.write(st ? 90 : 0);
  sendJson("A2E", "SERVO", pin, st);
}

void classify(JsonObject& obj)
{
  String type = obj["type"];
  int pin = obj["pin"];
  boolean state = obj["state"];

  if (type == "LIGHT") {
    Light(pin, state);
  }

  if (type == "SERVO") {
    Door(pin, state);
  }
}

void parseJsonObject(String data)
{
  StaticJsonBuffer<256> bufferred;
  JsonObject& object = bufferred.parseObject(data);
  if (object.success())
  {
    classify(object);
  }
  else Serial.println("parsing failed!!!");
}

void pinSetup()
{
  for (int i = 0; i < 10; i++)
  {
    pinMode(digitalPin[i], OUTPUT);
  }

}

void readfromESP ()
{
  String data = sCmd.next();
  Serial.println("Receive: " + data);
  parseJsonObject(data);
  delay(200);
  sCmd.clearBuffer();
}

void readfromSocket ()
{
  String data = sCmd.next();
  Serial.println("Receive: " + data);
  parseJsonObject(data);
  sCmd.clearBuffer();
}

void sendJson(String event, String type, int pin, boolean state)
{
  StaticJsonBuffer<256> buff;
  JsonObject& data = buff.createObject();
  data["type"] = type;
  data["pin"] = pin;
  data["state"] = state;
  mySerial.print(event);
  mySerial.print('\r');
  data.printTo(mySerial);
  mySerial.print('\r');
}

void turnOnPIR(boolean isOn)
{
  firstState[3] = analogRead(analogPin[3]) < 400;
  if (lastState[3] != firstState[3] && isOn)
  {
    sendJson("SEN-A2E", "SENSOR", 3, firstState[3]);
    Serial.print("3");
    Serial.print(": ");
    Serial.print(firstState[3]);
    Serial.print("\n");
    lastState[3] = firstState[3];
    delay(300);
  }
}

void checkDoor()
{
  // main door
  int D2 = analogRead(analogPin[1]);
  // window in diningroom
  int D3 = analogRead(analogPin[2]);
  // maindoors
  int D4 = analogRead(analogPin[3]);

  int sensors[] = {D2, D3, D4};
  int n = sizeof(sensors) / sizeof(int);

  for (int i = 0; i < n ; i++)
  {
    firstState[i] = sensors[i] > 30;
    
    if (lastState[i] != firstState[i])
    {
      sendJson("SEN-A2E", "SENSOR", i, firstState[i]);
      Serial.print(i);
      Serial.print(": ");
      Serial.print(firstState[i]);
      Serial.print("\n");
      lastState[i] = firstState[i];
      delay(300);
    }
  }  
  turnOnPIR(firstState[2]);
}

void setup() {
  Serial.begin(19200);
  mySerial.begin(19200);
  mySerial.print("connect");
  mySerial.print('\r');
  mySerial.print("successfully");
  mySerial.print('\r');
  sCmd.addCommand("E2A", readfromESP);
  sCmd.addCommand("E2A-S", readfromSocket);
  Serial.println("Ready...");
  servo.attach(servoPin);
}
int ko = 0;
void loop()
{
  sCmd.readSerial();
  checkDoor();
}
