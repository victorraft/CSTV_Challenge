package com.vron.cstv.match_list.presentation

import com.vron.cstv.common.domain.model.Match

sealed class MatchListItem {
    data class MatchItem(val match: Match) : MatchListItem()
    object LoadingItem : MatchListItem()
    object ErrorItem : MatchListItem()
}