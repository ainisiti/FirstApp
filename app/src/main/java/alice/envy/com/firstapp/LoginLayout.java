package alice.envy.com.firstapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by Envy on 11/06/2015.
 */

@SuppressLint("NewApi")
public class LoginLayout extends Activity {
    EditText un, pw;
    TextView error;
    Button ok;
    private String resp;
    private String errorMsg;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        un = (EditText) findViewById(R.id.et_un);
        pw = (EditText) findViewById(R.id.et_pw);
        ok = (Button) findViewById(R.id.btn_login);
        error = (TextView) findViewById(R.id.tv_error);

        ok.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                    postParameters.add(new BasicNameValuePair("username",un.getText().toString()));
                    postParameters.add(new BasicNameValuePair("password",pw.getText().toString()));

                    String response = null;
                    try{
                        response = SimpleHttpClient.executeHttpPost ("http://192.168.1.7:8084/LoginServer/login.do", postParameters);
                        String res = response.toString();
                        resp = res.replaceAll("\\s+","");
                    } catch (Exception e) {
                        e.printStackTrace();
                        errorMsg = e.getMessage();
                    }
                }
            }).start();
            try {
                Thread.sleep(1000);
                error.setText(resp);
                if (null != errorMsg && !errorMsg.isEmpty()){
                    error.setText(errorMsg);
                }
            } catch (Exception e) {
                error.setText(e.getMessage());
            }
        }
        });
    }
}
