Master [![Build Status](https://travis-ci.org/dvla/vehicles-change-keeper-online.svg?branch=master)](https://travis-ci.org/dvla/vehicles-change-keeper-online)

DVLA Vehicles Change Keeper Online
==================================

`vehicles-change-keeper-online` is the Web frontend to facilitate the selling of a vehicle to a new private keeper by an existing private keeper.

Architectural overview
----------------------

### Presentation layer

This project encapsulates the presentation layer of the application.

The codebase is predominantly [Scala][scala] and is implemented against [Play][play-framework]: a 'full stack' Web
framework for the JVM.

### Microservices

Most complex business decisions are deferred to a network of [RESTful][rest] microservices. These are maintained through
separate projects:

-   `os-address-lookup`
-   `vehicle-and-keeper-lookup`
-   `vehicles-acquire-fulfil`

These services are mocked for automated testing, but must be running locally for manual testing/development of dependant
components within the presentation layer.

Development prerequisites
-----------------------
1.  JDK 1.7 must be installed
1.  Install SASS. The [current documentation][install-sass] suggests:
    2. Install Ruby
    
       Mac: `brew install ruby` Add the bin folder to the path as suggested during the install

       Debian Linux: `sudo apt-get install ruby`
    2. Install SASS
    
       Mac & Debian Linux: `sudo gem install sass`

1.  Install SBT.  The [current documentation][install-sbt] suggests:

    Mac: `brew install sbt`
    
    Debian Linux: `sudo apt-get install sbt`

1.  Increase 'permanent generation space' requirements for SBT.

    Mac: Create the file `~/.sbtconfig` with the following content:

        SBT_OPTS="-XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:PermSize=256M -XX:MaxPermSize=2048M $SBT_OPTS"
        
    Linux: edit ~/.bashrc and add 
    
        export SBT_OPTS="-XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:PermSize=256M -XX:MaxPermSize=2048M$SBT_OPTS"
        
Compiling
---------
Being a standard Play! application vehicles-change-keeper-online can be compiled by simply running `sbt compile`. A distribution zip bundle can be generated by running `sbt dist`

Running
-------
Vehicles-change-keeper-online needs certain secrets to be able to run. If you don't have access to the secret repo you won't be able to run the application except during the test run.

1. Running vehicles-change-keeper-online only
    - Clone the secret repo in the parent folder of vehicles-change-keeper-online
    - `cd <<the appropriate secrets repo>>`
    - `./setup XYZ` *"XYZ" is an offline secret key obtained through a trusted team member*
    - `cd ../vehicles-change-keeper-online`
    - `sbt run`
    The application is now accessible at http://localhost:9000 . There is some functionality that might not work because vehicles-change-keeper-online depends on various REST services which are missing. The whole application will only work if these REST services are there.
2. Running  REST microservices manually from source code
    - Git clone all the microservices projects and decript the secrets `vehicles-acquire-fulfil`, `vehicle-and-keeper-lookup`, `os-address-lookup`, `<<the appropriate secrets repo>>' They should be all cloned in the same folder where `vehicles-change-keeper-online` was cloned.
    - Decrypt secrets `cd <<the appropriate secrets repo>>`, `./setup XYZ` *Again "XYZ" is an offline secret key obtained through a trusted team member*
3. Sandbox - Automatically run all the microservices
Just run ```sbt sandbox``` within vehicles-change-keeper-online and it will do all the above in a fast and reliable way. 

Afterwards open your browser to:

    http://localhost:9000

If here are any local changes to the vehicles-change-keeper-online code base they would be automatically picked up next time the browser is refreshed.


### Running with production logging

To emulate production-level logging:

1.  Ensure `syslog` is configured. Details have been provided for [configuring `syslog` on OSX][syslog-osx].

2.  Run the `vehicles-change-keeper-online` application:

        cd vehicles-change-keeper-online
        ./startWithLog.sh
        
3.  Open in Web browser:

        http://localhost:9000/


Session encryption
------------------

Please refer to the [session encryption][session-encryption] document for details on the encryption algorithm used.

[install-sass]: http://sass-lang.com/install "Install SASS"
[install-sbt]: http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html#installing-sbt "Install SBT"
[rest]: https://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm "REST"
[play-framework]: http://www.playframework.com/ "Play Framework"
[scala]: http://www.scala-lang.org/ "Scala Language"
[syslog-osx]: syslog-osx.md "Configuring syslog on OSX"
[session-encryption]: encrypted-session-state.md "Session Encryption"

