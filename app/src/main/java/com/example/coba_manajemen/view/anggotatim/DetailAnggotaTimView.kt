package com.example.coba_manajemen.view.anggotatim

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
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coba_manajemen.R
import com.example.coba_manajemen.model.AnggotaTim
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.anggotatim.DetailAnggotaTimUiState
import com.example.coba_manajemen.viewmodel.anggotatim.DetailAnggotaTimViewModel
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar

object DestinasiAnggotaDetail: DestinasiNavigasi {
    override val route = "detail_Anggota"
    override val titleRes = "Detail Anggota Tim"
    const val IDANGGOTA = "idAnggota"
    val routesWithArg = "$route/{$IDANGGOTA}"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAnggotaScreen(
    navigateBack: () -> Unit,
    navigateToAnggotaUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailAnggotaTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiAnggotaDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getAnggotaTimbyId() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAnggotaUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = MaterialTheme.colorScheme.primary // Add color to the FAB
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Anggota",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        DetailAnggotaStatus(
            modifier = Modifier.padding(innerPadding),
            detailAnggotaUiState = viewModel.agtDetailState,
            retryAction = { viewModel.getAnggotaTimbyId() }
        )
    }
}

@Composable
fun DetailAnggotaStatus(
    detailAnggotaUiState: DetailAnggotaTimUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailAnggotaTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    when (detailAnggotaUiState) {
        is DetailAnggotaTimUiState.Loading -> {
            OnLoading(
                modifier = modifier.fillMaxSize()
            )
        }

        is DetailAnggotaTimUiState.Success -> {
            if (detailAnggotaUiState.anggota.idAnggota == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data Tim", fontSize = 18.sp, color = Color.Gray)
                }
            } else {
                ItemDetailAnggota(
                    anggota = detailAnggotaUiState.anggota,
                    modifier = modifier.fillMaxSize(),
                    timList = viewModel.timList
                )
            }
        }

        is DetailAnggotaTimUiState.Error -> {
            OnError(
                retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ItemDetailAnggota(
    modifier: Modifier = Modifier,
    anggota: AnggotaTim,
    timList: List<Tim>,
) {
    val tim = timList.find { it.idTim == anggota.idTim }?.namaTim ?: ""
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.large, // Rounded corners
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailAnggota(judul = "ID Anggota", detail = anggota.idAnggota.toString(), icon = Icons.Default.Edit)
            ComponentDetailAnggota(judul = "Nama Tim", detail = tim, icon = Icons.Default.AccountBox)
            ComponentDetailAnggota(judul = "Nama Anggota", detail = anggota.namaAnggota, icon = Icons.Default.Person)
            ComponentDetailAnggota(judul = "Peran", detail = anggota.peran, icon = Icons.Default.Star)
        }
    }
}

@Composable
fun ComponentDetailAnggota(
    modifier: Modifier = Modifier,
    judul: String,
    detail: String,
    icon: ImageVector
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colorResource(id = R.color.green)
            )
            Text(
                text = "$judul:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.green),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = detail,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}
