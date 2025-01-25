package com.example.coba_manajemen.view.tugas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coba_manajemen.model.Proyek
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.model.Tugas
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.tugas.DetailTugasUiState
import com.example.coba_manajemen.viewmodel.tugas.DetailTugasViewModel
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar

object DestinasiTugasDetail: DestinasiNavigasi {
    override val route = "detail_Tugas"
    override val titleRes = "Detail Tugas"
    const val IDTUGAS = "idTugas"
    val routesWithArg = "$route/{$IDTUGAS}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTugasScreen(
    navigateBack: () -> Unit,
    navigateToTugasUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailTugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold (
        topBar = {
            CostumeTopAppBar(
                title = DestinasiTugasDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTugasbyId()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTugasUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Tugas"
                )
            }
        }
    ) {
            innerPadding ->
        DetailTugasStatus(
            modifier = Modifier.padding(innerPadding),
            detailTugasUiState = viewModel.tgsDetailState,
            retryAction = { viewModel.getTugasbyId() }
        )
    }
}

@Composable
fun DetailTugasStatus(
    detailTugasUiState: DetailTugasUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailTugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    when (detailTugasUiState) {
        is DetailTugasUiState.Loading ->OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is DetailTugasUiState.Success -> {
            if (detailTugasUiState.tugas.idTugas == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data Tugas")
                }
            } else {
                ItemDetailTugas(
                    tugas = detailTugasUiState.tugas,
                    timList = viewModel.timList,
                    prykList =viewModel.prykList,
                    modifier = modifier.fillMaxSize()
                )
            }
        }
        is DetailTugasUiState.Error ->OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailTugas(
    modifier: Modifier = Modifier,
    tugas: Tugas,
    timList: List<Tim>,
    prykList: List<Proyek>
) {
    val tim = timList.find { it.idTim == tugas.idTim }?.namaTim ?:""
    val pryk = prykList.find { it.idProyek == tugas.idProyek }?.namaProyek?:""

    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailTugas(judul = "ID Tugas", detail = tugas.idTugas.toString())
            ComponentDetailTugas(judul = "ID Proyek", detail = tugas.idProyek.toString())
            ComponentDetailTugas(judul = "ID Tim", detail = tugas.idTim.toString())
            ComponentDetailTugas(judul = "Nama Proyek", detail = pryk)
            ComponentDetailTugas(judul = "Nama Tim", detail = tim)
            ComponentDetailTugas(judul = "Nama Tugas", detail = tugas.namaTugas)
            ComponentDetailTugas(judul = "Deskripsi Tugas", detail = tugas.deskripsiTugas)
            ComponentDetailTugas(judul = "Prioritas", detail = tugas.prioritas)
            ComponentDetailTugas(judul = "Status Tugas", detail = tugas.statusTugas)
        }
    }
}

@Composable
fun ComponentDetailTugas(
    modifier: Modifier = Modifier,
    judul: String,
    detail: String
){
    Column(modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start)
    {
        Text(
            text = "$judul:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Magenta
        )
        Text(
            text = detail,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}