package com.vron.cstv.match_list.presentation

import com.vron.cstv.common.domain.model.Match

data class ViewState(
    val matchList: List<Match>,
    val isLoading: Boolean
)