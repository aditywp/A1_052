package com.example.coba_manajemen.view.proyek

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.viewmodel.proyek.UpdateProyekViewModel
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiProyekUpdate: DestinasiNavigasi {
    override val route = "update_Proyek"
    override val titleRes = "Update Pryk"
    const val IDPROYEK = "idproyek"
    val routesWithArg = "$route/{$IDPROYEK}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProyekScreen(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateProyekViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiProyekUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding->
        EntryBody(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.updatedUiState,
            onInsertValueChange = viewModel::updateInsertPrykState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePryk()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}