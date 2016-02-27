Utilities Calculator
README
Fully functioning stand alone application (once exported to a runnable .jar file). Manages the utility bills for tenants in a residence. Handles sublets who did not stay for a full month, landlords who pay no utilities and full time standard tenants. Uses a relational database to store and track payments for each tenant from month to month. Allows tenants to export a receipt detailing each tenants bills for the month and their yearly contributions.  
To test a dry run, run the class named UtilitiesCalcMain. This will initalize the db and run the GUI.
To test the application with mock data, go to createDB class and  set: 

final boolean DROP_DATABASE = false;
final boolean DROP_TABLE = true;
final boolean RESET_DATABSE_DUMMY_VALUES = true;
final boolean SHOW_ROW_COUNT = true;

This will insert mock data into the database. Then go to UtilitesCalcMain and run that class to to use this application with mock data.

**To accurately run tests, you must insert mock data using the above instructions**

If executing program in IDE, receipts will be saved in package next to source code named resources.receipts

-------------------------------------------------------------------------------------

USING THE APPLICATION IN A JAR
Once exported, the application will create a database folder next to the jar file containing all the database files.
The database will be completely empty. Do not relocate the database outside of the folder it is created in. Doing this will
eliminate all your data and the application will create a new databse folder where the old one was. 

PRINTING RECEIPTS
All receipts printed from the application will be saved next to the jar file.