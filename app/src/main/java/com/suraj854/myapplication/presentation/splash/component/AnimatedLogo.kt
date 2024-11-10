import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.suraj854.myapplication.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LogoAnimation() {
    // Animations for scale, rotation, and alpha (opacity)
    val scale = remember { Animatable(0.5f) }
    val alpha = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }

    // Start the animation as soon as the composable enters the composition
    LaunchedEffect(Unit) {
        // Sequence of animations
        launch {
            // Step 1: Scale in and fade in
            scale.animateTo(
                targetValue = 1.2f,
                animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
            )
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1500, easing = LinearEasing)
            )
        }

        // Step 2: After fade-in, rotation effect
        delay(1500)
        rotation.animateTo(
            targetValue = 360f,
            animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
        )

        // Step 3: Scale out after rotation
        scale.animateTo(
            targetValue = 0.8f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutLinearInEasing)
        )
    }

    // Layout to center the animated image
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo), // Replace with your logo image
            contentDescription = "Animated Logo",
            modifier = Modifier
                .scale(scale.value) // Apply scaling animation
                .alpha(alpha.value) // Apply fade in/out animation
                .graphicsLayer(rotationZ = rotation.value) // Apply rotation animation
                .size(300.dp), // Adjust size
            contentScale = ContentScale.Fit
        )
    }
}
