package com.example.exemplosimplesdecompose.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.exemplosimplesdecompose.R
import com.example.exemplosimplesdecompose.ui.theme.getAppBackground
import com.example.exemplosimplesdecompose.ui.theme.getAppOnPrimary
import com.example.exemplosimplesdecompose.ui.theme.getAppOnSurfaceVariant
import com.example.exemplosimplesdecompose.ui.theme.getAppOutline
import com.example.exemplosimplesdecompose.ui.theme.getAppPrimary

@Composable
fun InputView(navController: NavHostController) {
    var text by remember { mutableStateOf("") }
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = getAppPrimary(contrastLevel = currentContrastLevel),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(stringResource(id = R.string.digite_algo), color = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = getAppPrimary(contrastLevel = currentContrastLevel),
                    unfocusedBorderColor = getAppOutline(contrastLevel = currentContrastLevel),
                    focusedLabelColor = getAppPrimary(contrastLevel = currentContrastLevel),
                    unfocusedLabelColor = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel),
                    cursorColor = getAppPrimary(contrastLevel = currentContrastLevel)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("listaDePostos/$text") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = getAppPrimary(contrastLevel = currentContrastLevel),
                    contentColor = getAppOnPrimary(contrastLevel = currentContrastLevel)
                )
            ) {
                Text(stringResource(id = R.string.ir_para_proxima_tela))
            }
        }
    }
}
