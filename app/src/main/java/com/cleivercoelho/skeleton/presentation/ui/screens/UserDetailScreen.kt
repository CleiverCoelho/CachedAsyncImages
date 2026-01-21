package com.cleivercoelho.skeleton.presentation.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cleivercoelho.skeleton.presentation.ui.components.UserAvatar
import com.cleivercoelho.skeleton.presentation.viewmodel.UserDetailAction
import com.cleivercoelho.skeleton.presentation.viewmodel.UserDetailEvent
import com.cleivercoelho.skeleton.presentation.viewmodel.UserDetailViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                UserDetailEvent.NavigateBack -> onNavigateBack()
                is UserDetailEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
                is UserDetailEvent.OpenEmail -> {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${event.email}"))
                    context.startActivity(intent)
                }
                is UserDetailEvent.OpenPhone -> {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${event.phone}"))
                    context.startActivity(intent)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.user?.name ?: "User Details") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onAction(UserDetailAction.BackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.onAction(UserDetailAction.DeleteClicked) }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.error != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(uiState.error!!, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.onAction(UserDetailAction.RetryClicked) }) {
                            Text("Retry")
                        }
                    }
                }
                uiState.user != null -> {
                    val user = uiState.user!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        UserAvatar(imageUrl = user.avatarUrl, size = 120.dp)

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(user.name, style = MaterialTheme.typography.headlineMedium)

                        Spacer(modifier = Modifier.height(32.dp))

                        // Email row
                        OutlinedCard(
                            onClick = { viewModel.onAction(UserDetailAction.EmailClicked) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Email, contentDescription = null)
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(user.email)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Phone row
                        OutlinedCard(
                            onClick = { viewModel.onAction(UserDetailAction.PhoneClicked) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Phone, contentDescription = null)
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(user.phone)
                            }
                        }
                    }
                }
            }
        }
    }
}