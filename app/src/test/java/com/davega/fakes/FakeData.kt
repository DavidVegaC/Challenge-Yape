package com.davega.fakes

import com.davega.domain.models.Recipe
import com.davega.domain.models.SettingType
import com.davega.domain.models.Settings
import kotlinx.coroutines.flow.*
import java.util.*

object FakeData {
    val recipes: Flow<List<Recipe>> = flow {
        val recipes = listOf(
            Recipe(
                1,
                "Seco de Carne",
                "https://comidasperuanas.net/wp-content/uploads/2020/11/Seco-de-carne.webp",
                "Andrea Demarchi",
                listOf(
                    "1 kilo de carne cortada en trozos",
                    "2 cebollas rojas cortadas en cuadritos",
                    "1 cucharada de ajo molido",
                    "1 taza de aceite",
                    "1/2 taza de ají amarillo licuado",
                    "1/2 taza de ají mirasol licuado",
                    "4 papas cortadas en dos",
                    "2 zanahorias crudas cortadas en rodajas"
                ),
                listOf(
                    "En la olla agrega un chorro de aceite y coloca el kilo de carne cortada en trozos a dorar y retira.",
                    "Luego, en la olla ya desocupada se realiza un aderezo con las dos cebollas rojas cortadas en cuadritos. Deja cocinar por aproximadamente 5 minutos.",
                    "Ahora, agrega una cucharada de ajo molido y deja cocinar de nuevo por al menos un par de minutos.",
                    "Seguido, agrega media taza de ají amarillo licuado y media taza de ají mirasol licuado. deja cocinar por un aproximado de 5 minutos y mezcla todo muy bien a fuego medio.",
                    "Luego, añade un vaso de cerveza rubia o chicha de jora. Una vez listo, agrega un vaso de culantro bien licuado y deja hervir.",
                    "A continuación, agrega la sal, la pimienta y el comino al gusto. Baja el fuego e incorpora la carne. Cubre con agua y tapa, deja cocer a fuego bajo hasta dejar suave la carne."
                ),
                listOf(
                    "-6.77137",
                    "-79.84088"
                ),
                Date(),
                Date()
            ),
            Recipe(
                2,
                "Ceviche de Pescado",
                "https://comidasperuanas.net/wp-content/uploads/2015/08/Ceviche-Peruano-de-Pescado.webp",
                "Andrea Demarchi",
                listOf(
                    "1 kilo de pescado fresco y de preferencia que no sea grasoso",
                    "1 cebolla roja cortada en juliana",
                    "1 ají limo o en su defecto ají chili para los que viven fuera y es imposible encontrar el ají limo",
                    "10 limones en otros países les llaman limas, son verdes y pequeños",
                    "1 camote grande o 2 medianos hervidos batata/moniato",
                    "1 lechuga",
                    "1 atado de Culantro picado cilantro",
                    "1 choclo hervido maíz cocido",
                    "Sal y pimienta al gusto"
                ),
                listOf(
                    "Corta el pescado en cuadrados de 2 a 3 centímetros, esto no es una regla y depende del tipo de pescado, de su textura y de lo grasoso que sea. Es preferible como ya he comentado, elegir un pescado fresco de carne blanca y con cuerpo (fuerte).",
                    "En un bol de cristal o de metal frota el ají limón, esto es para que le de el aroma propio de este ají. Luego puedes agregar pequeños trozos de ají limo al gusto, pero con cuidado porque es muy picante.",
                    "Agrega el pescado cortado al bol y exprime los limones sobre el pescado directamente. El jugo (zumo) debe casi cubrir el pescado. Añade sal, pimienta al gusto, media cucharita de ajo molido, cebolla y un poco de culantro picado.",
                    "Agrega uno o dos cubos de hielo para enfriar la preparación por 3 minutos.",
                    "Deja reposar unos minutos, esto depende del gusto de cada persona. También hay pescados que absorben más rápido el limón y no necesitan demasiado tiempo. Repito, todo depende del gusto de cada persona.",
                    "Para servir, decoramos el plato con una hoja de lechuga, unos trozos de camote y servimos la preparación en el centro acompañado de choclo."
                ),
                listOf(
                    "-6.77137",
                    "-79.84088"
                ),
                Date(),
                Date()
            )
        )
        emit(recipes)
    }

    val recipe: Flow<Recipe> = flow {
        emit(
            Recipe(
                1,
                "Seco de Carne",
                "https://comidasperuanas.net/wp-content/uploads/2020/11/Seco-de-carne.webp",
                "Andrea Demarchi",
                listOf(
                    "1 kilo de carne cortada en trozos",
                    "2 cebollas rojas cortadas en cuadritos",
                    "1 cucharada de ajo molido",
                    "1 taza de aceite",
                    "1/2 taza de ají amarillo licuado",
                    "1/2 taza de ají mirasol licuado",
                    "4 papas cortadas en dos",
                    "2 zanahorias crudas cortadas en rodajas"
                ),
                listOf(
                    "En la olla agrega un chorro de aceite y coloca el kilo de carne cortada en trozos a dorar y retira.",
                    "Luego, en la olla ya desocupada se realiza un aderezo con las dos cebollas rojas cortadas en cuadritos. Deja cocinar por aproximadamente 5 minutos.",
                    "Ahora, agrega una cucharada de ajo molido y deja cocinar de nuevo por al menos un par de minutos.",
                    "Seguido, agrega media taza de ají amarillo licuado y media taza de ají mirasol licuado. deja cocinar por un aproximado de 5 minutos y mezcla todo muy bien a fuego medio.",
                    "Luego, añade un vaso de cerveza rubia o chicha de jora. Una vez listo, agrega un vaso de culantro bien licuado y deja hervir.",
                    "A continuación, agrega la sal, la pimienta y el comino al gusto. Baja el fuego e incorpora la carne. Cubre con agua y tapa, deja cocer a fuego bajo hasta dejar suave la carne."
                ),
                listOf(
                    "-6.77137",
                    "-79.84088"
                ),
                Date(),
                Date()
            )
        )
    }
}

suspend fun <T> Flow<List<T>>.flattenToList() =
    flatMapConcat { it.asFlow() }.toList()