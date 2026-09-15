package com.example.silvius12.ui

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

/**
 * Turns an [AutoCompleteTextView] into a plain picker: tapping it always offers
 * the whole list.
 *
 * Left to itself the view treats its own contents as a search term, so a field
 * that already holds a value ("Coffee", "+1") filters the list down to that one
 * entry and the popup stops appearing at all. Re-attaching the adapter before
 * each open clears that filter, and committing a choice with `filter = false`
 * stops the selection from becoming the next search term.
 */
fun AutoCompleteTextView.asPicker(
    items: List<String>,
    onPick: (String) -> Unit = {}
) {
    val adapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, items)

    fun open() {
        // Re-attaching resets any filtering left over from the current text.
        setAdapter(adapter)
        if (!isPopupShowing) showDropDown()
    }

    setAdapter(adapter)
    setOnClickListener { open() }
    setOnFocusChangeListener { _, hasFocus -> if (hasFocus) open() }
    setOnItemClickListener { parent, _, position, _ ->
        val picked = parent.getItemAtPosition(position) as String
        setText(picked, false)
        onPick(picked)
    }
}
