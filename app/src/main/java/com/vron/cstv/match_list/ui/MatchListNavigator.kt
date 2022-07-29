package com.vron.cstv.match_list.ui

import com.vron.cstv.common.domain.model.Match

interface MatchListNavigator {
    fun openMatchDetails(match: Match)
}