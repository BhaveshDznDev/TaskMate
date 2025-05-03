package com.example.taskmanager

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmanager.data.Task
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskView(
    id: Long,
    viewModel: TaskViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    if (id != 0L) {
        val task = viewModel.getTask(id).collectAsState(Task(0L, "", "", "", ""))
        viewModel.taskTitleState = task.value.taskTitle
        viewModel.taskDescriptionState = task.value.taskDescription ?: ""
        viewModel.taskDateState = task.value.taskDate
        viewModel.taskTimeState = task.value.taskTime ?: ""
    } else {
        viewModel.taskTitleState = ""
        viewModel.taskDescriptionState = ""
        viewModel.taskDateState = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        viewModel.taskTimeState = ""
    }

    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"))
    val currentDatetime = "$currentDate : $currentTime"

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (id == 0L) stringResource(R.string.add_task)
                        else stringResource(R.string.update_task)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.taskTitleState,
                onValueChange = { viewModel.taskTitleState = it },
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = viewModel.taskDescriptionState,
                onValueChange = { viewModel.taskDescriptionState = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = viewModel.taskDateState,
                onValueChange = { },
                label = { Text("Deadline Date") },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { dateDialogState.show() }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date Picker",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = viewModel.taskTimeState,
                onValueChange = { },
                label = { Text("Deadline Time") },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { timeDialogState.show() }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Time Picker",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Button(
                onClick = {
                    if (viewModel.taskTitleState.isNotEmpty() && viewModel.taskDateState.isNotEmpty()) {
                        if (id != 0L) {
                            viewModel.update(
                                Task(
                                    id = id,
                                    taskTitle = viewModel.taskTitleState.trim(),
                                    taskDescription = viewModel.taskDescriptionState.trim(),
                                    taskDate = viewModel.taskDateState.trim(),
                                    taskTime = viewModel.taskTimeState.trim(),
                                    lastModifiedDateTime = currentDatetime
                                )
                            )
                        } else {
                            viewModel.addTask(
                                Task(
                                    taskTitle = viewModel.taskTitleState.trim(),
                                    taskDescription = viewModel.taskDescriptionState.trim(),
                                    taskDate = viewModel.taskDateState.trim(),
                                    taskTime = viewModel.taskTimeState.trim(),
                                    lastModifiedDateTime = currentDatetime
                                )
                            )
                        }
                        scope.launch { navController.navigateUp() }
                    } else {
                        Toast.makeText(context, "Enter title & date to create a task", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = if (id != 0L) stringResource(R.string.update_task) else stringResource(R.string.add_task),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "Set")
                negativeButton(text = "Cancel")
            }
        ) {
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick a date",
                colors = DatePickerDefaults.colors()
            ) {
                viewModel.taskDateState = it.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            }
        }

        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(text = "Set")
                negativeButton(text = "Cancel")
            }
        ) {
            timepicker(
                initialTime = LocalTime.now(),
                title = "Set time",
                colors = TimePickerDefaults.colors()
            ) {
                viewModel.taskTimeState = it.format(DateTimeFormatter.ofPattern("hh:mm a"))
            }
        }
    }
}
