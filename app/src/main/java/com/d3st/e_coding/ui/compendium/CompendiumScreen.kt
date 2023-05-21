package com.d3st.e_coding.ui.compendium

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.d3st.e_coding.data.foodadditivesrepository.database.AdditiveType
import com.d3st.e_coding.presentation.theme.EcodingTheme
import com.d3st.e_coding.ui.details.AdditiveCard
import com.d3st.e_coding.ui.details.DetailsFoodAdditiveDomainModel

@Composable
fun CompendiumScreen(
    modifier: Modifier = Modifier,
    viewModel: CompendiumViewModel,
    onAdditiveClick: (String) -> Unit,
) {
    viewModel.getAllAdditives()
    val uiState = viewModel.uiState.collectAsState()
    uiState.value.additives?.let { CompendiumList(list = it, onAdditiveClick = onAdditiveClick) }

}


//@Composable
//fun CompendiumList(list: List<DetailsFoodAdditiveDomainModel>,onAdditiveClick: (String) -> Unit) {
//    Column(modifier = Modifier) {
//        list.forEach {
//            Text(it.canonicalName, modifier = Modifier.clickable { onAdditiveClick(it.canonicalName) })
//        }
//    }
//}
@Composable
fun CompendiumList(list: List<DetailsFoodAdditiveDomainModel>, onAdditiveClick: (String) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(list.size) {
            val item = list[it]
            AdditiveCard(
                text = item.canonicalName,
                rating = item.harmfulness,
                modifier = Modifier.clickable {
                    onAdditiveClick(
                        item.canonicalName
                    )
                },
            )
        }
    }
//    Column(modifier = Modifier) {
//        list.forEach {
//            Text(it.canonicalName, modifier = Modifier.clickable { onAdditiveClick(it.canonicalName) })
//        }
//    }
}


@Preview
@Composable
fun CompendiumListPreview() {
    EcodingTheme {
        CompendiumList(
            list = listOf(
                DetailsFoodAdditiveDomainModel(
                    canonicalName = "E202",
                    name = "Сорбат калия",
                    alias = listOf("Сорбат калия", "Калия сорбат"),
                    harmfulness = 1,
                    isBelongToGroup = false,
                    groupParentCanonicalName = "",
                    isAllowedRU = true,
                    isAllowedUS = true,
                    isAllowedEU = true,
                    functionalClass = listOf(AdditiveType.PRESERVATIVE),
                    origin = "artificial",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi sit amet convallis mi. Nullam tincidunt dignissim dignissim. Maecenas quis tincidunt diam. Vestibulum ac elit at metus aliquam maximus. Cras rutrum fermentum mi vel consequat. Pellentesque nec maximus mi, in dictum sapien. Ut in varius elit. Duis ut consequat tellus. ",
                    violatedSystems = listOf("ЖКТ", "легкие")
                ),
                DetailsFoodAdditiveDomainModel(
                    canonicalName = "E211",
                    name = "Бензоат натрия>",
                    alias = listOf("Бензоат натрия", "Натрия Бензоат"),
                    harmfulness = 5,
                    isBelongToGroup = false,
                    groupParentCanonicalName = "",
                    isAllowedRU = true,
                    isAllowedUS = true,
                    isAllowedEU = true,
                    functionalClass = listOf(AdditiveType.PRESERVATIVE),
                    origin = "artificial",
                    description = "Phasellus sapien purus, vestibulum ac est et, porttitor vestibulum ex. Suspendisse dictum elit ut leo venenatis congue porttitor at nisi. Pellentesque facilisis dui eget diam mollis, ac varius lacus molestie. Nunc a augue quam. Aenean porta mauris sapien, id viverra diam hendrerit finibus. Donec varius elementum felis vitae egestas.",
                    violatedSystems = listOf("ЖКТ")
                )
            ),
            onAdditiveClick = {}
        )
    }
}