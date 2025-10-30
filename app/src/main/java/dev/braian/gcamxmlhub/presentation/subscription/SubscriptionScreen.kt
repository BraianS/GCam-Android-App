package dev.braian.gcamxmlhub.presentation.subscription

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.braian.gcamxmlhub.data.model.SubscriptionPlan
import dev.braian.gcamxmlhub.ui.theme.GcamXMLHubTheme
import java.text.NumberFormat
import java.util.Locale




@SuppressLint("RememberReturnType")
@Composable
fun SubscriptionScreen() {

    val plans = remember {
        listOf(
            SubscriptionPlan(1, 10.99, "Plano de 1 mês. Ideal para experimentar."),
            SubscriptionPlan(3, 29.99, "Plano trimestral. Economize 10%."),
            SubscriptionPlan(6, 54.99, "Plano semestral. A melhor oferta com 20% de desconto.")
        )
    }

    var selectedPlanIndex by remember { mutableStateOf<Int?>(null) }
    val selectedPlan by remember {
        derivedStateOf { selectedPlanIndex?.let { plans[it] } }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título e sub-título
        Text(
            text = "Escolha seu Plano",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Acesso ilimitado a todos os recursos.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        // Linha com os três cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            plans.forEachIndexed { index, plan ->
                SubscriptionCard(
                    plan = plan,
                    isSelected = selectedPlanIndex == index,
                    onClick = { selectedPlanIndex = index }
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // Botão para escolher o plano
        Button (
            onClick = {
                // Lógica para processar o plano selecionado
                selectedPlan?.let {
                    // Aqui você iniciaria o processo de pagamento, por exemplo
                    println("Plano selecionado: ${it.months} meses por ${it.price}")
                }
            },
            enabled = selectedPlan != null,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Escolher Plano", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))

        // Texto informativo do plano selecionado
        selectedPlan?.let {
            Text(
                text = it.info,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }



    }
}





    @Composable
     fun SubscriptionCard(plan: SubscriptionPlan, isSelected: Boolean, onClick: () -> Unit) {
        val borderColor =
            if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
        val cardColor =
            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        val textColor =
            if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface

        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        val formattedPrice = currencyFormatter.format(plan.price)


        Card(
            modifier = Modifier
                .size(100.dp, 120.dp),
            colors = CardDefaults.cardColors(
                containerColor = cardColor
            ),
            border = BorderStroke(
                width = 3.dp,
                color = borderColor
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .clickable(onClick = onClick),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${plan.months}",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                )
                Text(
                    text = if (plan.months > 1) "meses" else "mês",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = textColor
                    )
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = formattedPrice,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                )
            }
        }
    }


@Preview
@Composable
fun SubscriptionCardPreview(){
    GcamXMLHubTheme {
        SubscriptionCard(
            plan = SubscriptionPlan(1, 10.99, "Plano de 1 mês. Ideal para experimentar."),
            isSelected = false,
            onClick = {}
        )
    }
}


@Preview
@Composable
fun SettingsScreenPreviewLight() {
    GcamXMLHubTheme(darkTheme = false) {
        SubscriptionScreen()
    }
}

@Preview
@Composable
fun SettingsScreenPreviewDark() {
    GcamXMLHubTheme(darkTheme = true) {
        SubscriptionScreen()
    }
}
