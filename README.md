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
### Linphone
#### Installation
> sudo apt-get install linphone-nogtk
#### Setup
By the first start the config file "~/.linphonerc" is created:
> linphonec

We'll have to disable ipv6 first to get things working with our FritzBox:
> linphonec&gt; ipv6 disable<br />
> ipv6 use disabled.<br />

Now it's time to configure our FritzBox as a proxy:
> linphonec&gt; proxy add<br />
> Adding new proxy setup. Hit ^D to abort.<br />
> Enter proxy sip address: <sip:fritz.box><br />
> Your identity for this proxy: sip:DoorPiFon@fritz.box<br />
> Do you want to register on this proxy (yes/no): yes<br />
> Specify register expiration time in seconds (default is 600):<br />
> Expiration: 600 seconds<br />
> Specify route if needed:<br />
> No route specified.<br />
> --------------------------------------------<br />
> sip address: sip:fritz.box<br />
> route:<br />
> identity: sip:DoorPiFon@fritz.box<br />
> register: yes<br />
> expires: 600<br />
> registered: no<br />
> --------------------------------------------<br />
> Accept the above proxy configuration (yes/no) ?: yes<br />
> Proxy added.<br />
> Add password...

For some reason linphonec doesn't remember that we've chosen to not use ipv6.
Therefore we'll have to add this to the config again manually.

Open "~/.linphonerc" (e.g. "vim .linphonerc") and add "use_ipv6=0" somewhere in the "[sip]" area.
## Configuration
### Properties
