package com.vron.cstv.app.ui

import android.app.Activity
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.match_list.ui.MatchListNavigator
import com.vron.cstv.app.ui.match_details.MatchDetailsActivityCompose

class AppJetpackComposeNavigator(
    private val activity: Activity
) : MatchListNavigator {

    override fun openMatchDetails(match: Match) {
        val intent = MatchDetailsActivityCompose.buildIntent(activity, match)
        activity.startActivity(intent)
    }
}