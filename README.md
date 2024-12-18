## How to run the project

First, open a new terminal and move to the directory where you have the selenium server .jar file. Then, run the 
following command:

``
    java -jar selenium-server-<version>.jar hub 
``

Replace "version" with yours. 

After, open another terminal and run the following:

``
    java -jar selenium-server-<version>.jar node --detect-drivers true --hub http://localhost:4444/wd/hub 
``

After these two steps, you have to run the "gui-suite.xml" suite file, inside src/test/resources/suites.

While running, please solve the captchas that will show up during the tests execution.