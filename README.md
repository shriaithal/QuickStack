# QuickStack

### Introduction
</br>Quick Stack is a new innovative online learning portal. It provides theoretical details about a topic and a set of instructions which can be executed by the user on a real, live environment. The environment can be as simple as consisting of one Linux server or can be as complex as consisting of multiple servers comprising of a database instance and a web server. As the user progresses in the lab assignment, he can take a backup of the environment and restore the backup if something goes wrong during his lab. The user can switch off their environments when they are not using it. At the end, the user is presented a bill, which is as per usage.
Quick Stack is developed on Open Stack which is a cloud operating system. It is an open source platform which aims to create abstracted pools of compute, storage, and networking resources that can be used to create virtual machines on top of standard server hardware.
The open source environment allows you to create a truly software defined data center. OpenStack has very robust role-based access controls. Access and resource utilization can be controlled at the level of users, roles, and projects. OpenStack is highly scalable and is supported by countless contributors and individuals from many organizations.
Quick Stack is a PaaS and needed a platform like OpenStack which can be used to create and manage various types of servers. These servers can be of different operating systems, different operating system versions etc. OpenStack provides a rich REST API and CLI library which can be invoked by the UI program to instruct OpenStack platform to perform necessary action. For e.g. When we start a lab, we need to create an instance of the server. The UI will internally invoke create server REST API to create the server instance.

### Technologies
1.	Quick Stack Project is built on top of OpenStack and uses both of its controlling interfaces: Rest API 
and Command Line Interface.
2.	OpenStack Ocata version is used in the project and is customized to be used in lab environment and 
so far, can only be run with one compute node.
3.   Server Side: Spring MVC, Java, OpenStack4j, Apache commons 
a.	Spring 4 is used to develop the web application with AngularJS and HTML5 to render views and JAVA REST APIs with JAVA POJO as the controller and model.
b.	Restful APIs are developed to login, GET user data, POST user data.
c.	OpenStack4j is used as the OpenStack SDK for Java to provision and control the OpenStack system from backend. 
4.   Client Side: HTML5, AngularJS, Bootstrap
a.	HTML5 along with Bootstrap to render application screens
b.	AngularJS is used to route across pages also, to invoke the backend REST APIs.
5.  Database: Mongo DB
6.  Build Tool: Maven
a.	Maven is used as the build tool and all the dependencies are provided in pom.xml.
7.  Web Server: Apache Tomcat 8.5
a.	Apache Tomcat is used to run the Quick Stack application on local host.
