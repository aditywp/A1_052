package com.example.coba_manajemen.view.tim

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
import androidx.compose.material.icons.filled.AccountBox
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coba_manajemen.R
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.tim.HomeTimUiState
import com.example.coba_manajemen.viewmodel.tim.HomeTimViewModel
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar

object DestinasiTimHome: DestinasiNavigasi {
    override val route = "home_Tim"
    override val titleRes = "Home Manajemen Tim"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTimScreen(
    navigateToAddTimEntry: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailTimClick: (String) -> Unit = {},
    viewModel: HomeTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiTimHome.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getallTim()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddTimEntry,
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
                        text = "TAMBAH TIM",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                }
            }
        }
    ){ innerPadding ->
        HomeTimStatus(
            homeTimUiState = viewModel.tmUiState,
            retryAction = {viewModel.getallTim()},
            modifier = Modifier.padding(innerPadding),
            onDetailClickTim = onDetailTimClick,
            onDeleteClick = {
                viewModel.deleteTim(it.idTim)
                viewModel.getallTim()
            }
        )
    }
}

@Composable
fun HomeTimStatus(
    homeTimUiState: HomeTimUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClickTim: (String) -> Unit = {},
    onDeleteClick: (Tim) -> Unit = {}
){
    when(homeTimUiState) {
        is HomeTimUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTimUiState.Success ->
            if (homeTimUiState.tim.isEmpty()) {
                return Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data TIM")
                }
            } else {
                TimLayout(
                    tim = homeTimUiState.tim,
                    modifier = modifier.fillMaxWidth(),
                    onDetailTIM = { onDetailClickTim(it.idTim.toString())
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeTimUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
            painter = painterResource(id=R.drawable.ic_launcher_foreground),
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
fun TimLayout(
    tim: List<Tim>,
    modifier: Modifier = Modifier,
    onDetailTIM: (Tim) -> Unit,
    onDeleteClick: (Tim) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tim) { timItem ->
            TimCard(
                tim = timItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailTIM(timItem) },
                onDeleteClick = { onDeleteClick(timItem) }
            )
        }
    }
}


@Composable
fun TimCard(
    tim: Tim,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tim) -> Unit = {}
){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Column(modifier = Modifier.fillMaxWidth())
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Tim Icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Red

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tim: ${tim.namaTim}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ID: ${tim.idTim}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Deskripsi: ${tim.deskripsiTim}",
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
                onDeleteClick(tim)
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
){
    AlertDialog(onDismissRequest = {},
        title = { Text("Delete Data")},
        text = { Text("Apakah anda yakin ingin menghapus data?")},
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