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
  The communication beteween the app and the Arduino happens with a Rails based server. Communication happens through the HTTP protocol. All the request are mplemented using the Android HttpURLConnection class. When the application starts a GET request is sent to the server and the information on the screen is updated. When a user interacts with the application, a new PUT request is send to the server and the coresponding fields in the DB are updated. The user can refresh the application with a down swipe gesture. Due to the Imperial and Metrics standard the user can choose the one, he wants the data to be converted in. All the buttons extend the Extended class.
 ```
 public abstract class Extended(){
    Integer state;
    Integer prevState;
    String name;
    String propName;
    Integer responseCode = 0;
    
    public Extended(Integer state, String name, String propName);
    
    //Getters and Setters for some of the class fields
    //-------------------------------------------------
    public void setState(Integer state);

    public Integer getState();

    public String getPropName();

    public int geResponceCode();
    //-------------------------------------------------
    
    //Creates a new ServerPutRequestTask() with the appropriate
    //field for the current button and the value set by the user
    void sendDataToServer(String field, String value);
    
    //Implementation of the AsyncTask class for sending PUT requests without blocking the UI thread
    class ServerPutRequestTask extends AsyncTask<String[], Void, Void> {
        
        @Override
        protected void onPreExecute() {
            asyncOnPreExecute();
        }
        
        //Builds a JSON object with the field and value strings, then calls 
        //the NetworkUtils sendPostRequestToServer(JSONObject data, URL url) with
        //the new JSON object and the server's URL
        @Override
        protected Void doInBackground(String[]... strings);

        @Override
        protected void onPostExecute(Void aVoid) {
            asyncOnPostExecute();
        }
    }
    
    //Methods meant to be implemented individually by the subclasses
    protected abstract void asyncOnPreExecute();
    protected abstract void asyncOnPostExecute();
 }
 ```
 The GET and PUT requests are implemented in the NetworkUtils class
 ```
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String SERVER_URL =
                "http://178.169.176.184:8000/users/";

    //Builds the Server URL using server's IP and the user's ID
    public static URL buildUrl(String userId);

    public static int sendPostRequestToServer(JSONObject data, URL url) throws IOException;

    public static HashMap<String, String> getResponseFromHttpUrl(URL url) throws IOException;

}
 ```
 
  
Server Repo - https://github.com/DanyYanev/Boiler_Control_Server <br>
Controller Repo - https://github.com/DanyYanev/Boiler_Control_Controller

Resources
----------
* Stick Switch: https://github.com/GwonHyeok/StickySwitch
* Circular Seek Bar: https://github.com/ksughosh/CircularSeekbar
* App Homescreen Background: https://www.behance.net/gallery/23582699/Happy-2015-animated-gif
* Navigation Drawer: https://stackoverflow.com/questions/31154027/how-to-create-mini-drawer-menu-in-android
* Icons: https://www.flaticon.com/
