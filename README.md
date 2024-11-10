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
Install Java 21
Installation via SDKMan for _root_.
Installation as _root_ is needed in order to get things working with _pigpio_.

#### Install as root

    sudo su -
    export SDKMAN_DIR="/usr/local/sdkman" && curl -s "https://get.sdkman.io" | bash
    source "/usr/local/sdkman/bin/sdkman-init.sh"
    sdk install java

#### Make sdkman java available for regular user
Add to ".bashrc" of your regular user:

    export SDKMAN_DIR="/usr/local/sdkman"
    [[ -s "/usr/local/sdkman/bin/sdkman-init.sh" ]] && source "/usr/local/sdkman/bin/sdkman-init.sh"
### Execution
It's important to run with _sudo_ permissions in order to initialize pin via _pigpio_.
### Setup doorbell as systemd serivce
Create a _doorbell.service_ in _/etc/systemd/system_.

    [Unit]
    Description=Doorbell is a simple java application ringing a door bell via VoIP call
    [Service]
    ExecStart=/usr/local/sdkman/candidates/java/current/bin/java -jar /opt/doorbell/doorbell.jar
    Type=simple
    WorkingDirectory=/opt/doorbell
    [Install]
    WantedBy=default.target

Run

    sudo systemctl daemon-reload
    sudo systemctl enable doorbell.service
## Configuration
### Properties
