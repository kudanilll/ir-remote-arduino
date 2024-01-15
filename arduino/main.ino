#include <IRremote.h>
#define RECV_PIN 12
#define LED_PIN 13

IRrecv receiver(RECV_PIN);
decode_results result;
bool lamp_state = false;

void setup() {
	Serial.begin(9600);
	receiver.enableIRIn();
	pinMode(LED_PIN, OUTPUT);
	Serial.println("program started!");
}

void loop() {
	if(receiver.decode(&result)) {
		Serial.print("data: ");
		Serial.println(result.value, HEX);
		String data = String(result.value, HEX);
		data.trim();
		if(data == "511d424f") {
			lamp_state = !lamp_state;
			digitalWrite(LED_PIN, lamp_state ? HIGH : LOW);
		}
		receiver.resume();
	}
	delay(100);
}