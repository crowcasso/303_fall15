package edu.elon.cs.noteshttpexample;

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

public class MainActivity extends Activity {

    // user interface
    private EditText x, y, note, which;
    private TextView textView;

    // URL -- phone must be connected to Elon's network
    private final String baseURL = "http://trumpy.cs.elon.edu:5000/notes";

    // values from EditTexts
    private String xText, yText, noteText, whichText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //user interface
        x = (EditText) findViewById(R.id.x);
        y = (EditText) findViewById(R.id.y);
        note = (EditText) findViewById(R.id.note);
        which = (EditText) findViewById(R.id.which);

        textView = (TextView) findViewById(R.id.textview);
    }

    // ADD NOTE button listener
    public void addButtonOnClick(View view) {
        textView.setText("");       // clear the lower TextView

        xText = x.getText().toString();
        yText = y.getText().toString();
        noteText = note.getText().toString();

        new AddNote().execute();

    }

    // DELETE NOTE button listener
    public void deleteButtonOnClick(View view) {
        textView.setText("");       // clear the lower TextView

        whichText = which.getText().toString();
        new DeleteNote().execute();
    }

    // SHOW NOTE button listener
    public void showButtonOnClick(View view) {
        textView.setText("");       // clear the lower TextView

        whichText = which.getText().toString();
        new ShowNote().execute();
    }

    // SHOW ALL NOTES button listener
    public void showAllButtonOnClick(View view) {
        textView.setText("");       // clear the lower TextView

        new ShowAllNotes().execute();
    }

    /*
     * AsyncTask that connects to trumpy (webserver) to
     * call /add RESTful webservice.
     *
     * returns:
     *          "#" -- the note number if successful
     *          "invalid" -- if the add request was malformed
     *
     * (POST) http://trumpy.cs.elon.edu:5000/notes/add?x=13&y=67&note=This is my note
     */
    private class AddNote extends AsyncTask<Void, Void, Void> {

        private String response = "";

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            try {
                // build the URL
                String param = "x=" + URLEncoder.encode(xText, "UTF-8") +
                        "&y=" + URLEncoder.encode(yText, "UTF-8") +
                        "&note=" + URLEncoder.encode(noteText, "UTF-8");
                URL url = new URL(baseURL + "/add?" + param);

                // connect to webserver (POST)
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");

                // read the response
                Scanner sc = new Scanner(urlConnection.getInputStream());
                response += sc.nextLine();

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
    * call /add RESTful webservice.
    *
    * returns:
    *          "# deleted" -- the note was successful deleted
    *
    * (GET,POST) http://trumpy.cs.elon.edu:5000/delete/7
    */
    private class DeleteNote extends AsyncTask<Void, Void, Void> {

        private String response = "";

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            try {
                // connect to webserver (GET)
                URL url = new URL(baseURL + "/delete/" + whichText);
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
    * call /add RESTful webservice.
    *
    * returns:
    *          "x,y
    *           note" -- the note
    *          "no note" -- if no note exists at given number
    *
    * (GET) http://trumpy.cs.elon.edu:5000/show/7
    */
    private class ShowNote extends AsyncTask<Void, Void, Void> {

        private String response = "";

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            try {
                // connect to webserver (GET)
                URL url = new URL(baseURL + "/show/" + whichText);
                urlConnection = (HttpURLConnection) url.openConnection();

                // read the response
                InputStream in = new BufferedInputStream((urlConnection.getInputStream()));
                Scanner sc = new Scanner(in);
                while (sc.hasNextLine()) {
                    response += sc.nextLine() + "\n";
                }

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
    * call /add RESTful webservice.
    *
    * returns:
    *          "#
     *          x,y
     *          note text" -- all of the notes with note number
    *          "" -- if no notes exists
    *
    * (GET) http://trumpy.cs.elon.edu:5000/show_all
    */
    private class ShowAllNotes extends AsyncTask<Void, Void, Void> {

        private String response = "";

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            try {
                // connect to webserver (GET)
                URL url = new URL(baseURL + "/show_all");
                urlConnection = (HttpURLConnection) url.openConnection();

                // read the response
                InputStream in = new BufferedInputStream((urlConnection.getInputStream()));
                Scanner sc = new Scanner(in);
                while (sc.hasNextLine()) {
                    response += sc.nextLine() + "\n";
                }

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
}
