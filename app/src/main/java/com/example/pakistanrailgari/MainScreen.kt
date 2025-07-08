package com.example.pakistanrailgari

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.pakistanrailgari.ui.theme.Black
import com.example.pakistanrailgari.ui.theme.Purple40
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun MainScreen(onBackPress: () -> Unit) {

    Scaffold(topBar = {
        SimpleTopBar(
            title = stringResource(
                R.string.app_name
            ), backIcon = R.drawable.ic_back__2_, onClick = {
                onBackPress()
            })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Purple40) //Main Page Color
                .padding(paddingValues)
        ) {

        }
    }
}

@Composable
fun SimpleTopBar(
    title: String = "",
    @DrawableRes backIcon: Int = -1,
    onClick: (() -> Unit)? = null,
    backPressTintColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable RowScope.() -> Unit = {},
) {
    AppTopBar(mainContent = {
        if (backIcon != -1) {
            IconButton(onClick = {
                onClick?.invoke()
            }) {
                Image(
                    painter = painterResource(id = backIcon),
                    contentDescription = null,
                    modifier = Modifier.size(14.sdp),
                    colorFilter = ColorFilter.tint(backPressTintColor)//if (title != "") ColorFilter.tint(MyColors.WhiteColor) else null
                )
            }
        } else {
            Spacer(modifier = Modifier.size(10.sdp))
        }
        if (title != "") {
            AppMainText(
                text = title,
                fontSize = 14,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Black
            )
        }
    }, content = content)
}

@Composable
fun AppTopBar(
    mainContent: @Composable RowScope.() -> Unit,
    content: @Composable RowScope.() -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(45.sdp)
            .padding(vertical = 4.sdp, horizontal = 8.sdp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            content = mainContent
        )
        Row(
            verticalAlignment = Alignment.CenterVertically, content = content
        )
    }
}