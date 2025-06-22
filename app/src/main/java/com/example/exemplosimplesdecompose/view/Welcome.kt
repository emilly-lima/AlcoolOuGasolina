package com.example.exemplosimplesdecompose.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.exemplosimplesdecompose.R
import com.example.exemplosimplesdecompose.ui.theme.getAppBackground
import com.example.exemplosimplesdecompose.ui.theme.getAppOnSurface
import com.example.exemplosimplesdecompose.ui.theme.getAppPrimary

@Composable
fun Welcome(navController: NavHostController) {
    val context = LocalContext.current
    val currentContrastLevel = loadContrastConfig(context)
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = getAppBackground(contrastLevel = currentContrastLevel)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.bem_vindo_ao_app),
                style = MaterialTheme.typography.headlineMedium,
                color = getAppPrimary(contrastLevel = currentContrastLevel),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = R.drawable.petrol),
                contentDescription = stringResource(id = R.string.descricao_imagem_boas_vindas),
                modifier = Modifier
                    .size(128.dp)
                    .clickable { navController.navigate("mainalcgas") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.bodyLarge,
                color = getAppOnSurface(contrastLevel = currentContrastLevel),
                textAlign = TextAlign.Center
            )
        }
    }
}
