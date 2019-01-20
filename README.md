# Category Api
Get formatted list of discounted products under a Category sorted as "highest price reduction first". Price reduction is calculated using [price.was - price.now].

## Requirements
* Oracle JDK 1.8u40 or higher
* Java IDE with Kotlin Support (e.g. IntelliJ IDEA)

## For Creating IntelliJ IDEA project

* Install essential IntelliJ plugins
  * Lombok Plugin
* Enable Annotation Processors
  * Go to `File > Settings` and search for `Annotation Processors`
  * Check `Enable annotation processing`
* Checkout the project using Git
* Using `New > Project from existing sources` (or from the welcome screen, use `Import Project`), select the build.gradle file
* Select a 1.8 JVM for `Gradle JVM` and check `Enable auto import`

## To Build the application

Run the gradle wrapper in the Category API directory.

#### Linux or Mac

    ./gradlew clean build

#### Windows

    gradlew clean build
    
## To Run the application

Run the below command to start the application once "Build Successful" message is displayed

#### Linux or Mac

    ./gradlew bootRun

#### Windows

    gradlew bootRun
    
This should start the application on embedded Tomcat server @ localhost, port: 8080, with sample console messages like below

    Tomcat started on port(s): 8080 (http) with context path ''
    Started ApiApplication in 7.877 seconds (JVM running for 8.489)
    <=========----> 75% EXECUTING [5m 53s]


Application output can be seen in browser by hitting the URL with category Id and label type(optional)

    Endpoint: http://localhost:8080/v1/categories/{categoryId}/discounted-products?labelType={labelType}
   
    Example: 
    
      - http://localhost:8080/v1/categories/600001506/discounted-products
      - http://localhost:8080/v1/categories/600001506/discounted-products?labelType=ShowWasNow
      - http://localhost:8080/v1/categories/600001506/discounted-products?labelType=ShowWasThenNow
      - http://localhost:8080/v1/categories/600001506/discounted-products?labelType=ShowPercDscount
 
Formatted JSON response of discounted products under that category or Respective Error messages should get displayed on the browser. Price label formatting is according to the allowed labelType passed as URL parameter (default is - Show Was, Now). 
 
 
