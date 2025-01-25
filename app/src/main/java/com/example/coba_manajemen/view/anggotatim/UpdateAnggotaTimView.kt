package com.example.coba_manajemen.view.anggotatim

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
import com.example.coba_manajemen.viewmodel.anggotatim.UpdateAnggotaTimViewModel
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiAnggotaUpdate: DestinasiNavigasi {
    override val route = "update_AnggotaTim"
    override val titleRes = "Update Anggota Tim"
    const val IDANGGOTA = "idanggota"
    val routesWithArg = "$route/{$IDANGGOTA}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAnggotaScreen(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateAnggotaTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiAnggotaUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding->
        EntryAnggotaBody(
            modifier = Modifier.padding(padding),
            insertAnggotaTimUiState = viewModel.updatedAnggotaTimUiState,
            onAnggotaTimValueChange = viewModel::updateInsertAnggotaTimState,
            timList = viewModel.timList,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateAnggotaTim()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}