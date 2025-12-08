package com.purple.aicalendar.ui.view.bottomSheet

import android.app.Dialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.purple.aicalendar.R
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.ui.components.AppButton
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.AppTextField
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.LightGray
import com.purple.aicalendar.ui.theme.White
import com.purple.aicalendar.ui.viewmodel.SharedViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun EditTask(
    modifier: Modifier=Modifier,
    onDismiss:()->Unit,
    event: Event,
    vm: SharedViewModel = hiltViewModel()
){
    var amount by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var showDateDialog by remember { mutableStateOf(false) }
    val initialDate = event.date
    val context = LocalContext.current

    Column(
        modifier
            .fillMaxWidth()
            .height(if(event.transactionType=="Bill")Dimens.dp(550)else Dimens.dp(600))
            .padding(all = Dimens.dp24)
            .background(White),
    ) {
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "",
                modifier = Modifier
                    .size(Dimens.dp16) // set a fixed size
                    .clickable(onClick = onDismiss)
            )
        }
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AppText(
                title = "Edit Task Details",
                fontSize = Dimens.sp(16F),
                fontWeight = FontWeight.Bold
            )
        }
        Gap.H(Dimens.dp20)
        AppTextField.Text(
            value = amount,
            onValueChange = { amount = it },
            title = "Amount (₦)",
            description = event.amount,
            height = Dimens.dp(40),
            modifier = Modifier.fillMaxWidth()
        )
        Gap.H(Dimens.dp10)
        AppTextField.Text(
            value = title,
            onValueChange = { title = it },
            title = "Title",
            description = event.title,
            height = Dimens.dp(40),
            modifier = Modifier.fillMaxWidth()
        )
        Gap.H(Dimens.dp10)
        // 🌟 DATE FIELD (clickable)
        Column {
            AppText(
                title = "Date",
                fontWeight = FontWeight.SemiBold,
                fontSize = Dimens.sp(12F),
                color = Color.Black
            )
            Gap.H(Dimens.dp4)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp(40))
                    .border(
                        width = Dimens.dp(1),
                        color = LightGray,
                        shape = RoundedCornerShape(Dimens.dp4)
                    )
                    .background(White, RoundedCornerShape(Dimens.dp8))
                    .padding(
                        start = Dimens.dp12,
                        top = Dimens.dp6,
                        end = Dimens.dp12,
                        bottom = Dimens.dp6
                    )
                    .clickable { showDateDialog = true },
                contentAlignment = Alignment.CenterStart
            ) {
                AppText(
                    title = selectedDate.ifEmpty { initialDate },
                    color = if (selectedDate.isEmpty()) Color.Gray else Color.Black,
                    fontSize = Dimens.sp(12F),
                    fontWeight = FontWeight.Medium,
                )
            }
        }
        // 🌟 SHOW DATE PICKER DIALOG
        if (showDateDialog) {
            val dialog = Dialog(context)
            val calendar = Calendar.getInstance()

            // Parse initialDate safely
            runCatching {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(initialDate)?.let {
                    calendar.time = it
                }
            }

            dialog.setContentView(R.layout.dialog_date_picker)
            val datePicker: DatePicker = dialog.findViewById(R.id.datePicker)
            datePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ) { _, year, month, dayOfMonth ->
                val formatted = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth)
                selectedDate = formatted
                dialog.dismiss()
                showDateDialog = false
            }
            dialog.show()
        }
        Gap.H(Dimens.dp10)
        if(event.transactionType=="Bill"){
            AppTextField.Text(
                value = name,
                onValueChange = {name = it},
                title = "Biller Name",
                description = event.billName?:"Bill name",
                height = Dimens.dp(40),
                modifier = Modifier .fillMaxWidth ()
            )
        }else{
            Column {

                AppTextField.Text(
                    value = number,
                    onValueChange = {number = it},
                    title = "Account Number",
                    description = event.accountNumber?:"0123456789",
                    height = Dimens.dp(40),
                    modifier = Modifier .fillMaxWidth ()
                )
                Gap.H(Dimens.dp10)
                AppTextField.Text(
                    value = name,
                    onValueChange = {name = it},
                    title = "Account Name",
                    description = event.obligee?:"John Doe",
                    height = Dimens.dp(40),
                    modifier = Modifier .fillMaxWidth ()
                )
            }
        }
        Gap.H(Dimens.dp10)
        AppTextField.Text(
            value = note,
            onValueChange = {note = it},
            title = "Notes (Optional)",
            description = "Add any notes...",
            maxLines = 3,
            height = Dimens.dp(75),
            modifier = Modifier .fillMaxWidth ()
        )
        Gap.H(Dimens.dp10)
        Row(
            modifier
                .fillMaxWidth()

            ){
            AppButton.Outlined(
                modifier.weight(1F),
                text="Cancel",
                onTap = onDismiss
            )
            Gap.W(Dimens.dp8)
            AppButton.Primary(
                modifier.weight(1F),
                text="Save Changes",
                onTap = {
                    vm.editEvent(
                        amount = amount.ifEmpty { event.amount },
                        title = title.ifEmpty { event.title },
                        date = selectedDate.ifEmpty { event.date },
                        name = name.ifEmpty {if(event.transactionType=="Bill") event.billName?:"" else event.obligee?:""},
                        number = number.ifEmpty { event.accountNumber?:""},
                        description = note.ifEmpty { event.description?:""},
                        transactionType = event.transactionType,
                        id = event.id,
                    )
                }
            )
        }
        Gap.H(Dimens.dp10)
    }
}