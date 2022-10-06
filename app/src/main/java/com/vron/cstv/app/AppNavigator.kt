package com.vron.cstv.app

import android.app.Activity
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.match_details.ui.compose.MatchDetailsActivityCompose
import com.vron.cstv.match_list.ui.MatchListNavigator

class AppNavigator(
    private val activity: Activity
) : MatchListNavigator {

    override fun openMatchDetails(match: Match) {
        val intent = MatchDetailsActivityCompose.buildIntent(activity, match)
        activity.startActivity(intent)
    }
}