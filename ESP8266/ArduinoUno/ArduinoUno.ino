#include <SoftwareSerial.h>
#include <SerialCommand.h>
#include <ArduinoJson.h>
#include <Servo.h>


const byte rx = 3, tx = 2;

SoftwareSerial mySerial(rx, tx);
SerialCommand sCmd(mySerial);

int digitalPin[] = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
int servo = digitalPin[7];
//Servo controlDoor;

// sử dụng cho cảm biến hồng ngoại A0 -> A3
int analogPin[] = {A0, A1, A2, A3, A4, A5};

// 0: mở, 1: đóng
boolean firstState[] = {false , false, false, false, false};
boolean lastState[] = {false , false, false, false, false};

String data = "";
boolean readData = false;

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

void openDoor()   {  }

void closeDoor()  {  }

void Door(int pin, boolean st)
{
  if (st == true)
    openDoor();
  else
    closeDoor();
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
    readData = true;
  }
  else Serial.println("parsing failed!!!");
}

void pinSetup()
{
  for (int i = 0; i < 10; i++)
  {
    pinMode(digitalPin[i], OUTPUT);
  }
  //    servo.attach(controlDoor);
}

void readfromESP ()
{
  data = sCmd.next();
  Serial.println("Receive: " + data);
  parseJsonObject(data);
  delay(200);
  sCmd.clearBuffer();
}

void readfromSocket ()
{
  data = sCmd.next();
  Serial.println("Receive: " + data);
  parseJsonObject(data);
//  sCmd.clearBuffer();
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

void checkDoor()
{
  //  if (readData) {
  for (int i = 0; i < 6; i++)
  {
    int r = analogRead(analogPin[i]);
    firstState[i] = r < 200;
  }
  for (int pin = 0; pin < 6; pin++)
  {
    if (lastState[pin] != firstState[pin])
    {
      sendJson("SEN-A2E", "SENSOR", pin, firstState[pin]);
    }
    lastState[pin] = firstState[pin];
  }
  //  }
}

void setup() {
  Serial.begin(19200);
  mySerial.begin(19200);
  mySerial.print("connect");
  mySerial.print('\r');
  mySerial.print("Begin");
  mySerial.print('\r');
  sCmd.addCommand("E2A", readfromESP);
  sCmd.addCommand("E2A-S", readfromSocket);
  Serial.println("Ready...");
}

void loop()
{
  sCmd.readSerial();
  checkDoor();
}
