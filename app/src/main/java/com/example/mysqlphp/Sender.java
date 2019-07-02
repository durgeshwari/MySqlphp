package com.example.mysqlphp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class Sender extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    EditText nameTxt,posTxt,teamTxt;

    String name,pos,team;

    ProgressDialog pd;

    public Sender(Context c, String urlAddress,EditText...editTexts) {
        this.c = c;
        this.urlAddress = urlAddress;

        //INPUT EDITTEXTS
        this.nameTxt=editTexts[0];

        //GET TEXTS FROM EDITEXTS
        name=nameTxt.getText().toString();
    }
    /*
   1.SHOW PROGRESS DIALOG WHILE DOWNLOADING DATA
    */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Send");
        pd.setMessage("Sending..Please wait");
        pd.show();
    }
    @Override
    protected String doInBackground(Void... voids) {
        return this.send();
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        pd.dismiss();

        if(response != null)
        {
            //SUCCESS
            Toast.makeText(c,response,Toast.LENGTH_LONG).show();

            nameTxt.setText("");

        }else
        {
            //NO SUCCESS
            Toast.makeText(c,"Unsuccessful "+response,Toast.LENGTH_LONG).show();
        }
    }

    private String send() {
        HttpURLConnection con=Connector.connect(urlAddress);
        if(con==null) {
            return null;
        }

        try {
            OutputStream os=con.getOutputStream();
            //WRITE
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            bw.write(new DataPackager(name).packData());
            bw.flush();

            //RELEASE RES
            bw.close();
            os.close();

            //HAS IT BEEN SUCCESSFUL?
            int responseCode=con.getResponseCode();

            if(responseCode==con.HTTP_OK) {
                //GET EXACT RESPONSE
                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response=new StringBuffer();
                String line;

                //READ LINE BY LINE
                while ((line=br.readLine()) != null)
                {
                    response.append(line);
                }
                //RELEASE RES
                br.close();
                return response.toString();

            }else {
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
