package com.example.coba_manajemen.view.tim

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.tim.DetailTimUiState
import com.example.coba_manajemen.viewmodel.tim.DetailTimViewModel
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar


object DestinasiTimDetail: DestinasiNavigasi {
    override val route = "detail_Tim"
    override val titleRes = "Detail Tim"
    const val IDTIM = "idTim"
    val routesWithArg = "$route/{$IDTIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTimScreen(
    navigateBack: () -> Unit,
    navigateToTimUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold (
        topBar = {
            CostumeTopAppBar(
                title = DestinasiTimDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTimbyId()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTimUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Tim"
                )
            }
        }
    ) {
            innerPadding ->
        DetailTimStatus(
            modifier = Modifier.padding(innerPadding),
            detailTimUiState = viewModel.timDetailState,
            retryAction = { viewModel.getTimbyId() }
        )
    }
}

@Composable
fun DetailTimStatus(
    detailTimUiState: DetailTimUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailTimUiState) {
        is DetailTimUiState.Loading ->OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is DetailTimUiState.Success -> {
            if (detailTimUiState.tim.idTim == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data Tim")
                }
            } else {
                ItemDetailTim(
                    tim = detailTimUiState.tim,
                    modifier = modifier.fillMaxSize()
                )
            }
        }
        is DetailTimUiState.Error ->OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailTim(
    modifier: Modifier = Modifier,
    tim: Tim
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailTim(judul = "ID tim", detail = tim.idTim.toString(), icon = Icons.Default.Edit)
            ComponentDetailTim(judul = "Nama tim", detail = tim.namaTim, icon = Icons.Default.AccountBox)
            ComponentDetailTim(judul = "Deskripsi tim", detail = tim.deskripsiTim, icon = Icons.Default.Info)
        }
    }
}

@Composable
fun ComponentDetailTim(
    modifier: Modifier = Modifier,
    judul: String,
    detail: String,
    icon: ImageVector
){
    Column(modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start)
    {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Red
            )
            Text(
                text = "$judul:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = detail,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Divider(
            thickness = 2.dp,
            modifier = Modifier.padding(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}