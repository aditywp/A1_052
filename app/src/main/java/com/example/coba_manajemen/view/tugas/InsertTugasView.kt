package com.example.coba_manajemen.view.tugas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coba_manajemen.R
import com.example.coba_manajemen.model.Proyek
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.tugas.InsertTugasUiEvent
import com.example.coba_manajemen.viewmodel.tugas.InsertTugasUiState
import com.example.coba_manajemen.viewmodel.tugas.InsertTugasViewModel
import com.example.coba_manajemen.widget.Dropdown
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.launch

object DestinasiAddTugasEntry: DestinasiNavigasi {
    override val route = "entry_Tugas"
    override val titleRes = "Entry Tugas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTugasScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiAddTugasEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryTugasBody(
            insertTugasUiState = viewModel.tgsuiState,
            onTugasValueChange = viewModel::updateInsertTimState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTugas()
                    navigateBack()
                }
            },
            timList = viewModel.timList,
            prykList = viewModel.prykList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryTugasBody(
    insertTugasUiState: InsertTugasUiState,
    onTugasValueChange: (InsertTugasUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    timList: List<Tim>,
    prykList: List<Proyek>,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertTugasUiEvent = insertTugasUiState.insertTugasUiEvent,
            onValueChange = onTugasValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Dropdown(
            selectedValue = timList.find { it.idTim == insertTugasUiState.insertTugasUiEvent.idTim }?.namaTim ?: "",
            options = timList.map { it.namaTim },
            label = "Pilih Tim",
            onValueChangedEvent = { newName ->
                val selectedTim = timList.firstOrNull { it.namaTim == newName }
                selectedTim?.let {
                    // Update idProyek based on selected project
                    onTugasValueChange(insertTugasUiState.insertTugasUiEvent.copy(idTim = it.idTim))
                }
            }
        )
        Dropdown(
            selectedValue = prykList.find { it.idProyek == insertTugasUiState.insertTugasUiEvent.idProyek }?.namaProyek ?: "",
            options = prykList.map { it.namaProyek },
            label = "Pilih Proyek",
            onValueChangedEvent = { newName ->
                val selectedProyek = prykList.firstOrNull { it.namaProyek == newName }
                selectedProyek?.let {
                    // Update idProyek based on selected project
                    onTugasValueChange(insertTugasUiState.insertTugasUiEvent.copy(idProyek = it.idProyek))
                }
            }
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green))
        ) {
            Text("Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertTugasUiEvent: InsertTugasUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTugasUiEvent) -> Unit = {},
    enabled: Boolean = true
){

    val statusTgsOption = listOf("Belum Mulai", "Sedang Berlangsung", "Selesai")
    val prioritasOption = listOf("Rendah", "Sedang", "Tinggi")

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertTugasUiEvent.namaTugas,
            onValueChange = {onValueChange(insertTugasUiEvent.copy(namaTugas = it))},
            label = { Text("Nama Tugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertTugasUiEvent.deskripsiTugas,
            onValueChange = {onValueChange(insertTugasUiEvent.copy(deskripsiTugas = it))},
            label = { Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Dropdown(
            selectedValue = insertTugasUiEvent.prioritas,
            options = prioritasOption,
            label = "Pilih Prioritas",
            onValueChangedEvent = { newPrioritas->
                onValueChange(insertTugasUiEvent.copy(prioritas = newPrioritas))
            }
        )
        Dropdown(
            selectedValue = insertTugasUiEvent.statusTugas,
            options = statusTgsOption,
            label = "Pilih Status Tugas",
            onValueChangedEvent = { newStatus->
                onValueChange(insertTugasUiEvent.copy(statusTugas = newStatus))
            }
        )
    }
}