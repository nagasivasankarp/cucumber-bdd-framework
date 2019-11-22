# cucumber-bdd-framework

Framework Description 

The framework which I followed is 'BDD CUCUMBER. Below is the brief description on how this framework works: 

➢ each page is defined as its own class. 

➢ Actions are represented as functions for a class. 

➢ Tests only talk to the features files 

➢ Class Inheritance can be used to define functionality to a set of pages.  
 
Prerequisites  

-> Java should be installed and path should be in environment variables. 

-> Maven should be installed path should be in environment variables. 

-> Cucumber Plug-in Should be Installed in IDE (in Eclipse, we can install using Eclipse Marketplace) .  

-> Import the framework to available IDE (Eclipse is suggestible).  

Update your jdk/jre to the project by follow below steps. 

Below are the steps to setup project properties:  
-> ---- Select Framework -> Right click -> Click on Properties 
        Click on Java Build Path -> Remove Existing JRE -> Add Library -> Select JRE System Library -> Next -> Click and Select Alternative JRE -> Finish -> Apply. 
        Click on Java Compiler -> Select Compiler Compliance Level -> Apply. 
        Click on Project Facets -> Select Java Version -> Select Runtimes -> Apply -> Apply and Close
        
After completing above setup, follow the below steps to install all the Maven dependencies 
 
  Select Project --> Right click --> Select 'Run As' --> Click on 'Maven Install' 
              
Execute the Test Runner: 

  Navigate to UITestRunner.java class in testrunner package; right click in the class and Run As Junit Test. 
  
 
Results Location  
  
  After execution completed, you can find the HTML result file in the Reports folder in the framework.  
  And you can find the Cucumber Reports in target folder. 
 
 
 
