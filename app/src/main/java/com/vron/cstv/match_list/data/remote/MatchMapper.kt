package com.vron.cstv.match_list.data.remote

import com.vron.cstv.match_list.data.remote.dto.MatchDto
import com.vron.cstv.match_list.domain.model.Match

class MatchMapper {
    fun toDomain(matchDto: MatchDto): Match {
        TODO()
//        return Match(
//            id = matchDto.id,
//        )
    }
}