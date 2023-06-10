package com.techhunters.easyschool.features.auth.presentation

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.techhunters.easyschool.R
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneLoginScreen(context: Context) {

    // on below line creating variable for course name,
    // course duration and course description.
    var phoneNumber by remember {
        mutableStateOf("")
    }

    val otp = remember {
        mutableStateOf("")
    }

    val verificationID = remember {
        mutableStateOf("")
    }

    val message = remember {
        mutableStateOf("")
    }

    // on below line creating variable
    // for firebase auth and callback
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    mAuth.setLanguageCode("ar")
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.stu),
            contentDescription = "Login Background Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
            contentScale = ContentScale.Crop
        )
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
        )


        Button(
            onClick = {

                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(context, "Please enter phone number..", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    val number = "+967${phoneNumber}"
                    // on below line calling method to generate verification code.
                    sendVerificationCode(number, mAuth, context as Activity, callbacks)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text("Login", color = Color.White)
        }

        // adding spacer on below line.
        Spacer(modifier = Modifier.height(10.dp))

        // on below line creating text field for otp
        OutlinedTextField(
            // on below line we are specifying
            // value for our course duration text field.
            value = otp.value,
            //specifying key board on below line.
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            // on below line we are adding on
            // value change for text field.
            onValueChange = { otp.value = it },

            // on below line we are adding place holder
            // as text as "Enter your course duration"
            placeholder = { Text(text = "Enter your otp") },

            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),

            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

            // on below line we are adding
            // single line to it.
            singleLine = true,
        )

        // adding spacer on below line.
        Spacer(modifier = Modifier.height(10.dp))

        // on below line creating button to add
        // data to firebase firestore database.
        Button(
            onClick = {
                // on below line we are validating
                // user input parameters.
                if (TextUtils.isEmpty(otp.value)) {
                    // displaying toast message on below line.
                    Toast.makeText(context, "Please enter otp..", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // on below line generating phone credentials.
                    val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationID.value, otp.value
                    )
                    // on below line signing within credentials.
                    signInWithPhoneAuthCredential(
                        credential,
                        mAuth,
                        context as Activity,
                        context,
                        message
                    )
                }
            },
            // on below line we are
            // adding modifier to our button.
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // on below line we are adding text for our button
            Text(text = "Verify OTP", modifier = Modifier.padding(8.dp))
        }

        // on below line adding spacer.
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            // on below line displaying message for verification status.
            text = message.value,
            style = TextStyle(color = Color.Green, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
    }


    // on below line creating callback
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                // on below line updating message
                // and displaying toast message
                message.value = "Verification successful"
                Toast.makeText(context, "Verification successful..", Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                // on below line displaying error as toast message.
                message.value = "Fail to verify user : \n" + p0.message
                Toast.makeText(context, "Verification failed..", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                p1: PhoneAuthProvider.ForceResendingToken
            ) {
                // this method is called when code is send
                super.onCodeSent(verificationId, p1)
                verificationID.value = verificationId
            }
        }

        /*TextButton(
            onClick = { *//* Handle forgot password button click *//* },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                "Forgot Password?",
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = 16.sp
            )
        }*/
    }

// on below line creating method to
// sign in with phone credentuals.
private fun signInWithPhoneAuthCredential(
    credential: PhoneAuthCredential,
    auth: FirebaseAuth,
    activity: Activity,
    context: Context,
    message: MutableState<String>
) {
    // on below line signing with credentials.
    auth.signInWithCredential(credential)
        .addOnCompleteListener(activity) { task ->
            // displaying toast message when
            // verification is successful
            if (task.isSuccessful) {
                message.value = "Verification successful"
                Toast.makeText(context, "Verification successful..", Toast.LENGTH_SHORT).show()
            } else {
                // Sign in failed, display a message
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    // The verification code
                    // entered was invalid
                    Toast.makeText(
                        context,
                        "Verification failed.." + (task.exception as FirebaseAuthInvalidCredentialsException).message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
}

// below method is use to send
// verification code to user phone number.
private fun sendVerificationCode(
    number: String,
    auth: FirebaseAuth,
    activity: Activity,
    callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
) {
    // on below line generating options for verification code
    val options = PhoneAuthOptions.newBuilder(auth)
        .setPhoneNumber(number) // Phone number to verify
        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
        .setActivity(activity) // Activity (for callback binding)
        .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}