# doorbell
## Description
### Idea
Idea of the project is to use a Raspberry Pi 4 as a doorbell. For this the door push button is connected to one of the 
GPIO pins. The button push is read and a SIP/VoIP call to the phone is triggered.
Everything is documented and visualized in a web ui which is implemented with SpringBoot.
### Outlook
At a later point in time this project might be enhanced by a KNX connection to also handle and trigger other things via 
KNX bus.
## Prerequisites / installation
### RaspberryPi 4
### Java
Install Java 11
> sudo apt install default-jdk
### Pi4j
Install Pi4j
See: https://pi4j.com/1.2/install.html
> curl -sSL https://pi4j.com/install | sudo bash
### Wiring Pi
Install Wiring Pi
See: http://wiringpi.com/download-and-install/
> sudo apt-get install wiringpi