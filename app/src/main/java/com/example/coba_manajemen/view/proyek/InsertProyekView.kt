package com.example.coba_manajemen.view.proyek

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.viewmodel.proyek.InsertProyekViewModel
import com.example.coba_manajemen.viewmodel.proyek.InsertUiEvent
import com.example.coba_manajemen.viewmodel.proyek.InsertUiState
import com.example.coba_manajemen.widget.Dropdown
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.launch

object DestinasiProyekEntry: DestinasiNavigasi {
    override val route = "entry_Proyek"
    override val titleRes = "Entry Proyek"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryProyekScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertProyekViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiProyekEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onInsertValueChange = viewModel::updateInsertPrykState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPryk()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onInsertValueChange:(InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(16.dp)

    ){
        FormInput (
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onInsertValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val statusOption = listOf("Aktif", "Dalam Progres", "Selesai")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ){
        OutlinedTextField(
            value = insertUiEvent.namaProyek,
            onValueChange = { onValueChange(insertUiEvent.copy(namaProyek = it)) },
            label = { Text("Nama Proyek") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsiProyek,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiProyek = it)) },
            label = { Text("Deskripsi Proyek") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.tanggalMulai,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggalMulai = it)) },
            label = { Text("Tanggal Mulai") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.tanggalBerakhir,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggalBerakhir = it)) },
            label = { Text("Tanggal Berakhir") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Dropdown(
            selectedValue = insertUiEvent.statusProyek,
            options = statusOption,
            label = "Status Proyek",
            onValueChangedEvent = { newStatus->
                onValueChange(insertUiEvent.copy(statusProyek = newStatus))
            }

        )
        if (enabled) {
            Text(
                text = "isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}