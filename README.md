# Boiler_Control_App
  An Android app for distant comumncation with an Arduino based heating system.
  
## Design & Architecture  
  The communication beteween the app and the Arduino happens with a Rails based server. Communication happens through the HTTP protocol. When the application starts a GET request is sent to the server and the information on the screen is updated. When a user interacts with the application, a new PUT request is send to the server and the coresponding fields in the DB are updated. The user can refresh the application with a down swipe gesture. Due to the Imperial and Metrics standard the user can choose the one, he wants the data to be converted in. 
  
Server Repo - https://github.com/DanyYanev/Boiler_Control_Server
Controller Repo - https://github.com/DanyYanev/Boiler_Control_Controller

Resources
----------
* Stick Switch: https://github.com/GwonHyeok/StickySwitch
* Circular Seek Bar: https://github.com/ksughosh/CircularSeekbar
* App Homescreen Background: https://www.behance.net/gallery/23582699/Happy-2015-animated-gif
* Navigation Drawer: https://stackoverflow.com/questions/31154027/how-to-create-mini-drawer-menu-in-android
* Icons: https://www.flaticon.com/
