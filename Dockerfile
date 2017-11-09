FROM ubuntu:14.04

#   Prerequsites
RUN apt-get update
RUN apt-get install -y software-properties-common


# Install Java 8
RUN sudo add-apt-repository -y ppa:webupd8team/java
RUN sudo apt-get update
RUN sudo apt-get install -y oracle-java8-installer

# Install Maven
RUN     sudo apt-get install -y maven

# Install Git
RUN sudo apt-get install -y git

# Clone project
RUN sudo git clone https://github.com/Topolev/sitnitsa-bigcity-parcer.git

# Download most of maven dependencies
RUN cd sitnitsa-bigcity-parcer

