import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.rememberLottieAnimatable
import androidx.compose.runtime.LaunchedEffect
import com.airbnb.lottie.LottieComposition

@Composable
fun LottieAnimationWithCompletion(
    composition: LottieComposition?,
    onAnimationComplete: () -> Unit
) {
    // Check if composition is null
    if (composition == null) {
        println("Lottie composition is null, animation won't display")
        return
    }
    var isPlaying by remember { mutableStateOf(true) } // Ensure proper imports
    val progress = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        progress.animate(
            composition = composition,
            iterations = 1
        )
        isPlaying = false
        onAnimationComplete()
    }

    LottieAnimation(
        composition = composition,
        progress = { progress.progress },
        contentScale = ContentScale.FillWidth
    )
}