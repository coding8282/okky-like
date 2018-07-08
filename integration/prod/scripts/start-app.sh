#!/usr/bin/env bash

sudo chmod +x /home/ec2-user/okky-like-1.0.0.jar
sudo ln -sf /home/ec2-user/okky-like-1.0.0.jar /etc/init.d/okky-like
sudo service okky-like start
sleep 10s