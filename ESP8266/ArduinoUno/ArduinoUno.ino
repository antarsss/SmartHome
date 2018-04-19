#include <SoftwareSerial.h>
#include <SerialCommand.h>
#include <ArduinoJson.h>
#include <Servo.h>


const byte rx = 3, tx = 2;
 
SoftwareSerial mySerial(rx, tx); 
SerialCommand sCmd(mySerial);

int digitalPin[] = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
int servo = digitalPin[7];
Servo controlDoor;

// sử dụng cho cảm biến hồng ngoại A0 -> A3
int analogPin[] = {A0, A1, A2, A3, A4, A5};

// 0: mở, 1: đóng
boolean firstState[] = {false , false, false, false, false};
boolean lastState[] = {false , false, false, false, false};

String data = "";

void sendtoESP(String data)
{
    mySerial.print("A2ESP");
    mySerial.print('\r');
    mySerial.print(data);
    mySerial.print('\r');
}

void setState(int pin, boolean state){
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
    sendtoESP(data);
}

// pos là position
// st là state

void Light(int pin, boolean st)
{ 
    setState(pin, st);
}

void openDoor()   {  }

void closeDoor()  {  }

void Door(String deviceName, String pos, boolean st)
{
    if (pos == "LIVINGROOM")
    {
        if (deviceName == "Main Door")
        {
            if (st == true)
                openDoor();
            else closeDoor();
            sendtoESP(data);
        }
    }
}

void Filter(JsonObject& obj)
{
    String type = obj["type"];
    int pin = obj["pin"]; 
    boolean state = obj["state"];
    
    if (type == "LED"){
      Light(pin, state);
      Serial.println(state);
    }
        
//    if (type == "SERVO")
////        Door(deviceName, pos, state);
//    if (type == "SENSOR")
////        Door(deviceName, pos, state);
}

void parseJsonObject(String s)
{
    StaticJsonBuffer<512> jsonBuffer;
    JsonArray& arr = jsonBuffer.parseArray(s);
    if (arr.success())
    {
        for (int i = 0; i < arr.size(); i++)
        {
            JsonObject& obj = arr[i];
            Filter(obj);    
        }
    }
    else Serial.println("parsing failed!!!");
}

void pinSetup()
{
    // set pin mode to Digital
    for (int i = 0; i < 10; i++)
    {
        pinMode(digitalPin[i], OUTPUT);
    }
  
//    servo.attach(controlDoor);
}

void readfromESP ()
{
    data = sCmd.next(); //Chỉ cần một dòng này để đọc tham số nhận đươc
    Serial.println("READ: " + data);
    parseJsonObject(data);
}
void createJsonObject(String type, int pin, boolean state)
{
      DynamicJsonBuffer buff;
      JsonArray& arr = buff.createArray();
      JsonObject& data = buff.createObject();
      data["type"] = type;
      data["pin"] = pin;
      data["state"] = state;
      arr.add(data);
      // send
      mySerial.print("A2ESP");
      mySerial.print('\r');
      arr.printTo(mySerial);
      mySerial.print('\r');              
}

int r, r1, r2, r3, r4;

void checkDoor()
{
    // main door
    r = analogRead(analogPin[0]);
    // window in livingroom
    r1 = analogRead(analogPin[1]);
    // window in diningroom
    r2 = analogRead(analogPin[2]);
    // window in bedroom
    r3 = analogRead(analogPin[3]);
    // pir
    r4 = analogRead(analogPin[4]);
    
    if (r > 200)
      firstState[0] = false;
    else firstState[0] = true;
    
    if (r1 > 200)
      firstState[1] = false;
    else firstState[1] = true; 
      
    if (r2 > 200)
      firstState[2] = false;
    else firstState[2] = true;    
      
    if (r3 > 200)
      firstState[3] = false;
    else firstState[3] = true;

    if (r4 < 500)
      firstState[4] = false;
    else firstState[4] = true;
    
    for (int i = 0; i < 5; i++)
    {
        if (lastState[i] != firstState[i])
        {    
            createJsonObject("SENSOR", i, firstState[i]);
        }
        lastState[i] = firstState[i];
    }
} 

void setup() {
    Serial.begin(19200);
    mySerial.begin(19200);
    sCmd.addCommand("ESP2A", readfromESP);
    Serial.println("Ready...");
}

void loop() 
{
    sCmd.readSerial();  
    checkDoor();  
}
