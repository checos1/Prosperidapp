package com.example.apphogares.frontEnd

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.frontEnd.theme.AppHogaresTheme

import javax.inject.Inject

sealed class RoutesNav (val route:String = ""){
    object indexScreen: RoutesNav("indexScreen")
    object loginScreen: RoutesNav("loginScreen")
    object questionScreen: RoutesNav("questionScreen")
    object wellcomeScreen: RoutesNav("wellcomeScreen")
    object permissionsDeviceScreen: RoutesNav("permissionsDeviceScreen")
    object infoHogarScreen: RoutesNav("infoHogarScreen")
    object ofertaHogarScreen: RoutesNav("ofertaHogarScreen")
    object emparejarScreen: RoutesNav("emparejarPalabraScreen")
    object completarScreen: RoutesNav("completarPalabraScreen")
    object memoriaScreen: RoutesNav("memoriaPalabrasScreen")
    object sopaScreen: RoutesNav("sopaPalabraScreen")

    object rutaScreen: RoutesNav("rutaScreen")
    object historietaScreen: RoutesNav("historietaScreen")
    object alertaScreen: RoutesNav("alertaScreen")
    object encuestaScreen: RoutesNav("EncuestaScreen")

    object encuestaCategoriaScreen: RoutesNav("EncuestaScreen")

    object gestionaScreen: RoutesNav("gestionaScreen")

    object PQRScreen: RoutesNav("PQRScreen")
    object CrearPQRScreen: RoutesNav("CrearPQRScreen")

    object InformacionPQRScreen: RoutesNav("InformacionPQRScreen")

    object CamaraScreen: RoutesNav("CamaraScreen")

    object PeriodicoVidaScreen: RoutesNav("PeriodicoVidaScreen")

    object ComunicacionesScreen: RoutesNav("ComunicacionesScreen")

    object categoriaScreen: RoutesNav("categoriaScreen")

    object InformacionRutaScreen: RoutesNav("InformacionRutaScreen")

}

class AppNavigation @Inject constructor(
    val mainViewModel: MainViewModel,
    val screenWidthDp: Int,
    val screenHeightDp: Int
) {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun AppNavigationHost() {
        mainViewModel.screenWidthDp.value = screenWidthDp
        mainViewModel.screenHeightDp.value = screenHeightDp
        val nav = rememberNavController()
        mainViewModel.funNav = { uri: String -> nav.navigate(uri.toString()) }
        AppHogaresAplication.funNav = mainViewModel.funNav

        AppHogaresTheme {
            NavHost(nav, startDestination = RoutesNav.indexScreen.route ) {
                composable(RoutesNav.loginScreen.route){ mainViewModel.loginScreen()}
                composable(RoutesNav.indexScreen.route){ backStackEntry -> mainViewModel.indexScreen() }
                composable(RoutesNav.rutaScreen.route){ mainViewModel.rutaScreen() }
                composable("${RoutesNav.historietaScreen.route}/{code}") { backStackEntry ->
                    backStackEntry.arguments?.getString("code")?.let { mainViewModel.historietaScreen(it, nav.context) }
                }
                composable(RoutesNav.questionScreen.route){mainViewModel.questionScreen()}
                composable(RoutesNav.wellcomeScreen.route){mainViewModel.wellcomeScreen()}
                composable(RoutesNav.permissionsDeviceScreen.route){mainViewModel.permissionsDeviceScreen()}
                composable(RoutesNav.infoHogarScreen.route){mainViewModel.infoHogarScreen()}
                composable(RoutesNav.ofertaHogarScreen.route){mainViewModel.ofertaHogarScreen()}

                composable("${RoutesNav.emparejarScreen.route}/{catCode}/{rutaCode}/{tematicaCode}") { backStackEntry ->
                    backStackEntry.arguments?.getString("catCode")?.let { catCode ->
                        backStackEntry.arguments?.getString("rutaCode")?.let { rutaCode ->
                            backStackEntry.arguments?.getString("tematicaCode")?.let { tematicaCode ->
                                mainViewModel.emparejarPalabraScreen(catCode, rutaCode, tematicaCode)
                            }
                        }
                    }
                }

                composable("${RoutesNav.completarScreen.route}/{catCode}/{rutaCode}/{tematicaCode}") { backStackEntry ->
                    backStackEntry.arguments?.getString("catCode")?.let { catCode ->
                        backStackEntry.arguments?.getString("rutaCode")?.let { rutaCode ->
                            backStackEntry.arguments?.getString("tematicaCode")?.let { tematicaCode ->
                                mainViewModel.completarPalabraScreen(catCode, rutaCode, tematicaCode)
                            }
                        }
                    }
                }

                composable("${RoutesNav.memoriaScreen.route}/{catCode}/{rutaCode}/{tematicaCode}") { backStackEntry ->
                    backStackEntry.arguments?.getString("catCode")?.let { catCode ->
                        backStackEntry.arguments?.getString("rutaCode")?.let { rutaCode ->
                            backStackEntry.arguments?.getString("tematicaCode")?.let { tematicaCode ->
                                mainViewModel.memoriaPalabraScreen(catCode, rutaCode, tematicaCode)
                            }
                        }
                    }
                }
                composable("${RoutesNav.sopaScreen.route}/{catCode}/{rutaCode}/{tematicaCode}") { backStackEntry ->
                    backStackEntry.arguments?.getString("catCode")?.let { catCode ->
                        backStackEntry.arguments?.getString("rutaCode")?.let { rutaCode ->
                            backStackEntry.arguments?.getString("tematicaCode")?.let { tematicaCode ->
                                mainViewModel.sopaPalabraScreen(catCode, rutaCode, tematicaCode)
                            }
                        }
                    }
                }

                composable(RoutesNav.alertaScreen.route){mainViewModel.alertaScreen()}
                composable(RoutesNav.encuestaScreen.route){mainViewModel.encuestaScreen()}
                composable(RoutesNav.encuestaCategoriaScreen.route){mainViewModel.encuestaCategoriaScreen()}
                composable(RoutesNav.gestionaScreen.route){mainViewModel.gestionaScreen()}
                composable(RoutesNav.PQRScreen.route){mainViewModel.PQRsScreen()}

                composable(RoutesNav.CrearPQRScreen.route){mainViewModel.crearPQRScreen()}

                composable(RoutesNav.InformacionPQRScreen.route){mainViewModel.informacionPQRScreen()}

                composable(RoutesNav.PeriodicoVidaScreen.route){mainViewModel.periodicoVidaScreen()}

                composable(RoutesNav.ComunicacionesScreen.route){mainViewModel.comunicacionesScreen()}

                composable(RoutesNav.categoriaScreen.route){mainViewModel.categoriaScreen()}

                composable(RoutesNav.InformacionRutaScreen.route){mainViewModel.informacionRutaScreen()}

/*              composable(RoutesNav.CamaraScreen.route){mainViewModel.camaraScreen()}*/


            }
        }
    }
}
