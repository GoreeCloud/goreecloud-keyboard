package com.goreecloud.keyboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class KeyboardToolbarConfigurationTest {
    @Test
    fun defaultsExposeOnlyImplementedLocalActions() {
        val configuration = KeyboardToolbarConfiguration()

        assertTrue(configuration.enabled)
        assertEquals(
            listOf(
                KeyboardToolbarAction.EMOJI,
                KeyboardToolbarAction.SYMBOLS,
                KeyboardToolbarAction.SETTINGS,
            ),
            configuration.visibleActions(),
        )
        assertTrue(configuration.isVisible())
    }

    @Test
    fun disabledToolbarAndEmptyActionSetCollapseCleanly() {
        assertTrue(
            KeyboardToolbarConfiguration(enabled = false).visibleActions().isEmpty()
        )
        assertFalse(KeyboardToolbarConfiguration(enabled = false).isVisible())

        val noActions = KeyboardToolbarConfiguration(
            enabled = true,
            showEmoji = false,
            showSymbols = false,
            showSettings = false,
        )
        assertTrue(noActions.visibleActions().isEmpty())
        assertFalse(noActions.isVisible())
    }

    @Test
    fun actionSelectionPreservesDeterministicToolbarOrder() {
        val configuration = KeyboardToolbarConfiguration(
            enabled = true,
            showEmoji = true,
            showSymbols = false,
            showSettings = true,
        )

        assertEquals(
            listOf(KeyboardToolbarAction.EMOJI, KeyboardToolbarAction.SETTINGS),
            configuration.visibleActions(),
        )
    }
}
