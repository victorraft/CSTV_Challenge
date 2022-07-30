package com.vron.cstv.common.data.remote.mapper

import com.vron.cstv.common.data.remote.dto.PlayerDto
import com.vron.cstv.common.data.remote.dto.TeamDetailsDto
import com.vron.cstv.common.domain.model.Player
import com.vron.cstv.common.domain.model.TeamDetails

class TeamDetailsMapper {

    fun toDomain(teamDetailsDto: TeamDetailsDto): TeamDetails =
        TeamDetails(
            id = teamDetailsDto.id,
            players = teamDetailsDto.players.map { playerDto -> toDomain(playerDto) }
        )

    private fun toDomain(playerDto: PlayerDto): Player =
        Player(
            id = playerDto.id,
            firstName = playerDto.firstName.orEmpty(),
            lastName = playerDto.lastName.orEmpty(),
            name = playerDto.name,
            imageUrl = playerDto.imageUrl
        )
}