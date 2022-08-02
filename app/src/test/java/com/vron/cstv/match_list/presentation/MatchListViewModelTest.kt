package com.vron.cstv.match_list.presentation

import com.vron.cstv.InstantExecutorExtension
import com.vron.cstv.common.domain.model.League
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.MatchStatus
import com.vron.cstv.common.domain.model.Serie
import com.vron.cstv.getOrAwaitValue
import com.vron.cstv.match_list.domain.usecase.GetMatchList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class)
internal class MatchListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private fun buildFakeMatches(count: Int) = List(count) {
        Match(
            id = it,
            teams = emptyList(),
            league = League("", ""),
            serie = Serie(""),
            status = MatchStatus.RUNNING,
            beginAt = ""
        )
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When ViewModel is created it loads list of matches`() = runTest {
        // Arrange
        val fakeMatches = buildFakeMatches(20)
        val getMatchList = object : GetMatchList {
            override suspend fun execute(params: GetMatchList.Params): Result<List<Match>> = Result.success(fakeMatches)
        }
        val matchListViewModel = MatchListViewModel(getMatchList)

        // Act & Assert
        verifyIfIsLoading(matchListViewModel)
        advanceUntilIdle()
        verifyIfMatchesWereReturned(matchListViewModel, fakeMatches)
    }

    @Test
    fun `When ViewModel is created it displays an error state if the matches cannot be fetched`() = runTest {
        // Arrange
        val getMatchList = object : GetMatchList {
            override suspend fun execute(params: GetMatchList.Params): Result<List<Match>> = Result.failure(IOException())
        }
        val matchListViewModel = MatchListViewModel(getMatchList)

        // Act & Assert
        verifyIfIsLoading(matchListViewModel)
        advanceUntilIdle()
        verifyIfErrorStateHappened(matchListViewModel)
    }

    private fun verifyIfIsLoading(matchListViewModel: MatchListViewModel) {
        val state = matchListViewModel.viewState.getOrAwaitValue()
        assertEquals(ViewState(showLoading = true), state)
    }

    private fun verifyIfMatchesWereReturned(matchListViewModel: MatchListViewModel, expectedMatches: List<Match>) {
        val state = matchListViewModel.viewState.getOrAwaitValue()
        assertEquals(ViewState(matchList = expectedMatches), state)
    }

    private fun verifyIfErrorStateHappened(matchListViewModel: MatchListViewModel) {
        val state = matchListViewModel.viewState.getOrAwaitValue()
        assertEquals(ViewState(showError = true), state)
    }
}