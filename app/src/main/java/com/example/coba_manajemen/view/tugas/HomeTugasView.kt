package com.example.coba_manajemen.view.tugas

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coba_manajemen.R
import com.example.coba_manajemen.model.Tugas
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.tugas.HomeTugasUiState
import com.example.coba_manajemen.viewmodel.tugas.HomeTugasViewModel
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar

object DestinasiTugasHome: DestinasiNavigasi {
    override val route = "home_Tugas"
    override val titleRes = "Home Manajemen Tugas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTugasScreen(
    navigateToAddTugasEntry: () -> Unit,
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeTugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiTugasHome.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateToBack,
                onRefresh = {
                    viewModel.getallTugas()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddTugasEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Proyek",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))  // Menambah jarak antara ikon dan teks
                    Text(
                        text = "TAMBAH TUGAS",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                }
            }
        }
    ){ innerPadding ->
        HomeTugasStatus(
            homeTugasUiState = viewModel.tgsUiState,
            retryAction = {viewModel.getallTugas()},
            modifier = Modifier.padding(innerPadding),
            onDetailClick= onDetailClick,
            onDeleteClick = {
                viewModel.deleteTugas(it.idTugas)
                viewModel.getallTugas()
            }
        )
    }
}

@Composable
fun HomeTugasStatus(
    homeTugasUiState: HomeTugasUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    onDeleteClick: (Tugas) -> Unit = {}
){
    when(homeTugasUiState) {
        is HomeTugasUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTugasUiState.Success ->
            if (homeTugasUiState.tugas.isEmpty()) {
                return Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data Tugas")
                }
            } else {
                TugasLayout(
                    tugas = homeTugasUiState.tugas,
                    modifier = modifier.fillMaxWidth(),
                    onDetailTugas = { onDetailClick(it.idTugas.toString())
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeTugasUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun TugasLayout(
    tugas: List<Tugas>,
    modifier: Modifier = Modifier,
    onDetailTugas: (Tugas) -> Unit,
    onDeleteClick: (Tugas) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tugas) { tugasItem ->
            TugasCard(
                tugas = tugasItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailTugas(tugasItem) },
                onDeleteClick = { onDeleteClick(tugasItem) }
            )
        }
    }
}


@Composable
fun TugasCard(
    tugas: Tugas,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tugas) -> Unit = {}
){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card (
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ){
        Column (
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tugas.namaTugas,
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
                    text = tugas.idTugas.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Text(
                text = tugas.prioritas,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = tugas.statusTugas,
                style = MaterialTheme.typography.titleMedium
            )
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDeleteClick(tugas)
                },
                onDeleteCancel =  {
                    deleteConfirmationRequired = false
                }, modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
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