package com.example.makelemonade

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.makelemonade.ui.theme.MakeLemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MakeLemonadeApp()
        }
    }
}

@Preview
@Composable
fun MakeLemonadeApp(){
    Header()
    Steps(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun Steps( modifier: Modifier = Modifier){
    var actualStep by remember { mutableStateOf(1) }
    var randomClicks by remember { mutableStateOf((2..4).random()) }
    var clicksCounter by remember { mutableStateOf(0) }

    var actualImageID = when(actualStep){
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    var actualDescriptionID = when(actualStep){
        1 -> R.string.tree
        2 -> R.string.squeeze
        3 -> R.string.drink
        else -> R.string.glass
    }

    var changeStep = {

        if(actualStep == 1){
            randomClicks = (2..4).random()
            clicksCounter = 0
        }

        if(actualStep == 2 && randomClicks >= clicksCounter){
            clicksCounter++
            actualStep = if(clicksCounter == randomClicks) 3 else 2
        } else {
            when(actualStep){
                1 -> actualStep = 2
                3 -> actualStep = 4
                else -> actualStep = 1
            }
        }
    }

    ImageWithDescription(
        modifier = modifier,
        changeStep,
        actualImageID,
        actualDescriptionID,
        randomClicks,
        clicksCounter,
        actualStep
    )

}

@Composable
fun ImageWithDescription(
    modifier: Modifier = Modifier,
    onStepClicked: () -> Unit,
    imageId: Int,
    descriptionId: Int,
    randClicks: Int,
    clickCounter: Int,
    actualStep: Int ){

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = onStepClicked,
            modifier = Modifier.shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(195, 236, 210))
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "Tree"
            )
        }

        Text(
            stringResource(id = descriptionId),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 20.dp)
        )
        if(actualStep == 2){
            Text(text="Random clicks = Clicks counter: $randClicks = $clickCounter", fontSize = 8.sp)
        }
    }
}

@Composable
fun Header(){
    Text(
        text = stringResource(id = R.string.title),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier
            .background(Color(249, 228, 76))
            .fillMaxWidth()
            .padding(vertical = 14.dp)
    )
}

