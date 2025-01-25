package com.example.coba_manajemen.view.tugas

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.tugas.UpdateTugasViewModel
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiTugasUpdate: DestinasiNavigasi {
    override val route = "update_Tugas"
    override val titleRes = "Update Tugas"
    const val IDTUGAS = "idtugas"
    val routesWithArg = "$route/{$IDTUGAS}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTugasScreen(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiTugasUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding->
        EntryTugasBody(
            modifier = Modifier.padding(padding),
            insertTugasUiState = viewModel.updatedTugasUiState,
            onTugasValueChange = viewModel::updateInsertTugasState,
            timList = viewModel.timList,
            prykList = viewModel.prykList,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTugas()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}