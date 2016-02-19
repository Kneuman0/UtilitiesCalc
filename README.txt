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