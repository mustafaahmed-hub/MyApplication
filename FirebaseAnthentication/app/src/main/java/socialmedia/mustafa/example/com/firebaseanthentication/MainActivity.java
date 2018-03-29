package socialmedia.mustafa.example.com.firebaseanthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth mAuth;
    ProgressBar progressBarSignIn;
    EditText mEmailSignIn , mPasswordSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.signup_text).setOnClickListener(this);
        findViewById(R.id.button_signin).setOnClickListener(this);
        progressBarSignIn=(ProgressBar)findViewById(R.id.progressbar_signin);
        mAuth = FirebaseAuth.getInstance();
        mEmailSignIn =(EditText)findViewById(R.id.email_field_signin);
        mPasswordSignIn=(EditText)findViewById(R.id.password_field_signin);



    }
    private void userLogIn(){

        String memail = mEmailSignIn.getText().toString();
        String mpassword =mPasswordSignIn.getText().toString();
        if(memail.isEmpty()){
            mEmailSignIn.setError("Email required");
            mEmailSignIn.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(memail).matches()){

            mEmailSignIn.setError("Enter valid Email");
            mEmailSignIn.requestFocus();
            return;
        }
        else if(mpassword.isEmpty()){
            mPasswordSignIn.setError("Password Required");
            mPasswordSignIn.requestFocus();
            return;
        }
        else if(mpassword.length()<6){
            mPasswordSignIn.setError("Password Minimum length is 6");
            mPasswordSignIn.requestFocus();
            return;
        }
        progressBarSignIn.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(memail,mpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBarSignIn.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    Intent intent =new Intent(MainActivity.this,ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null) {

            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signup_text:

                startActivity(new Intent(this,SignUpActivity.class));
                break;

            case R.id.button_signin:
                userLogIn();
                break;
        }

    }
}
