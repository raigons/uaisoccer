# UAISOCCER [![Build Status](https://snap-ci.com/raigons/uaisoccer/branch/master/build_image)](https://snap-ci.com/raigons/uaisoccer/branch/master)
A project built on thoughtworks beach as part of study of spring

* Installation
  * [Install Ansible and Vagrant](https://github.com/raigons/uaisoccer#install-ansible-and-vagrant)
  * [Install Bower and Node Dependecies](https://github.com/raigons/uaisoccer#install-bower-and-node-dependencies)
* Running Project
  * [Running Vagrant](https://github.com/raigons/uaisoccer#running-vagrant)  
  * [Running Api](https://github.com/raigons/uaisoccer#run-api) 
  * [Running App](https://github.com/raigons/uaisoccer#run-app) 

## Install Ansible and Vagrant

1. Install [ANSIBLE](http://www.ansible.com "ansible")

2. Install [VAGRANT](http://www.vagrantup.com/ "vagrant") and [VirtualBox](https://www.virtualbox.org/wiki/Downloads "virtualbox")

## Running Vagrant

1. On terminal, move to your project folder `cd projectPath` and then run the following commands:
2. `$ vagrant up` to start you vagrant box.
3. `$ vagrant ssh` to acess your vagrant box with ssh.

## Install Bower and Node Dependecies 

1. [Run Vagrant](https://github.com/raigons/uaisoccer#running-vagrant) if it is not running yet.
2. `$ cd /vagrant/` to navigate to project folder.
3. `$ bower install` to install bower dependencies
4. `$ npm install` to install node dependencies

## Run API

1. [Run Vagrant](https://github.com/raigons/uaisoccer#running-vagrant) if it is not running yet.
2. `$ cd /vagrant/api` to navigate to api folder.
3. `$ gradle tomcatRun` to run tomcat container.

Now you can acess the API in the address: http://localhost:8080/api

## Run APP

1. [Run Vagrant](https://github.com/raigons/uaisoccer#running-vagrant) if it is not running yet.
2. `$ cd /vagrant/` to navigate to project folder.
3. `$ gulp serve` to run app server.
 
Now you can acess the APP in the address: http://localhost:9000

