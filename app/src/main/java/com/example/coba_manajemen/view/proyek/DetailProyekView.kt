package com.example.coba_manajemen.view.proyek

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
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
import com.example.coba_manajemen.model.Proyek
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.viewmodel.proyek.DetailProyekViewModel
import com.example.coba_manajemen.viewmodel.proyek.DetailUiState
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar

object DestinasiProyekDetail: DestinasiNavigasi {
    override val route = "detail_Proyek"
    override val titleRes = "Detail Pryk"
    const val IDPROYEK = "idProyek"
    val routesWithArg = "$route/{$IDPROYEK}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProyekScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    navigateToAddTugas: () -> Unit,
    navigateToSeeTugas: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailProyekViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold (
        topBar = {
            CostumeTopAppBar(
                title = DestinasiProyekDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getProyekbyId()
                }
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                contentAlignment = Alignment.BottomEnd // Atur posisi di sudut kanan bawah
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp), // Jarak antara tombol
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FloatingActionButton(
                        onClick = navigateToSeeTugas,
                        shape = MaterialTheme.shapes.medium,
                        containerColor = Color.Black
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Lihat Tugas",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))  // Menambah jarak antara ikon dan teks
                            Text(
                                text = "LIHAT TUGAS",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }
                    }
                    FloatingActionButton(
                        onClick = navigateToAddTugas,
                        shape = MaterialTheme.shapes.medium,
                        containerColor = Color.Black
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Lihat Tugas",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))  // Menambah jarak antara ikon dan teks
                            Text(
                                text = "TAMBAH TUGAS",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }
                    }
                    FloatingActionButton(
                        onClick = navigateToItemUpdate,
                        shape = MaterialTheme.shapes.medium,
                        containerColor = MaterialTheme.colorScheme.primary,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Lihat Tugas",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))  // Menambah jarak antara ikon dan teks
                            Text(
                                text = "EDIT PROYEK",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

    ) {
        innerPadding ->
        DetailProyekStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.proyekDetailState,
            retryAction = { viewModel.getProyekbyId() }
        )
    }
}

@Composable
fun DetailProyekStatus(
    detailUiState: DetailUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailUiState.Success -> {
            if (detailUiState.proyek.idProyek == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data Proyek")
                }
            } else {
                ItemDetailProyek(
                    proyek = detailUiState.proyek,
                    modifier = modifier.fillMaxSize()
                )
            }
        }
            is DetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailProyek(
    modifier: Modifier = Modifier,
    proyek: Proyek
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
            ComponentDetailPryk(judul = "ID Proyek", detail = proyek.idProyek.toString(),icon = Icons.Default.Edit)
            ComponentDetailPryk(judul = "Nama Proyek", detail = proyek.namaProyek, icon = Icons.Default.AccountBox)
            ComponentDetailPryk(judul = "Deskripsi Proyek", detail = proyek.deskripsiProyek, icon =  Icons.Default.Info)
            ComponentDetailPryk(judul = "Tanggal Mulai", detail = proyek.tanggalMulai, icon = Icons.Default.DateRange)
            ComponentDetailPryk(judul = "Tanggal Selesai", detail = proyek.tanggalBerakhir, icon = Icons.Default.DateRange)
            ComponentDetailPryk(judul = "Status Proyek", detail = proyek.statusProyek, icon = Icons.Default.CheckCircle)
        }
    }
}

@Composable
fun ComponentDetailPryk(
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
        ){
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Blue
            )
            Text(
                text = "$judul:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = detail,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Divider(
            thickness = 2.dp,
            modifier = Modifier.padding(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}