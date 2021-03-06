package cs115.ucsc.polidev.politrack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//main login page, authenticates users to login.
public class LoginActivity extends Activity {

    private Button login;

    private EditText account;
    private EditText password;

    static String nAcc;
    private String nPass;

    static boolean SWITCH_ON = false;

    //firebase calls
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.login);

        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
    }
    //sign-up for an account
    public void GoToNewUser(View view){
        Intent intent = new Intent(this, NewUserActivity.class);
        startActivity(intent);
    }

    //authenticates with firebase
    public void Login(View view){
        this.nAcc = account.getText().toString();
        this.nPass = password.getText().toString();
        mAuth.signInWithEmailAndPassword(nAcc, nPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            makeNew(nAcc, nPass);

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(cs115.ucsc.polidev.politrack.LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }



                    }
                });

    }
    //saving user logins to database
    public void makeNew(String s, String x){
        SWITCH_ON = true;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UserEmail", s);
        intent.putExtra("UserPass", x);
        startActivity(intent);
    }
    //testing feature to bypass login
    public void bypass(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
