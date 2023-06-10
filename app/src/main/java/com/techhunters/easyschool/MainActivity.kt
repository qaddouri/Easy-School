package com.techhunters.easyschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.techhunters.easyschool.core.navigation.Screen
import com.techhunters.easyschool.core.ui.theme.EasySchoolTheme
import com.techhunters.easyschool.features.auth.presentation.PhoneLoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasySchoolTheme {
                // A surface container using the 'background' color from the theme
                /* Surface(
                     modifier = Modifier.fillMaxSize(),
                     color = MaterialTheme.colorScheme.background
                 ) {
                     Greeting("Android")
                 }*/


            }

            PhoneLoginScreen(LocalContext.current)

            /*val painter = painterResource(id = R.drawable.stu)


            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                RoleCard(
                    painter = painter,
                    title = "Student",
                    modifier = Modifier.background(Color.Blue)
                )

                Spacer(modifier = Modifier.height(20.dp))

                FirstCard(
                    painter = painter, title = "Reports updates",
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.height(20.dp))

                SecondCard(painter = painter, title = "Image card", modifier = Modifier)*/

            }
        }
    }




@Composable
fun MainScreen(navController: NavController) {
    Button(
        onClick = {
            navController.navigate(Screen.DetailsScreen.withArgs("Abdo"))
        }) {

    }
}

@Composable
fun DetailsScreen(name: String?) {

}

@Composable
fun RoleCard(painter: Painter, title: String, modifier: Modifier) {

    Card(
        modifier = modifier
            .height(80.dp)
            .width(250.dp)
            .background(Color.Gray)
    ) {


    }


}

@Composable
fun FirstCard(painter: Painter, title: String, modifier: Modifier) {

    Card(
        modifier = modifier
            .size(200.dp)
            .background(Color.Gray)
    ) {
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Icon(
                painter = painter,
                contentDescription = "Favorite Icon",
                tint = Color.Red,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = title, style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

        }


    }


}

@Composable
fun SecondCard(painter: Painter, title: String, modifier: Modifier) {

    Card(
        modifier = modifier
            .height(300.dp)
            .width(200.dp)
            .background(Color.Gray)
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .background(Color.Gray),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Image(
                painter = painter,
                contentDescription = title,
                contentScale = ContentScale.Crop,
            )

            Text(
                text = title, style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

        }


    }


}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EasySchoolTheme {
        SecondCard(
            painter = painterResource(id = R.drawable.stu),
            title = "try this ...",
            modifier = Modifier
        )
    }
}