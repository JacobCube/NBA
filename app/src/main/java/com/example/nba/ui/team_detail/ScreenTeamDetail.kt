package com.example.nba.ui.team_detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nba.R

/**
 * Screen of a team
 * @param teamId team identifier by which we can download the necessary data
 */
@Preview(showBackground = true)
@Composable
fun ScreenTeamDetail(
    modifier: Modifier = Modifier,
    teamId: String? = null,
) {
    val viewModel = hiltViewModel<TeamDetailViewModel>()
    teamId?.let {
        LaunchedEffect(Unit) {
            viewModel.getTeamById(it)
        }
    }
    val teamState = viewModel.dataManager.team.collectAsState()
    teamState.value?.let { team ->
        ConstraintLayout(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            val (txtAbbreviation, txtLocation, txtFullName, txtName) = createRefs()
            team.fullName?.let {
                Text(
                    text = it,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.constrainAs(txtFullName) {
                        start.linkTo(parent.start, 8.dp)
                        top.linkTo(parent.top, 4.dp)
                    }
                )
            }
            team.name?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier.constrainAs(txtName) {
                        start.linkTo(parent.start, 8.dp)
                        top.linkTo(txtFullName.bottom, 4.dp)
                    }
                )
            }
            team.abbreviation?.let {
                Text(
                    text = stringResource(id = R.string.player_position, it),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.constrainAs(txtAbbreviation) {
                        start.linkTo(txtName.end, 4.dp)
                        top.linkTo(txtFullName.bottom, 4.dp)
                    }
                )
            }
            Text(
                text = stringResource(
                    id = R.string.team_detail_location,
                    team.city ?: "",
                    team.conference ?: "",
                    team.division ?: ""
                ),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.constrainAs(txtLocation) {
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(txtName.bottom, 4.dp)
                }
            )
        }
    }
}