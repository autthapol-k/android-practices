package com.autthapol.instagramuicompose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val lazyGridState = rememberLazyGridState()
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = { TopAppBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            ProfileSection(modifier = Modifier.padding(8.dp))
            ButtonsSection(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(24.dp))
            HighlightsSection(
                modifier = Modifier.padding(horizontal = 16.dp), highlights = listOf(
                    ImageWithText(
                        image = painterResource(id = R.drawable.youtube), text = "YouTube"
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.qa), text = "Q&A"
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.discord), text = "Discord"
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.telegram), text = "Telegram"
                    )
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            PostTabView(
                images = listOf(
                    ImageWithText(
                        image = painterResource(id = R.drawable.ic_grid), text = "grid"
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.ic_reels), text = "reels"
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.ic_igtv), text = "igtv"
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.profile), text = "profile"
                    )
                )
            ) {
                selectedTabIndex = it
            }
            when (selectedTabIndex) {
                0 -> PostGridView(
                    state = lazyGridState,
                    posts = listOf(
                        painterResource(id = R.drawable.kmm),
                        painterResource(id = R.drawable.intermediate_dev),
                        painterResource(id = R.drawable.master_logical_thinking),
                        painterResource(id = R.drawable.bad_habits),
                        painterResource(id = R.drawable.multiple_languages),
                        painterResource(id = R.drawable.learn_coding_fast),
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    TopAppBar(title = {
        Text(
            text = "philipplackner_official",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
    }, actions = {
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bell),
                contentDescription = "notification",
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
        }
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dotmenu),
                contentDescription = "more",
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
        }
    }, navigationIcon = {
        IconButton(onClick = { }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
        }
    })
}

@Preview(showBackground = true)
@Composable
fun ProfileSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            RoundedImage(
                image = painterResource(id = R.drawable.philipp), modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            StatSection(modifier = modifier.fillMaxWidth())
        }
        ProfileDescription(
            modifier = modifier.fillMaxWidth(),
            displayName = "Programming Mentor",
            description = "10 years of coding experience\n" + "Want me to make your app? Send me an email!\n" + "Subscribe to my YouTube channel!",
            url = "https://youtube.com/c/PhilippLackner",
            followedBy = listOf("codinginflow", "miakhalifa"),
            otherCount = 17
        )
    }
}


@Composable
fun RoundedImage(modifier: Modifier = Modifier, image: Painter) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(1.dp, Color.LightGray, shape = CircleShape)
            .padding(3.dp)
            .clip(CircleShape)
    )
}

@Composable
fun StatSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ProfileStat(number = "601", name = "Posts")
        ProfileStat(number = "99.8K", name = "Followers")
        ProfileStat(number = "72", name = "Following")
    }
}

@Composable
fun ProfileStat(modifier: Modifier = Modifier, number: String, name: String) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = number,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(text = name)
    }
}

@Composable
fun ProfileDescription(
    modifier: Modifier = Modifier,
    displayName: String,
    description: String,
    url: String,
    followedBy: List<String>,
    otherCount: Int
) {
    val letterSpacing = 0.5.sp
    val lineHeight = 20.sp
    Column(modifier = modifier) {
        Text(
            text = displayName,
            fontWeight = FontWeight.Bold,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        Text(
            text = description, letterSpacing = letterSpacing, lineHeight = lineHeight
        )
        Text(
            text = url,
            color = Color(0xFF3D3D91),
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        if (followedBy.isNotEmpty()) {
            Text(text = buildAnnotatedString {
                val boldStyle = SpanStyle(
                    color = Color.Black, fontWeight = FontWeight.Bold
                )
                append("Followed by ")
                followedBy.forEachIndexed { index, name ->
                    this.pushStyle(boldStyle)
                    append(name)
                    pop()
                    if (index < followedBy.size - 1) {
                        append(", ")
                    }
                }
                if (otherCount > 2) {
                    append(" and ")
                    pushStyle(boldStyle)
                    append("$otherCount others")
                }
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonsSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ActionButton(
            text = "Following",
            image = Icons.Default.KeyboardArrowDown,
            modifier = Modifier.widthIn(min = 100.dp)
        )
        ActionButton(
            text = "Message", modifier = Modifier.widthIn(min = 100.dp)
        )
        ActionButton(
            text = "Email", modifier = Modifier.widthIn(min = 100.dp)
        )
        ActionButton(image = Icons.Default.KeyboardArrowDown, modifier = Modifier.width(40.dp))
    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier, text: String? = null, image: ImageVector? = null
) {
    TextButton(
        onClick = { },
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(width = 2.dp, color = Color.LightGray),
        modifier = modifier.height(36.dp)
    ) {
        if (text != null) Text(text = text, color = Color.Black, fontWeight = FontWeight.Bold)
        if (image != null) Icon(
            imageVector = image, contentDescription = null, tint = Color.Black
        )
    }
}

@Composable
fun HighlightsSection(modifier: Modifier = Modifier, highlights: List<ImageWithText>) {
    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(highlights) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedImage(image = it.image, modifier = Modifier.size(70.dp))
                Text(
                    text = it.text, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center
                )
            }
        }
    }
}

data class ImageWithText(
    val image: Painter,
    val text: String,
)

@Composable
fun PostTabView(
    modifier: Modifier = Modifier,
    images: List<ImageWithText>,
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val inactiveColor = Color(0xFF777777)

    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = Color.Transparent,
        contentColor = Color.Black,
        modifier = modifier,
        indicator = {
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(it[selectedIndex]),
                color = Color.Gray
            )
        },
    ) {
        images.forEachIndexed { index, item ->
            Tab(
                selected = index == selectedIndex,
                selectedContentColor = Color.Black,
                unselectedContentColor = inactiveColor,
                onClick = {
                    selectedIndex = index
                    onTabSelected(index)
                }
            ) {
                Icon(
                    painter = item.image,
                    contentDescription = item.text,
                    tint = if (selectedIndex == index) Color.Black else inactiveColor,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(20.dp)
                )
            }
        }
    }
}

@Composable
fun PostGridView(
    modifier: Modifier = Modifier,
    state: LazyGridState,
    posts: List<Painter>
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    LazyVerticalGrid(
        state = state,
        modifier = modifier.heightIn(max = screenHeight.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(posts) {
            Image(
                painter = it,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(1f)
            )
        }
    }
}
