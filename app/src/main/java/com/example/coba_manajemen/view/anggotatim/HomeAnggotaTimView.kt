package com.example.coba_manajemen.view.anggotatim

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coba_manajemen.R
import com.example.coba_manajemen.model.AnggotaTim
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.anggotatim.HomeAnggotaTimUiState
import com.example.coba_manajemen.viewmodel.anggotatim.HomeAnggotaTimViewModel
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar

object DestinasiAnggotaHome: DestinasiNavigasi {
    override val route = "home_Anggota_Tim"
    override val titleRes = "Home Manajemen Anggota Tim"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAnggotaScreen(
    navigateToAddAnggotaEntry: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onUpdateClick: (Int) -> Unit = {},
    viewModel: HomeAnggotaTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiAnggotaHome.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getallAnggotaTim()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddAnggotaEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(18.dp)
                    .width(150.dp)
                    .height(30.dp),
                containerColor = colorResource(id = R.color.green)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Proyek",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))  // Menambah jarak antara ikon dan teks
                    Text(
                        text = "TAMBAH PROYEK",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        HomeAnggotaStatus(
            homeAnggotaTimUiState = viewModel.agtUiState,
            retryAction = { viewModel.getallAnggotaTim() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onUpdateClick = onUpdateClick,
            onDeleteClick = { viewModel.deleteAnggotaTim(it.idTim) }
        )
    }
}

@Composable
fun HomeAnggotaStatus(
    homeAnggotaTimUiState: HomeAnggotaTimUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onDeleteClick: (AnggotaTim) -> Unit = {},
    onUpdateClick: (Int) -> Unit
) {
    when (homeAnggotaTimUiState) {
        is HomeAnggotaTimUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeAnggotaTimUiState.Success -> {
            if (homeAnggotaTimUiState.anggota.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data Anggota Tim", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                AnggotaLayout(
                    anggota = homeAnggotaTimUiState.anggota,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idAnggota) },
                    onDeleteClick = onDeleteClick,
                    onUpdateClick = {onUpdateClick(it.idAnggota)}
                )
            }
        }

        is HomeAnggotaTimUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(
    modifier: Modifier = Modifier
){
    Image(
        modifier =  modifier.size(200.dp),
        painter = painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id= R.drawable.ic_launcher_foreground),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun AnggotaLayout(
    anggota: List<AnggotaTim>,
    modifier: Modifier = Modifier,
    onDetailClick: (AnggotaTim) -> Unit,
    onDeleteClick: (AnggotaTim) -> Unit = {},
    onUpdateClick: (AnggotaTim) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(anggota) { agtItem ->
            AnggotaCard(
                anggota = agtItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(agtItem) },
                onDeleteClick = { onDeleteClick(agtItem) },
                onUpdateClick = { onUpdateClick(agtItem)},
                onDetailClick = { onDetailClick(agtItem) }
            )
        }
    }
}


@Composable
fun AnggotaCard(
    anggota: AnggotaTim,
    modifier: Modifier = Modifier,
    onDeleteClick: (AnggotaTim) -> Unit,
    onDetailClick: (AnggotaTim) -> Unit,
    onUpdateClick: (AnggotaTim) -> Unit
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth())
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Anggota Icon",
                        modifier = Modifier.size(24.dp),
                        tint = colorResource(id = R.color.green)
                        )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Anggota: ${anggota.namaAnggota}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
                Divider(
                    thickness = 2.dp,
                    modifier = Modifier.padding(12.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ID: ${anggota.idAnggota}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Peran: ${anggota.peran}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Button(
                        onClick = { onDetailClick(anggota) },
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green)),
                        modifier = Modifier
                            .height(40.dp)
                            .width(100.dp)
                    ){
                        Text(
                            text = "Detail"
                        )
                    }
                    Button(
                        onClick = { onUpdateClick(anggota) },
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green)),
                        modifier = Modifier
                            .height(40.dp)
                            .width(100.dp)
                    ){
                        Text(
                            text = "Update"
                        )
                    }
                    IconButton(
                        onClick = { deleteConfirmationRequired = true },
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Project",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick(anggota)
            },
            onDeleteCancel = {
                deleteConfirmationRequired = false
            },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        containerColor = MaterialTheme.colorScheme.surface,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}