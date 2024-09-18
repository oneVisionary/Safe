package com.example.safe.Common

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class Contact(val name: String, val phoneNumber: String)

fun fetchContacts(context: Context): SnapshotStateList<Contact> {
    val contacts = mutableStateListOf<Contact>()

    val cursor: Cursor? = context.contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        null,
        null,
        null
    )

    cursor?.use {
        val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

        while (cursor.moveToNext()) {
            val name = cursor.getString(nameIndex)
            val phoneNumber = cursor.getString(phoneIndex)
            contacts.add(Contact(name, phoneNumber))
        }
    }

    return contacts
}