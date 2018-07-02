#!/usr/bin/env bash

sudo ln -sf /home/ec2-user/okky-like-1.0.0-SNAPSHOT.jar /etc/init.d/okky-like
sudo service okky-like start