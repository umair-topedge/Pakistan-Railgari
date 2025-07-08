package com.example.pakistanrailgari

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.example.pakistanrailgari.ui.theme.Black
import com.example.pakistanrailgari.ui.theme.Pink40
import com.example.pakistanrailgari.ui.theme.Purple80
import com.example.pakistanrailgari.ui.theme.ironGray
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun SplashScreenContent(
    showProgress: Boolean,
    progress: Float,
) {
    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.6f)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Image(
                            modifier = Modifier.size(70.sdp),
                            painter = painterResource(R.drawable.ic_train),
                            contentDescription = null
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            NormalTextAnnotated(text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = ironGray,
                                        fontSize = 11.ssp,
                                        fontStyle = FontStyle.Italic
                                    )
                                ) {
                                    append("\n" + stringResource(R.string.effiecent_reliable))
                                }
                            }, textAlign = TextAlign.Center)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center

                    ) {
                        val imageLoader = ImageLoader.Builder(LocalContext.current)
                            .components {
                                add(GifDecoder.Factory())
                            }
                            .build()

                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://gimpchat.com/files/4643_OvercrowdedTrain.gif") // your GIF URL or local file
                                .crossfade(true)
                                .build(),
                            contentDescription = "GIF animation",
                            imageLoader = imageLoader,

                            )
                    }

                }
                Column(
                    modifier = Modifier
                        .weight(0.1f),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 34.dp)
                            .padding(bottom = 24.dp)
                    ) {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = showProgress,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 6.sdp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    AppMainText(
                                        text = stringResource(R.string.loading),
                                        color = ironGray,
                                        isSingleLine = true
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.sdp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(5.sdp)
                                        .clip(CircleShape)
                                        .clip(CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    LinearProgressIndicator(
                                        modifier = Modifier.fillMaxSize(),
                                        gapSize = 0.dp,
                                        progress = { progress },
                                        color = Purple80,
                                        drawStopIndicator = {},
                                        trackColor = Color.Transparent
                                    )
                                }
                            }
                        }
                        androidx.compose.animation.AnimatedVisibility(
                            visible = !showProgress,
                            enter = fadeIn(tween(1000)),
                            exit = fadeOut()
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                CircularProgressIndicator(
                                    color = Pink40,
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun NormalTextAnnotated(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign? = null,
    fontFamily: FontFamily = FontFamily.Default
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        fontFamily = fontFamily
    )
}

@Composable
fun AppMainText(
    text: String,
    fontSize: Int = 12,
    color: Color = Black,
    isSingleLine: Boolean = false,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    fontFamily: FontFamily = FontFamily.Default,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textDecoration: TextDecoration = TextDecoration.None,
) {

    Text(
        text = text,
        fontSize = fontSize.ssp,
        fontWeight = fontWeight,
        color = color,
        fontFamily = fontFamily,
        textAlign = textAlign,
        overflow = overflow,
        lineHeight = fontSize.ssp / .65,
        maxLines = if (isSingleLine) 1 else Int.MAX_VALUE,
        textDecoration = textDecoration
    )
}
