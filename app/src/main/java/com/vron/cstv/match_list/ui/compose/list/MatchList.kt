package com.vron.cstv.match_list.ui.compose.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.vron.cstv.R
import com.vron.cstv.common.domain.fakes.buildFakeMatches
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.ui.compose.theme.CSTVTheme
import com.vron.cstv.common.ui.compose.theme.CardShape

@Composable
fun MatchList(
    matches: List<Match>,
    showLoadingFooter: Boolean = false,
    showErrorFooter: Boolean = false,
    onItemClick: (Match) -> Unit = {},
    onErrorClick: () -> Unit = {},
    listState: LazyListState = rememberLazyListState(),
) {
    val itemPadding = dimensionResource(id = R.dimen.match_item_list_spacing)
    val itemPaddingModifier = Modifier.padding(horizontal = itemPadding, vertical = itemPadding / 2)

    LazyColumn(state = listState) {
        items(matches) { match ->
            MatchListItem(
                match = match,
                modifier = itemPaddingModifier,
                onClick = { onItemClick(match) }
            )
        }

        if (showLoadingFooter) {
            item {
                ListLoadingItem(modifier = itemPaddingModifier)
            }
        } else if (showErrorFooter) {
            item {
                ListErrorItem(modifier = itemPaddingModifier, onClick = onErrorClick)
            }
        }
    }
}

@Composable
fun MatchListCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(
        shape = CardShape,
        modifier = modifier.fillMaxWidth(),
        content = content,
    )
}

@Composable
fun ListLoadingItem(modifier: Modifier = Modifier) {
    MatchListCard(
        modifier = modifier.height(dimensionResource(id = R.dimen.loading_footer_height))
    ) {
        LoadingBar(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}

@Composable
fun LoadingBar(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun ListErrorItem(modifier: Modifier = Modifier, onClick: () -> Unit) {
    MatchListCard(modifier = modifier) {
        ErrorMessage(
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = dimensionResource(id = R.dimen.error_footer_min_height))
                .clickable { onClick() }
        )
    }
}

@Composable
fun ErrorMessage(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.error_loading_more_text),
            fontSize = 18.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun MatchListPreview() {
    CSTVTheme {
        MatchList(matches = buildFakeMatches())
    }
}

@Preview
@Composable
fun MatchListLoadingPreview() {
    CSTVTheme {
        MatchList(matches = emptyList(), showLoadingFooter = true)
    }
}

@Preview
@Composable
fun MatchListErrorPreview() {
    CSTVTheme {
        MatchList(matches = emptyList(), showErrorFooter = true)
    }
}