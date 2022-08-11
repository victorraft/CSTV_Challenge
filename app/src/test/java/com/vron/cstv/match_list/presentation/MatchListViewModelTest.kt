package com.vron.cstv.match_list.presentation

import com.vron.cstv.InstantExecutorExtension
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class)
internal class MatchListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

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
        val getMatches = GetMatchListFakeImpl()
        val matchListViewModel = MatchListViewModel(getMatches)

        verifyIfIsLoading(matchListViewModel)
        advanceUntilIdle()
        verifyIfMatchesWereReturned(matchListViewModel, fakeMatchesPage1)
    }

    @Test
    fun `When ViewModel is created it displays an error state if the matches cannot be fetched`() = runTest {
        val getMatches = GetMatchListFakeImpl(returnFailure = true)
        val matchListViewModel = MatchListViewModel(getMatches)

        advanceUntilIdle()
        verifyIfErrorStateHappened(matchListViewModel)
    }

    @Test
    fun `When end of the match list is approaching the ViewModel loads more matches`() = runTest {
        val getMatches = GetMatchListFakeImpl()
        val matchListViewModel = MatchListViewModel(getMatches)

        advanceUntilIdle()
        matchListViewModel.onEndOfListApproaching()
        advanceUntilIdle()
        verifyIfMatchesWereReturned(matchListViewModel, fakeMatchesPage1 + fakeMatchesPage2)
    }

    @Test
    fun `When end of the match list is approaching the ViewModel makes only one request`() = runTest {
        val getMatches = GetMatchListFakeImpl()
        val matchListViewModel = MatchListViewModel(getMatches)

        advanceUntilIdle()
        matchListViewModel.onEndOfListApproaching()
        matchListViewModel.onEndOfListApproaching()
        matchListViewModel.onEndOfListApproaching()
        matchListViewModel.onEndOfListApproaching()
        advanceUntilIdle()

        // getMatches should only be called twice: one in the initialization, other in the first time the list approaches the end.
        assertEquals(2, getMatches.timesCalled)
    }

    @Test
    fun `After initial error, calling refresh() loads successfully`() = runTest {
        val getMatches = GetMatchListFakeImpl(returnFailure = true)
        val matchListViewModel = MatchListViewModel(getMatches)

        // Induce error (e.g. no connection)
        advanceUntilIdle()
        verifyIfErrorStateHappened(matchListViewModel)

        // Removes error (e.g. connection was recovered)
        getMatches.returnFailure = false

        // User tapped the error message to retry
        matchListViewModel.refresh()
        advanceUntilIdle()
        verifyIfMatchesWereReturned(matchListViewModel, fakeMatchesPage1)
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