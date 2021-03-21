# Newcastle University College Admin System (Team 21)

### Contributors: 
- Vita Rinkauskaite
- Sarunas Martinaitis
- Sophie Kirkup
- Matthew Jackson
- Abdulla Rashed

## Classes:
In the program exist the following classes, which map to entities in our database (not to be accessible to the user):
- `Staff`
- `Students`
- `Room`
- `Module`
- `ModuleRequirements`
- `Time`

The class `SetUpDatabase` when run reads data from the .csv files in the resources directory and enters 
it into the appropriate table in the database.  

There are also the following classes, which implement the functionality of the 'Room Booking System' and 
'Timetable System' (not to be accessible to the user):
- `RoomBooking`
- `Timetable`

The user interacts with the program via the `UI` class. This class provides the 
menu interfaces to the user and takes inputs from them, as well as invoking the methods from `RoomBooking` and 
`Timetable`, then formatting and outputting the results from these. The user can access the `UI` class by running the 
`Main` class. 

There is also an `InputCheck` class for validity checking. `UI` regularly calls methods from this class. 

## Interfaces 
Our program involves the following generic interface classes:
- `InputCheckInterface`
- `RoomBookingInterface`
- `TimetableInterface`
- `UInterface`

## Testing 
We have included the following classes in order to test certain functionalities:
- `StaffModuleTesting` - tests the code that links `Staff` and `Module` together in a Many to Many Relationship
- `StudentModuleTesting` - tests the code that links `Student` and `Module` together in a Many to Many Relationship
- `TimetableTesting` - tests whether the program will allow bookings for times that overlap
