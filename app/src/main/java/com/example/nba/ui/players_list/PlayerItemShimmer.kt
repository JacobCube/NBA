package com.example.nba.ui.players_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

/** Widget with shimmer animation for loading player item */
@Composable
fun PlayerItemShimmer(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        val (txtName, txtHeight, txtWeight, txtPosition, team, imgWeight, imgHeight) = createRefs()
        Text(
            text = "",
            fontSize = 16.sp,
            modifier =  Modifier
                .fillMaxWidth(0.35f)
                .constrainAs(txtName) {
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(parent.top, 4.dp)
                }.background(MaterialTheme.colorScheme.surfaceTint, RoundedCornerShape(6.dp))
        )
        Column(
            modifier = Modifier
                .constrainAs(team) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }.padding(8.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "",
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .background(MaterialTheme.colorScheme.surfaceTint, RoundedCornerShape(6.dp))
            )
            Text(
                text = "",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(0.35f)
                    .background(MaterialTheme.colorScheme.surfaceTint, RoundedCornerShape(6.dp))
            )
        }
        Text(
            text = "",
            fontSize = 16.sp,
            modifier =  Modifier
                .fillMaxWidth(0.1f)
                .constrainAs(txtPosition) {
                    start.linkTo(txtName.end, 4.dp)
                    top.linkTo(parent.top, 4.dp)
                }.background(MaterialTheme.colorScheme.surfaceTint, RoundedCornerShape(6.dp))
        )
        Spacer(
            modifier = Modifier
                .size(32.dp)
                .constrainAs(imgHeight) {
                    top.linkTo(txtName.bottom, 4.dp)
                    start.linkTo(parent.start, 8.dp)
                }
                .background(MaterialTheme.colorScheme.surfaceTint, RoundedCornerShape(6.dp))
        )
        Text(
            text = "",
            fontSize = 14.sp,
            modifier =  Modifier
                .fillMaxWidth(0.2f)
                .constrainAs(txtHeight) {
                    start.linkTo(imgHeight.end, 6.dp)
                    linkTo(imgHeight.top, imgHeight.bottom)
                }
                .background(MaterialTheme.colorScheme.surfaceTint, RoundedCornerShape(6.dp))
        )
        Spacer(
            modifier = Modifier
                .size(32.dp)
                .constrainAs(imgWeight) {
                    top.linkTo(imgHeight.bottom, 4.dp)
                    start.linkTo(parent.start, 8.dp)
                }
                .background(MaterialTheme.colorScheme.surfaceTint, RoundedCornerShape(6.dp))
        )
        Text(
            text = "",
            fontSize = 14.sp,
            modifier =  Modifier
                .fillMaxWidth(0.2f)
                .constrainAs(txtWeight) {
                    start.linkTo(imgWeight.end, 6.dp)
                    linkTo(imgWeight.top, imgWeight.bottom)
                }
                .background(MaterialTheme.colorScheme.surfaceTint, RoundedCornerShape(6.dp))
        )
    }
}