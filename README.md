# Boiler Control App
  An Android app for distant comumncation with an Arduino based heating system. <br>
  
## Requiermements 
* Min SDK Version: 21 (Lollipop)
* Target SDK Version: 26 (Oreo)

## Preview
<img src = "https://cdn.discordapp.com/attachments/415239661175439361/452961529805930527/20180604_005708.png"  width="200"> <img src = "https://cdn.discordapp.com/attachments/415239661175439361/452961529185304586/20180604_005604.png"  width="200">
<img src = "https://cdn.discordapp.com/attachments/415239661175439361/452961528535318539/20180604_005541.png"  width="200">
<img src = "https://cdn.discordapp.com/attachments/415239661175439361/452961528535318538/20180604_005522.png"  width="200">
  

  
## Design & Architecture  
  The communication beteween the app and the Arduino happens with a Rails based server. Communication happens through the HTTP protocol. When the application starts a GET request is sent to the server and the information on the screen is updated. When a user interacts with the application, a new PUT request is send to the server and the coresponding fields in the DB are updated. The user can refresh the application with a down swipe gesture. Due to the Imperial and Metrics standard the user can choose the one, he wants the data to be converted in. 
  
Server Repo - https://github.com/DanyYanev/Boiler_Control_Server <br>
Controller Repo - https://github.com/DanyYanev/Boiler_Control_Controller

Resources
----------
* Stick Switch: https://github.com/GwonHyeok/StickySwitch
* Circular Seek Bar: https://github.com/ksughosh/CircularSeekbar
* App Homescreen Background: https://www.behance.net/gallery/23582699/Happy-2015-animated-gif
* Navigation Drawer: https://stackoverflow.com/questions/31154027/how-to-create-mini-drawer-menu-in-android
* Icons: https://www.flaticon.com/
