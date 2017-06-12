import machine
import time
import sys
import network
import ubinascii
import socket

WIFINETWORK = 'maprdemo'
WIFIPASSWD = 'maprdemo'
ANKI_CONTROLLER_IP = '192.168.12.138'
ANKI_CONTROLLER_PORT = 7877
SKULL_MAC_ADDRESS = 'a0-20-a6-17-f3-63'

class SW40(object):
    def __init__(self, pin):
        self.pin = pin
        self.pin.irq(trigger=machine.Pin.IRQ_FALLING | machine.Pin.IRQ_RISING, handler=self.callback)
        self.count = 0

    def callback(self , pin):
        self.count += 1

def wifi_setup():
    nic = network.WLAN(network.STA_IF)
    nic.active(True)
    return (nic)

def wifi_connect(nic):
    nic.connect(WIFINETWORK, WIFIPASSWD)

def wifi_is_connected(nic):
    return nic.isconnected()

def http_post(host, port, data):
    addr = socket.getaddrinfo(host, port)[0][-1]
    s = socket.socket()
    s.connect(addr)
    s.send(bytes(data, 'utf8'))
    s.close()

def main():
    sensor = SW40(machine.Pin(14, machine.Pin.IN))
    ledpin = machine.Pin(2, machine.Pin.OUT)
    macaddr = ubinascii.hexlify(network.WLAN().config('mac'),'-').decode()

    car = "Skull"
    if SKULL_MAC_ADDRESS == macaddr :
        car = "GroundShock"

    print("my macaddr: %s" % macaddr)
    nic = wifi_setup()
    try:
        while True:
            while (wifi_is_connected(nic) == False):
                print("connecting to wifi "+ WIFINETWORK)
                wifi_connect(nic)
                time.sleep(3)

            perhalfsec = ((sensor.count + 10) / 10)
            sleepamt = int(500 / perhalfsec)
            sensor.count = 0

            for i in range(0, perhalfsec):
                time.sleep_ms(sleepamt)
                ledpin.low()
                time.sleep_ms(sleepamt)
                ledpin.high()

            data = " "
            dlen = len(data)
            msg = "POST /setSpeed/"+ car +"/"+ str(sensor.count * 2.5) +" HTTP/1.1\r\n" \
                    "User-Agent: curl/7.38.0\r\n" \
                    "Host: localhost:8082\r\n" \
                    "Accept: */*\r\n" \
                    "Content-Length: " + str(dlen) + "\r\n\r\n" + str(data)
            #print("sleep amt is %d, persec %d" % (sleepamt, persec))
            print("posting %s - from %s" % (msg ,  macaddr) )
            http_post(ANKI_CONTROLLER_IP, ANKI_CONTROLLER_PORT, msg)

    except KeyboardInterrupt:
        print("exiting...")
        sys.exit(0)
if __name__ == '__main__':
    main()
