package com.example.coba_manajemen.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.coba_manajemen.view.anggotatim.DestinasiAddAnggotaEntry
import com.example.coba_manajemen.view.anggotatim.DestinasiAnggotaDetail
import com.example.coba_manajemen.view.anggotatim.DestinasiAnggotaHome
import com.example.coba_manajemen.view.anggotatim.DestinasiAnggotaUpdate
import com.example.coba_manajemen.view.anggotatim.DetailAnggotaScreen
import com.example.coba_manajemen.view.anggotatim.EntryAnggotaScreen
import com.example.coba_manajemen.view.anggotatim.HomeAnggotaScreen
import com.example.coba_manajemen.view.anggotatim.UpdateAnggotaScreen
import com.example.coba_manajemen.view.proyek.DestinasiProyekDetail
import com.example.coba_manajemen.view.proyek.DestinasiProyekEntry
import com.example.coba_manajemen.view.proyek.DestinasiProyekHome
import com.example.coba_manajemen.view.proyek.DestinasiProyekUpdate
import com.example.coba_manajemen.view.proyek.DetailProyekScreen
import com.example.coba_manajemen.view.proyek.EntryProyekScreen
import com.example.coba_manajemen.view.proyek.HomeProyekScreen
import com.example.coba_manajemen.view.proyek.UpdateProyekScreen
import com.example.coba_manajemen.view.tim.DestinasiAddTimEntry
import com.example.coba_manajemen.view.tim.DestinasiTimDetail
import com.example.coba_manajemen.view.tim.DestinasiTimHome
import com.example.coba_manajemen.view.tim.DestinasiTimUpdate
import com.example.coba_manajemen.view.tim.DetailTimScreen
import com.example.coba_manajemen.view.tim.EntryTimScreen
import com.example.coba_manajemen.view.tim.HomeTimScreen
import com.example.coba_manajemen.view.tim.UpdateTimScreen
import com.example.coba_manajemen.view.tugas.DestinasiAddTugasEntry
import com.example.coba_manajemen.view.tugas.DestinasiTugasDetail
import com.example.coba_manajemen.view.tugas.DestinasiTugasHome
import com.example.coba_manajemen.view.tugas.DestinasiTugasUpdate
import com.example.coba_manajemen.view.tugas.DetailTugasScreen
import com.example.coba_manajemen.view.tugas.EntryTugasScreen
import com.example.coba_manajemen.view.tugas.HomeTugasScreen
import com.example.coba_manajemen.view.tugas.UpdateTugasScreen

