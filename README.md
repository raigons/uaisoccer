[![Build Status](https://snap-ci.com/raigons/uaisoccer/branch/master/build_image)](https://snap-ci.com/raigons/uaisoccer/branch/master) [![Code Climate](https://codeclimate.com/github/raigons/uaisoccer/badges/gpa.svg)](https://codeclimate.com/github/raigons/uaisoccer) [![Test Coverage](https://codeclimate.com/github/raigons/uaisoccer/badges/coverage.svg)](https://codeclimate.com/github/raigons/uaisoccer/coverage)

# UAISOCCER
A project built on thoughtworks beach as part of study of spring

* Installation
  * [Install Ansible and Vagrant](https://github.com/raigons/uaisoccer#install-ansible-and-vagrant)
* [Running Project](https://github.com/raigons/uaisoccer#running-project)  

## Install Ansible and Vagrant

1. Install [ANSIBLE](http://www.ansible.com "ansible")

2. Install [VAGRANT](http://www.vagrantup.com/ "vagrant") and [VirtualBox](https://www.virtualbox.org/wiki/Downloads "virtualbox")

  __Note__: Don't forget to install the __VirtualBox Extension Pack__ too which can be found in the website above!

## Running Project 

1. On terminal, move to your project folder `cd projectPath` and then run the following commands:
2. `$ vagrant up` 
3. `$ vagrant ssh`
4. `$ cd /vagrant/api`
5. `$ gradle tomcatRun`


