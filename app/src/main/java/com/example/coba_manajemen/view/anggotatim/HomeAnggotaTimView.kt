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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeAnggotaTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiAnggotaHome.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
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
                modifier = Modifier.padding(18.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Anggota",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "TAMBAH ANGGOTA",
                        style = MaterialTheme.typography.bodySmall,
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
            onDeleteClick = { viewModel.deleteAnggotaTim(it.idTim) }
        )
    }
}

@Composable
fun HomeAnggotaStatus(
    homeAnggotaTimUiState: HomeAnggotaTimUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    onDeleteClick: (AnggotaTim) -> Unit = {}
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
                    onDetail = { onDetailClick(it.idAnggota.toString()) },
                    onDeleteClick = onDeleteClick
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
    onDetail: (AnggotaTim) -> Unit,
    onDeleteClick: (AnggotaTim) -> Unit = {}
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
                    .clickable { onDetail(agtItem) },
                onDeleteClick = { onDeleteClick(agtItem) }
            )
        }
    }
}


@Composable
fun AnggotaCard(
    anggota: AnggotaTim,
    modifier: Modifier = Modifier,
    onDeleteClick: (AnggotaTim) -> Unit = {}
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
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = { deleteConfirmationRequired = true },
                        modifier = Modifier.align(Alignment.End)
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