package com.example.coba_manajemen.view.proyek

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coba_manajemen.R
import com.example.coba_manajemen.model.Proyek
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.viewmodel.proyek.HomeProyekViewModel
import com.example.coba_manajemen.viewmodel.proyek.HomeUiState
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar

object DestinasiProyekHome: DestinasiNavigasi {
    override val route = "home_Proyek"
    override val titleRes = "Home Manajemen Proyek"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeProyekScreen(
    navigateToItemEntry: () -> Unit,
    navigateToTim: () -> Unit,
    navigateToTugas: () -> Unit,
    navigateToAnggota: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeProyekViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiProyekHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getAllPryk()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Proyek"
                )
            }
        },
        bottomBar = {
            // ButtonBar sebagai Bottom Bar
            ButtonBar(
                navigateToItemEntry = navigateToItemEntry,
                navigateToTim = navigateToTim,
                navigateToTugas = navigateToTugas,
                navigateToAnggota = navigateToAnggota
            )
        }
    ) { innerPadding ->
        HomeProyekStatus(
            homeUiState = viewModel.prykUIState,
            retryAction = { viewModel.getAllPryk() },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePryk(it.idProyek)
                viewModel.getAllPryk()
            }
        )
    }
}

@Composable
fun ButtonBar(
    navigateToItemEntry: () -> Unit = {},
    navigateToTim: () -> Unit = {},
    navigateToTugas: () -> Unit = {},
    navigateToAnggota: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = navigateToItemEntry,
            modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
        ) {
            Text(text = "Tambah Proyek")
        }

        Button(
            onClick = navigateToTim,
            modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
        ) {
            Text(text = "Manajemen TIM")
        }

        Button(
            onClick = navigateToTugas,
            modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
        ) {
            Text(text = "Manajemen Tugas")
        }

        Button(
            onClick = navigateToAnggota,
            modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
        ) {
            Text(text = "Manajemen Anggota ")
        }
    }
}




@Composable
fun HomeProyekStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Proyek) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUiState.Success -> {
            if (homeUiState.proyek.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data Proyek")
                }
            } else {
                ProyekLayout(
                    proyek = homeUiState.proyek,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idProyek.toString()) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(id = R.drawable.loading),
        contentDescription = "Loading"
    )
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = "Error"
        )
        Text(
            text = "Failed to load projects",
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text("Retry")
        }
    }
}

@Composable
fun ProyekLayout(
    proyek: List<Proyek>,
    modifier: Modifier = Modifier,
    onDetailClick: (Proyek) -> Unit,
    onDeleteClick: (Proyek) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(proyek) { proyekItem ->
            ProyekCard(
                proyek = proyekItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(proyekItem) },
                onDeleteClick = { onDeleteClick(proyekItem) }
            )
        }
    }
}

@Composable
fun ProyekCard(
    proyek: Proyek,
    modifier: Modifier = Modifier,
    onDeleteClick: (Proyek) -> Unit = {}
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = proyek.namaProyek,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { deleteConfirmationRequired = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                Text(
                    text = proyek.idProyek.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = proyek.deskripsiProyek,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick(proyek)
            },
            onDeleteCancel = {
                deleteConfirmationRequired = false
            }, modifier = Modifier.padding(8.dp)
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
        text = { Text("Are you sure you want to delete this project?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Yes")
            }
        }
    )
}
