package com.example.axon.horizontalpager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun Main() {
    val menu = listOf("Salads", "Rice", "Dessert", "Drinks")
    var selectedTabIndex by remember{
        mutableStateOf(0)
    }
    var scope = rememberCoroutineScope()
    var pagerState = rememberPagerState(0) {
        menu.size
    }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TabComp(
            menu,
            pagerState.currentPage
        ) { newValue ->
            scope.launch {
                pagerState.animateScrollToPage(newValue)
            }
        }


            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize(),
                ) {page ->

                when(page) {
                    0->Text(
                        text = "Page: ${menu[page]}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally))

                    1->Text(
                        text = "Page: ${menu[page]}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally))

                    2->Text(
                        text = "Page: ${menu[page]}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally))

                    3->Text(
                        text = "Page: ${menu[page]}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally))

                }

            }
        }

    }


@Composable
private fun TabComp(
    menu: List<String>,
    selectedTabIndexState: Int,
    onSelectedTabIndexStateChanged: (Int) -> Unit
) {

    TabRow(
        selectedTabIndex = selectedTabIndexState,
    ) {
        menu.forEachIndexed { index, menuItem ->
            Tab(
                selected = selectedTabIndexState == index,
                onClick = {
//                    selectedTabIndexState = index
                    onSelectedTabIndexStateChanged(index)
                },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(text = menuItem)


            }

        }

    }
}