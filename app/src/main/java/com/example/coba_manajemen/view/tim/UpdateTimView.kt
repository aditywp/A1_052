package com.example.coba_manajemen.view.tim

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
import com.example.coba_manajemen.viewmodel.tim.UpdateTimViewModel
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiTimUpdate: DestinasiNavigasi {
    override val route = "update_Tim"
    override val titleRes = "Update Tim"
    const val IDTIM = "idtim"
    val routesWithArg = "$route/{$IDTIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTimScreen(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiTimUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding->
        EntryTimBody(
            modifier = Modifier.padding(padding),
            insertTimUiState = viewModel.updatedTimUiState,
            onTimValueChange = viewModel::updateInsertTimState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTim()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}