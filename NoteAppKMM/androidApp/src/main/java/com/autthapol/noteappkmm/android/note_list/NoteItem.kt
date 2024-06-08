package com.autthapol.noteappkmm.android.note_list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.autthapol.noteappkmm.domain.note.Note
import com.autthapol.noteappkmm.domain.time.DateTimeUtil

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    backgroundColor: Color,
    onNoteClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    val formattedDate = remember {
        DateTimeUtil.formatNoteDate(note.created)
    }

    Column(modifier = modifier
        .clip(RoundedCornerShape(5.dp))
        .background(backgroundColor)
        .clickable { onNoteClicked() }
        .padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = note.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Icon(
                modifier = Modifier.clickable(remember { MutableInteractionSource() }, null) {
                    onDeleteClicked()
                },
                imageVector = Icons.Default.Close,
                contentDescription = "Delete note"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = note.content, fontWeight = FontWeight.Light)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.align(Alignment.End),
            text = formattedDate,
            color = Color.DarkGray
        )
    }
}