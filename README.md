# finalyearproject

Software Implementation


Overview of implementation


Implementing the features of my application was a step-by-step process. Some features were dependent on others so I had to prioritise the most important features and then develop from there. As I was developing the application I had to refactor and remodel quite a lot of data, which often required me to recreate my database.
The first step I made in my application was to build my database the way I originally expected it to turn. This of course wasn’t the finished structure of the database but I used it as a starting point. I made sure that all foreign keys were referenced correctly.
The user interface is incredibly important when developing an application. I ensured that my UI was easy to use. I made sure that my homepage made a lasting impression on users so they would return. Bootstrap helped a lot with my navbar and various buttons and forms across the application. I tried to have similar layouts for pages so that it was easy for users to use.
 
User registration


The first step was to develop the ability for a user to register an account with the application. There are two Java methods involved in the registration of an account: showNewAccount() and createAccount(). In showNewAccount(), a new user object is created and added to the model and a registration page is displayed. createAccount() is called once the user submits the form. In createAccount(), validation is performed to ensure that the data that was entered is valid. If any errors are found then the original form is displayed again with the specific errors, as seen in the image below. JavaScript is used to ensure that the two password fields are the same. If they are not then the user cannot submit the form. If the data entered is valid then the user is saved in the database and then notified that the account has been created.
 
Login & Log out


Spring security has some excellent features which help with logging in and logging out. In my security-context xml file I declared an http element which has logout, login, session and url elements. A login form is displayed and the user is asked to enter their username and password. An authentication provider is used to select users from the database and if the username, password and authority is correct. When the user is logged in a session is created. I have set the session to timeout after 20 minutes as I felt extra security should be added. When the user wishes to log out then a log out success page is displayed. 
 

Creating a house


In order for a user to create a house, they must first be logged in. Once they are logged in then they have permissions to create a house. Like the registration of an account, two methods are used to create a house: createHouse() and doCreate(). The createHouse method's main responsibility is to display the form for users to create or edit their house. A house object is first declared as null. A Principal variable is used to check if the logged in user has a house already created. If it does, the house details are displayed on the form for the user to edit. If not, the form is displayed and the house object is added to the model. The doCreate method's main responsibility is to actually create the house. A house object and the BindingResult is sent from the form. It also checks whether the user wishes to save the house or delete from the jsp. If the BindingResult has errors present then the form is displayed again with the errors. 
If the user wishes to save the house, the Google Maps API is used to check if the house address actually exists. Jsoup is used to scrape the latitude and longitude from the result page and the house is added to the database and a "success" page is displayed. Otherwise an error page is displayed. If the user wishes to delete then the id of the house is retrieved from the object and the houseService method for deletion is called.
 
Displaying the map
Displaying the map was hugely important to my application. Google Maps allowed me to add each house to a map and display information about the house on an infowindow. All houses are retrieved from the database and added to the model so they can be accessed in the JSP. In the map.jsp, JavaScript is used to display the map. Arrays are created for latitude, longitude, room, rent and id information for each house. These arrays are then looped through to add the marker to a specific location on the map. 
 
A listener is added to this marker so when it is clicked on an infowindow is displayed with information about the house.
 
Viewing all houses


Displaying all houses that are in the system allows users to choose which matches their needs most. This was vital to the application as it brings the user one step closer to adding a roomie. The user can then click on the house address and view more information about the house. There is also the option to add/edit a house or view the houses on the map. Code wise all it involved was retrieving a list of houses from the database and looping through this list in the JSP page to add each house to a table.
 
Viewing a user’s house


Viewing a house can allow the user to do many things: view the owners profile, view the facilities of the house, view the picture the owner has uploaded, message the owner and post a comment. I tried my best to make this as realistic as possible so I designed it like the house pages on daft.ie, but with extra functionality. 
In terms of code this only required one method in my controller: showHouse().The showHouse method's main responsibility is to view a particular house. It also takes the house id as a parameter and uses the houseService to retrieve the house information. Message and comment obects are instantiated and added to the model so a user can message the owner or post a comment. A list of all comments on that house is also retrieved from the database and displayed.
House pages look visually different depending on who is viewing them. If the owner is viewing it, then they have certain permissions like editing the house or uploading a picture whereas if it is just a standard user then they have the basic permissions.
 
Searching for houses


A user can search for a house on the homepage. They enter a town/county, minimum rooms, maximum rooms, minimum rent and maximum rent and press the search button. A list of results is returned and these can be sorted in ascending or descending order.
 


Searching for a user
Users can also search for other users from any page as it is in the header. They enter the name of the user and then press the search button. The result is then displayed on a page where the user can then click on the searched users’ profile.

Viewing a user’s profile


Viewing a user’s profile can allow the user to do many things: view the users personal details, view the picture of the house the user has uploaded and their house, message the user and add them as a roomie.
In terms of code this only required one method in my controller: showUser().The showUser method's main responsibility is to view a particular users profile. It also takes the username as a parameter and uses the houseService and userService service classes to retrieve the information about the houses and users. Message and roomie objects are instantiated and added to the model so a user can message the user or add them as a roomie. Profile pictures and house pictures are also checked to see if they exist using Booleans and added to the model. 
User pages also look visually different depending on who is viewing them. If the logged in user is viewing it, then they obviously can’t add themselves as a roomie or message themselves whereas if it is a different user then they can do this.
 
Messaging


All messages done in this application are done internally. I did not want to use email or Facebook for this as I felt it was important to keep it within the system. When a message is sent it is inserted into the database. A user can view all the conversations they have with other users.
 
They can then click on one of these users where they are brought to the messaging page. This enables both users to have a live conversation with each other. jQuery is used to automatically refresh the section every 3 seconds.
Stylistically I tried to keep the layout as much like Facebook Messenger as I could as I knew that users would be familiar with this layout and it would be easy to use. Messages that you sent appear on the right of the page with a blue background whereas messages you received appear on the left with a grey background.
When the user sends a new message the message is sent and the page is reloaded.
 

Roomies


The roomies homepage enables users to view information about all roomies living in the house. They can choose to create tasks, create contacts or view all roomies where they can then remove roomies. The layout of this page depends on how much information has been created and how many roomies a user has. 
 
All roomies can create contacts and tasks which will be helpful for other roomies. It helps manage the day-to-day tasks within the house. 

 Administrator
 
 
The administrator has extra features to a normal user. They are able to delete a house or user with just the click of a button as well as all the features a user has. 
 

