package com.vron.cstv.match_list.presentation

import com.vron.cstv.match_list.domain.model.Match

data class ViewState(
    val matchList: List<Match>,
    val isLoading: Boolean
)