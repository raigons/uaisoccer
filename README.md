# UAISOCCER [![Build Status](https://snap-ci.com/raigons/uaisoccer/branch/master/build_image)](https://snap-ci.com/raigons/uaisoccer/branch/master)
Easily schedule soccer championships.

* Installation
  * [Install Ansible and Vagrant](https://github.com/raigons/uaisoccer#install-ansible-and-vagrant)
  * [Install Bower and Node Dependecies](https://github.com/raigons/uaisoccer#install-bower-and-node-dependecies)
* Running Project
  * [Useful Aliases](https://github.com/raigons/uaisoccer#useful-aliases)  

## Install Ansible and Vagrant

1. Install [Ansible](http://www.ansible.com "ansible")
2. Install [Vagrant](http://www.vagrantup.com/ "vagrant") and [VirtualBox](https://www.virtualbox.org/wiki/Downloads "virtualbox")
3. Install [Vagrant Cachier Plugin](http://fgrehm.viewdocs.io/vagrant-cachier "vagrant cachier")

## Running Vagrant

1. On terminal, move to your project folder `cd projectPath` and then run the following commands:
2. `$ vagrant up` to start you vagrant box.
3. `$ vagrant ssh` to acess your vagrant box with ssh.

## Useful Aliases
Some useful aliases to get you started

1. `$ idp` to install bower and node dependencies if not yet installed
2. `$ rapi` to run api if not running. Now you can access the API in the address: http://localhost:8090/api
3. `$ rapp` to run the app server if not running. Now you can access the APP in the address: http://localhost:9000
