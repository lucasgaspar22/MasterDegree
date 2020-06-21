#!/bin/bash

nohup java -jar ws3d-all-0.0.1.jar &
sleep 8
mono ./ClarionApp/bin/Release/ClarionApp.exe
