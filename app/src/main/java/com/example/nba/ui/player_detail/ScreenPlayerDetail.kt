package com.example.nba.ui.player_detail

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

/**
 * Screen of an NBA player detail
 * @param playerId player identification for either accessing local or network services to retrieve needed information
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
fun ScreenPlayerDetail(
    modifier: Modifier = Modifier,
    playerId: String? = null,
    onTeamClicked: (teamId: Long?) -> Unit = {},
) {
    val viewModel = hiltViewModel<PlayerDetailViewModel>()
    playerId?.let {
        LaunchedEffect(Unit) {
            viewModel.getPlayerById(it)
        }
    }
    val playerState = viewModel.dataManager.player.collectAsState()
    playerState.value?.let { player ->
        ConstraintLayout(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            val (txtName, txtHeight, txtWeight, txtPosition, team, imgWeight, imgHeight) = createRefs()

            Text(
                text = (player.firstName?: "") + " " + (player.lastName ?: ""),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier =  Modifier.constrainAs(txtName) {
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(parent.top, 4.dp)
                }
            )
            if(player.team != null) {
                Column(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(0.5.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
                        .clickable(
                            remember { MutableInteractionSource() },
                            indication = rememberRipple(color = MaterialTheme.colorScheme.surfaceTint),
                            enabled = true
                        ) {
                            onTeamClicked.invoke(player.team.id)
                        }.constrainAs(team) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = player.team.name.toString(),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = player.team.city.toString(),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.inversePrimary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            if(player.position.isNullOrBlank().not()) {
                Text(
                    text = stringResource(
                        com.example.nba.R.string.player_position,
                        player.position.toString()
                    ),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    modifier =  Modifier.constrainAs(txtPosition) {
                        start.linkTo(txtName.end, 4.dp)
                        top.linkTo(parent.top, 4.dp)
                    }
                )
            }
            if(player.heightFeet != null && player.heightInches != null) {
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
                        player.heightFeet,
                        player.heightInches
                    ),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    modifier =  Modifier.constrainAs(txtHeight) {
                        start.linkTo(imgHeight.end, 6.dp)
                        linkTo(imgHeight.top, imgHeight.bottom)
                    }
                )
            }

            if(player.weightPounds != null) {
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
                        player.weightPounds
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
}