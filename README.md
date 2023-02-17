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
Install Java 17
Installation via SDKMan
> curl -s "https://get.sdkman.io" | bash
> 
> $ source "$HOME/.sdkman/bin/sdkman-init.sh"
> 
> $ sdk install java
### Execution
It's important to run with _sudo_ permissions in order to initialize pin via _pigpio_.
## Configuration
### Properties
