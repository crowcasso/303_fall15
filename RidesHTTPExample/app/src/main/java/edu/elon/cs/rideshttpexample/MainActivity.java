package edu.elon.cs.rideshttpexample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * An example app to show GET, POST to a RESTful website.
 *
 * @author J. Hollingsworth
 */
public class MainActivity extends Activity {

    // user interface
    private EditText phone, name, latitude, longitude;
    private TextView textView;

    // URL -- phone must be connected to Elon's network
    private final String baseURL = "http://trumpy.cs.elon.edu:5000/rides";

    // values from the EditTexts
    private String phoneText, nameText, latitudeText, longitudeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // user interface
        phone = (EditText) findViewById(R.id.phone);
        name = (EditText) findViewById(R.id.name);
        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);

        textView = (TextView) findViewById(R.id.textview);
    }

    // GET RIDE button listener
    public void getButtonOnClick(View view) throws IOException {
        textView.setText("");       // clear the lower TextView

        // connect to website and call /get  (GET)
        new GetRide().execute();
    }

    // REQUEST RIDE button listener
    public void requestButtonOnClick(View view) {
        textView.setText("");   // clear the lower TextView

        // get all the strings needed to make a request
        phoneText = phone.getText().toString();
        nameText = name.getText().toString();
        latitudeText = latitude.getText().toString();
        longitudeText = longitude.getText().toString();

        // connect to the website and call /request (POST)
        new RequestRide().execute();
    }

    // DELETE RIDE button listener
    public void deleteButtonOnClick(View view) {
        textView.setText("");   // clear the lower TextView

        // connect to the website and call /delete (GET, POST)
        new DeleteRide().execute();
    }

    /*
     * AsyncTask that connects to trumpy (webserver) to
     * call /get RESTful webservice.
     *
     * returns:
     *          "none" -- if no rides have been requested
     *          "phone,name,latitude,longitude" -- if ride exists
     *
     * (GET) http://trumpy.cs.elon.edu:5000/rides/get
     */
    private class GetRide extends AsyncTask<Void, Void, Void> {

        String response = "";

        @Override
        protected Void doInBackground(Void ... voids) {

            HttpURLConnection urlConnection = null;
            try {
                // connect to webserver (GET)
                URL url = new URL(baseURL + "/get");
                urlConnection = (HttpURLConnection) url.openConnection();

                // read the response
                InputStream in = new BufferedInputStream((urlConnection.getInputStream()));
                Scanner sc = new Scanner(in);
                response = sc.nextLine();

                // used to allow UI to update the TextView
                publishProgress();

            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                // always disconnect
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        // This is allowed to call UI objects
        @Override
        protected void onProgressUpdate(Void ... voids) {
            textView.setText(response);
        }
    }

    /*
     * AsyncTask that connects to trumpy (webserver) to
     * call /request RESTful webservice.
     *
     * returns:
     *          "invalid" -- if any value is missing or malformed
     *          "ok" -- if ride was successfully requested
     *
     * (POST) http://trumpy.cs.elon.edu:5000/rides/request?phone=336-693-6543&name=joel&latitude=56.5&longitude=10.3
     */
    private class RequestRide extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void ... voids) {

            HttpURLConnection urlConnection = null;
            try {
                // build the URL
                String param = "phone=" + URLEncoder.encode(phoneText, "UTF-8") +
                        "&name=" + URLEncoder.encode(nameText, "UTF-8") +
                        "&latitude=" + URLEncoder.encode(latitudeText, "UTF-8") +
                        "&longitude=" + URLEncoder.encode(longitudeText, "UTF-8");
                URL url = new URL(baseURL + "/request?" + param);

                // connect to webserver (POST)
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");

                // read the response
                String response = "";
                Scanner sc = new Scanner(urlConnection.getInputStream());
                // loop in case the response is more than one line
                while (sc.hasNextLine()) {
                    response += sc.nextLine();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                // always disconnect
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }

    /*
     * AsyncTask that connects to trumpy (webserver) to
     * call /delete RESTful webservice.
     *
     * returns:
     *          "ok" -- on success
     *
     * (GET) http://trumpy.cs.elon.edu:5000/rides/delete
     */
    private class DeleteRide extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void ... voids) {

            HttpURLConnection urlConnection = null;
            try {
                // connect to run GET command (delete)
                URL url = new URL(baseURL + "/delete");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream((urlConnection.getInputStream()));

                // read the response
                Scanner sc = new Scanner(in);
                System.out.println(sc.nextLine());

            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                // always disconnect
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}