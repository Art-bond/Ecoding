package com.d3st.e_coding.ui.details


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.d3st.e_coding.data.foodadditivesrepository.database.AdditiveType
import com.d3st.e_coding.presentation.theme.EcodingTheme
import com.d3st.e_coding.presentation.theme.Grey100

@Composable
fun DetailsScreen(
    additive: DetailsFoodAdditiveDomainModel,
    modifier: Modifier = Modifier,
) {
    Column {
        Row {
            Card(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(0.3f),
                backgroundColor = Grey100
            ) {
                Column {
                    AdditiveCard(text = additive.canonicalName, rating = additive.harmfulness)
                    RatingElement(rating = additive.harmfulness)
                }

            }
            Column {
                Text(
                    modifier = Modifier,
                    text = additive.name,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                )
                Card(backgroundColor = Grey100, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text("Разрешен в России:")
                        if (additive.isAllowedRU) Text("Да") else Text("Нет")
                        Text("Разрешен в США:")
                        if (additive.isAllowedUS) Text("Да") else Text("Нет")
                        Text("Разрешен в ЕС:")
                        if (additive.isAllowedEU) Text("Да") else Text("Нет")
                        Text("Тип:")
                        Text(additive.functionalClass.toString())
                        Text("Происхождение:")
                        Text(additive.origin)
                        Text("Отрицательно влияет на:")
                        repeat(additive.violatedSystems.size) {
                            Text(additive.violatedSystems.get(it))
                        }
                    }
                }
            }

        }
        Card(backgroundColor = Grey100) {
            Text(additive.description)
        }
    }
}

fun MainInfoCard(modifier: Modifier = Modifier) {

}

fun AdditionalInfoCard(modifier: Modifier = Modifier) {

}

fun DescriptionCard(modifier: Modifier = Modifier) {

}


@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=1080px,height=2280px,dpi=409"
)
@Composable
fun DetailsPreview() {
    val additive = DetailsFoodAdditiveDomainModel(
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
    )
    EcodingTheme {
        DetailsScreen(additive = additive)
    }
}