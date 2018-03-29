package socialmedia.mustafa.example.com.firebaseanthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    ProgressBar progressBarSignup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTextEmail=(EditText)findViewById(R.id.email_field_signup);
        editTextPassword=(EditText)findViewById(R.id.password_field_signup);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.button_signup).setOnClickListener(this);
        findViewById(R.id.signin_text);
        progressBarSignup=(ProgressBar)findViewById(R.id.progressbar_signup);
    }
    private void registerUser(){
        String memail = editTextEmail.getText().toString();
        String mpassword=editTextPassword.getText().toString();
        if(memail.isEmpty()){
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(memail).matches()){

            editTextEmail.setError("Enter valid Email");
            editTextEmail.requestFocus();
            return;
        }
        else if(mpassword.isEmpty()){
            editTextPassword.setError("Password Required");
            editTextPassword.requestFocus();
            return;
        }
        else if(mpassword.length()<6){
            editTextPassword.setError("Password Minimum length is 6");
            editTextPassword.requestFocus();
            return;
        }
        progressBarSignup.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(memail,mpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBarSignup.setVisibility(View.GONE);
                if(task.isSuccessful()){


                    Toast.makeText(getApplicationContext(),"Sign up succesful",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(SignUpActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                        Toast.makeText(getApplicationContext(),"User Already registered",Toast.LENGTH_SHORT).show();
                    else
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_signup:
                registerUser() ;
                break;
            case R.id.signin_text:

                startActivity(new Intent(this,MainActivity.class));
                break;

        }
    }
}
