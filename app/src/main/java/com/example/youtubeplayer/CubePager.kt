package com.example.youtubeplayer

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlin.math.absoluteValue
import kotlin.math.min

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CubePager(
    pageCount: Int,
    content: @Composable (Int) -> Unit
) {

    val state = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = {
            pageCount
        }
    )

    val scale by remember {
        derivedStateOf {
            1f - (state.currentPageOffsetFraction.absoluteValue) * .3f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val offsetFromStart = state.offsetForPage(0).absoluteValue
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .offset { IntOffset(0, 150.dp.roundToPx()) }
                .scale(scaleX = .6f, scaleY = .24f)
                .scale(scale)
                .rotate(offsetFromStart * 90f)
                .blur(
                    radius = 110.dp,
                    edgeTreatment = BlurredEdgeTreatment.Unbounded,
                )
                .background(Color.Black)
        )

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .scale(1f, scaleY = scale),
            state = state,
            pageSpacing = 0.dp,
            userScrollEnabled = true,
            reverseLayout = false,
            contentPadding = PaddingValues(0.dp),
            beyondBoundsPageCount = 0,
            pageSize = PageSize.Fill,
            flingBehavior = PagerDefaults.flingBehavior(state = state),
            key = null,
            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                Orientation.Horizontal
            ),
            pageContent = { page ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight()

                        .graphicsLayer {
                            val pageOffset = state.offsetForPage(page)
                            val offScreenRight = pageOffset < 0f
                            val deg = 105f
                            val interpolated =
                                FastOutLinearInEasing.transform(pageOffset.absoluteValue)
                            rotationY = min(interpolated * if (offScreenRight) deg else -deg, 90f)

                            transformOrigin = TransformOrigin(
                                pivotFractionX = if (offScreenRight) 0f else 1f,
                                pivotFractionY = .5f
                            )
                        }
                        .drawWithContent {
                            val pageOffset = state.offsetForPage(page)

                            drawContent()
                            drawRect(
                                Color.Black.copy(
                                    (pageOffset.absoluteValue * .7f)
                                )
                            )
                        }
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    content(page)
                }
            }
        )
    }
}

// ACTUAL OFFSET
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.offsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}