package com.example.coba_manajemen.view.anggotatim

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.anggotatim.InsertAnggotaTimUiEvent
import com.example.coba_manajemen.viewmodel.anggotatim.InsertAnggotaTimUiState
import com.example.coba_manajemen.viewmodel.anggotatim.InsertAnggotaTimViewModel
import com.example.coba_manajemen.widget.Dropdown
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.launch


object DestinasiAddAnggotaEntry: DestinasiNavigasi {
    override val route = "entry_Anggota"
    override val titleRes = "Entry Anggota Tim"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryAnggotaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAnggotaTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiAddAnggotaEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryAnggotaBody(
            insertAnggotaTimUiState = viewModel.agtuiState,
            onAnggotaTimValueChange = viewModel::updateInsertAnggotaTimState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAnggotaTim()
                    navigateBack()
                }
            },
            timList = viewModel.timList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryAnggotaBody(
    insertAnggotaTimUiState: InsertAnggotaTimUiState,
    onAnggotaTimValueChange: (InsertAnggotaTimUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    timList: List<Tim>,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertTimUiEvent = insertAnggotaTimUiState.insertAnggotaTimUiEvent,
            onValueChange = onAnggotaTimValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Dropdown(
            selectedValue = timList.find { it.idTim == insertAnggotaTimUiState.insertAnggotaTimUiEvent.idTim }?.namaTim ?: "",
            options = timList.map { it.namaTim },
            label = "Pilih Tim",
            onValueChangedEvent = { newName ->
                val selectedTim = timList.firstOrNull { it.namaTim == newName }
                selectedTim?.let {
                    // Update idProyek based on selected project
                    onAnggotaTimValueChange(insertAnggotaTimUiState.insertAnggotaTimUiEvent.copy(idTim = it.idTim))
                }
            }
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
    insertTimUiEvent: InsertAnggotaTimUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertAnggotaTimUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    val peranOption = listOf("Pemimpin", "Anggota")

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertTimUiEvent.namaAnggota,
            onValueChange = {onValueChange(insertTimUiEvent.copy(namaAnggota = it))},
            label = { Text("Nama Anggota") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Dropdown(
            selectedValue = insertTimUiEvent.peran,
            options = peranOption,
            label = "Pilih Peran",
            onValueChangedEvent = { newPeran ->
                onValueChange(insertTimUiEvent.copy(peran = newPeran))
            }

        )
    }
}