package com.example.nba.ui.players_list

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.example.nba.data.io.PlayerIO

@Composable
fun ConstraintLayoutScope.TeamColumn(
    modifier: Modifier = Modifier,
    playerIO: PlayerIO,
    onTeamClicked: (teamId: Long?) -> Unit
) {
    val team = createRef()
    Column(
        modifier = modifier
            .padding(top = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(0.5.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
            .clickable(
                remember { MutableInteractionSource() },
                indication = rememberRipple(color = MaterialTheme.colorScheme.surfaceTint),
                enabled = true
            ) {
                onTeamClicked.invoke(playerIO.team?.id)
            }
            .constrainAs(team) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = playerIO.team?.name.toString(),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = playerIO.team?.city.toString(),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}