@Composable
fun PengelolaHalamanProyek(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiProyekHome.route,
        modifier = Modifier
    ) {
        composable(DestinasiProyekHome.route) {
            HomeProyekScreen(
                navigateToItemEntry = { navController.navigate(DestinasiProyekEntry.route) },
                navigateToTim = { navController.navigate(DestinasiTimHome.route) },
                navigateToTugas = { navController.navigate(DestinasiTugasHome.route) },
                navigateToAnggota = { navController.navigate(DestinasiAnggotaHome.route) },
                onDetailClick = { idProyek ->
                    navController.navigate("${DestinasiProyekDetail.route}/$idProyek")
                }
            )
        }
        composable(DestinasiProyekEntry.route) {
            EntryProyekScreen(
                navigateBack = {
                    navController.navigate(DestinasiProyekHome.route) {
                        popUpTo(DestinasiProyekHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiProyekDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiProyekDetail.IDPROYEK) {
                    type = NavType.StringType
                }
            )
        ) {
            val idProyek = it.arguments?.getString(DestinasiProyekDetail.IDPROYEK)
            idProyek?.let { idProyek ->
                DetailProyekScreen(
                    navigateToItemUpdate = {
                        navController.navigate("${DestinasiProyekUpdate.route}/$idProyek")
                    },
                    navigateToAddTugas = {
                        navController.navigate(DestinasiAddTugasEntry.route)
                    },
                    navigateToSeeTugas = {
                        navController.navigate(DestinasiTugasHome.route)
                    },
                    navigateBack = {
                        navController.navigate(DestinasiProyekHome.route) {
                            popUpTo(DestinasiProyekHome.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable(
            DestinasiProyekUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiProyekUpdate.IDPROYEK) {
                    type = NavType.IntType // Tipe argumen
                }
            )
        ) {
            val idProyek = it.arguments?.getInt(DestinasiProyekUpdate.IDPROYEK) // Ambil nilai
            idProyek?.let { id ->
                UpdateProyekScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        //HOME TIM
        composable(DestinasiTimHome.route) {
            HomeTimScreen(
                navigateToAddTimEntry = { navController.navigate(DestinasiAddTimEntry.route) },
                navigateBack = {
                    navController.navigate(DestinasiProyekHome.route) {
                        popUpTo(DestinasiTimHome.route) {
                            inclusive = true
                        }
                    }
                },
                onDetailTimClick = { idTim ->
                    navController.navigate("${DestinasiTimDetail.route}/$idTim")
                }
            )
        }
        composable(DestinasiAddTimEntry.route) {
            EntryTimScreen(
                navigateBack = {
                    navController.navigate(DestinasiTimHome.route) {
                        popUpTo(DestinasiTimHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiTimDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiTimDetail.IDTIM) {
                    type = NavType.StringType
                }
            )
        ) {
            val idTim = it.arguments?.getString(DestinasiTimDetail.IDTIM)
            idTim?.let { idTim ->
                DetailTimScreen(
                    navigateToTimUpdate = {
                        navController.navigate("${DestinasiTimUpdate.route}/$idTim")
                    },
                    navigateBack = {
                        navController.navigate(DestinasiTimHome.route) {
                            popUpTo(DestinasiTimHome.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable(
            DestinasiTimUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiTimUpdate.IDTIM) {
                    type = NavType.IntType // Tipe argumen
                }
            )
        ) {
            val idTim = it.arguments?.getInt(DestinasiTimUpdate.IDTIM) // Ambil nilai
            idTim?.let { id ->
                UpdateTimScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        //TUGAS
        composable(DestinasiTugasHome.route) {
            HomeTugasScreen(
                navigateToAddTugasEntry = { navController.navigate(DestinasiAddTugasEntry.route) },
                onDetailClick = { idTugas ->
                    navController.navigate("${DestinasiTugasDetail.route}/$idTugas") },
                navigateToBack = {
                    navController.navigate(DestinasiProyekHome.route) {
                        popUpTo(DestinasiProyekHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(DestinasiAddTugasEntry.route) {
            EntryTugasScreen(
                navigateBack = {
                    navController.navigate(DestinasiTugasHome.route) {
                        popUpTo(DestinasiTugasHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiTugasDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiTugasDetail.IDTUGAS) {
                    type = NavType.StringType
                }
            )
        ){
            val idTugas = it.arguments?.getString(DestinasiTugasDetail.IDTUGAS)
            idTugas?.let { idTugas ->
                DetailTugasScreen(
                    navigateToTugasUpdate = {
                        navController.navigate("${DestinasiTugasUpdate.route}/$idTugas")
                    },
                    navigateBack = {
                        navController.navigate(DestinasiTugasHome.route) {
                            popUpTo(DestinasiTugasHome.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable(
            DestinasiTugasUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiTugasUpdate.IDTUGAS) {
                    type = NavType.IntType // Tipe argumen
                }
            )
        ) {
            val idTugas = it.arguments?.getInt(DestinasiTugasUpdate.IDTUGAS) // Ambil nilai
            idTugas?.let { id ->
                UpdateTugasScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        //Anggota
        composable(DestinasiAnggotaHome.route) {
            HomeAnggotaScreen(
                navigateToAddAnggotaEntry = { navController.navigate(DestinasiAddAnggotaEntry.route) },
                onDetailClick = { idAnggota ->
                    navController.navigate("${DestinasiAnggotaDetail.route}/$idAnggota")
                },
                navigateBack = {
                    navController.navigate(DestinasiProyekHome.route) {
                        popUpTo(DestinasiProyekHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(DestinasiAddAnggotaEntry.route) {
            EntryAnggotaScreen(
                navigateBack = {
                    navController.navigate(DestinasiAnggotaHome.route) {
                        popUpTo(DestinasiAnggotaHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiAnggotaDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiAnggotaDetail.IDANGGOTA) {
                    type = NavType.StringType
                }
            )
        ){
            val idAnggota = it.arguments?.getString(DestinasiAnggotaDetail.IDANGGOTA)
            idAnggota?.let { idAnggota ->
                DetailAnggotaScreen(
                    navigateToAnggotaUpdate = {
                        navController.navigate("${DestinasiAnggotaUpdate.route}/$idAnggota")
                    },
                    navigateBack = {
                        navController.navigate(DestinasiAnggotaHome.route) {
                            popUpTo(DestinasiAnggotaHome.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable(
            DestinasiAnggotaUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiAnggotaUpdate.IDANGGOTA) {
                    type = NavType.IntType // Tipe argumen
                }
            )
        ) {
            val idAnggota = it.arguments?.getInt(DestinasiAnggotaUpdate.IDANGGOTA) // Ambil nilai
            idAnggota?.let { id ->
                UpdateAnggotaScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}