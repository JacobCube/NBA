package com.example.nba.ui.players_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nba.data.io.PlayerIO
import com.example.nba.ui.navigation.NBAActivity.Companion.PREVIEW_DEFAULT_PLAYER

/** Player item displaying given data */
@Preview(showBackground = true)
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerItem(
    modifier: Modifier = Modifier,
    playerIO: PlayerIO = PREVIEW_DEFAULT_PLAYER,
    onItemClicked: (playerId: Long?) -> Unit = {},
    onTeamClicked: (teamId: Long?) -> Unit = {},
) {
    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .clickable(
                remember { MutableInteractionSource() },
                indication = rememberRipple(color = MaterialTheme.colorScheme.surfaceTint),
                enabled = true
            ) {
                onItemClicked.invoke(playerIO.id)
            }
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        val (txtName, txtHeight, txtWeight, txtPosition, imgWeight, imgHeight) = createRefs()

        Text(
            text = (playerIO.firstName?: "") + " " + (playerIO.lastName ?: ""),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier =  Modifier.constrainAs(txtName) {
                start.linkTo(parent.start, 8.dp)
                top.linkTo(parent.top, 4.dp)
            }
        )
        if(playerIO.team != null) {
            TeamColumn(playerIO = playerIO, onTeamClicked = onTeamClicked)
        }
        if(playerIO.position.isNullOrBlank().not()) {
            Text(
                text = stringResource(
                    com.example.nba.R.string.player_position,
                    playerIO.position.toString()
                ),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier =  Modifier.constrainAs(txtPosition) {
                    start.linkTo(txtName.end, 4.dp)
                    top.linkTo(parent.top, 4.dp)
                }
            )
        }
        if(playerIO.heightFeet != null && playerIO.heightInches != null) {
            GlideImage(
                modifier = Modifier
                    .size(32.dp)
                    .constrainAs(imgHeight) {
                        top.linkTo(txtName.bottom, 4.dp)
                        start.linkTo(parent.start, 8.dp)
                    },
                model = "https://cdn1.iconfinder.com/data/icons/medical-color-filled-line-part-1/160/22-512.png",
                contentDescription = null
            )
            Text(
                text = stringResource(
                    com.example.nba.R.string.player_weight_feet_inches,
                    playerIO.heightFeet,
                    playerIO.heightInches
                ),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier =  Modifier.constrainAs(txtHeight) {
                    start.linkTo(imgHeight.end, 6.dp)
                    linkTo(imgHeight.top, imgHeight.bottom)
                }
            )
        }

        if(playerIO.weightPounds != null) {
            GlideImage(
                modifier = Modifier
                    .size(32.dp)
                    .constrainAs(imgWeight) {
                        top.linkTo(imgHeight.bottom, 4.dp)
                        start.linkTo(parent.start, 8.dp)
                    },
                model = "https://cdn-icons-png.flaticon.com/512/249/249236.png",
                contentDescription = null,
            )
            Text(
                text = stringResource(
                    com.example.nba.R.string.player_weight_pounds,
                    playerIO.weightPounds
                ),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier =  Modifier.constrainAs(txtWeight) {
                    start.linkTo(imgWeight.end, 6.dp)
                    linkTo(imgWeight.top, imgWeight.bottom)
                }
            )
        }
    }
}