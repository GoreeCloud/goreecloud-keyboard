#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
SERVICE = ROOT / "android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardService.kt"


def fail(message: str) -> None:
    raise SystemExit(f"Keyboard editor privacy lifecycle boundary failed: {message}")


def function_body(source: str, signature: str) -> str:
    start = source.find(signature)
    if start < 0:
        fail(f"missing `{signature}`")
    brace = source.find("{", start)
    if brace < 0:
        fail(f"missing body for `{signature}`")

    depth = 0
    for index in range(brace, len(source)):
        character = source[index]
        if character == "{":
            depth += 1
        elif character == "}":
            depth -= 1
            if depth == 0:
                return source[brace + 1 : index]
    fail(f"unterminated body for `{signature}`")
    raise AssertionError("unreachable")


def require_all(label: str, body: str, markers: tuple[str, ...]) -> None:
    for marker in markers:
        if marker not in body:
            fail(f"{label} missing `{marker}`")


def main() -> None:
    if not SERVICE.is_file():
        fail(f"missing {SERVICE.relative_to(ROOT)}")

    source = SERVICE.read_text(encoding="utf-8")
    require_all(
        "inactive process default",
        source,
        (
            "private var sensitiveInput = true",
            "private var suggestionsSuppressed = true",
        ),
    )

    start_input = function_body(
        source,
        "override fun onStartInput(attribute: EditorInfo?, restarting: Boolean)",
    )
    require_all(
        "editor-session start",
        start_input,
        (
            "super.onStartInput(attribute, restarting)",
            "beginEditorSession(attribute)",
        ),
    )

    start_view = function_body(
        source,
        "override fun onStartInputView(info: EditorInfo?, restarting: Boolean)",
    )
    require_all(
        "input-view start",
        start_view,
        (
            "super.onStartInputView(info, restarting)",
            "beginEditorSession(info)",
            "updateSuggestions()",
        ),
    )

    finish_input = function_body(source, "override fun onFinishInput()")
    require_all(
        "editor-session finish",
        finish_input,
        (
            "super.onFinishInput()",
            "resetEditorSession()",
        ),
    )

    finish_view = function_body(
        source,
        "override fun onFinishInputView(finishingInput: Boolean)",
    )
    require_all(
        "input-view finish",
        finish_view,
        (
            "super.onFinishInputView(finishingInput)",
            "resetEditorSession()",
        ),
    )

    begin_session = function_body(source, "private fun beginEditorSession(info: EditorInfo?)")
    require_all(
        "editor-session policy initialization",
        begin_session,
        (
            "shifted = false",
            "composingWord.clear()",
            "if (info == null)",
            "sensitiveInput = true",
            "suggestionsSuppressed = true",
            "val inputType = info.inputType",
            "InputPrivacyClassifier.isSensitive(inputType)",
            "EditorSuggestionPolicy.shouldSuppress(inputType)",
        ),
    )
    if "info?.inputType ?: 0" in begin_session:
        fail("unknown EditorInfo must not fall through as ordinary input type 0")

    null_policy = """if (info == null) {
            // Unknown editor metadata must not silently receive ordinary-field privileges. Treat it
            // as sensitive so backspace avoids surrounding-text inspection and suggestions remain
            // suppressed until Android provides a concrete EditorInfo for the active session.
            sensitiveInput = true
            suggestionsSuppressed = true
            return
        }"""
    if null_policy not in begin_session:
        fail("null EditorInfo must fail closed as sensitive and suggestions-suppressed before return")

    reset_session = function_body(source, "private fun resetEditorSession()")
    require_all(
        "editor-session teardown",
        reset_session,
        (
            "shifted = false",
            "sensitiveInput = true",
            "suggestionsSuppressed = true",
            "composingWord.clear()",
            "keyboardView?.setLayer(KeyboardLayer.LETTERS)",
            "keyboardView?.setShifted(false)",
            "keyboardView?.setSuggestions(emptyList())",
        ),
    )
    if "sensitiveInput = false" in reset_session or "suggestionsSuppressed = false" in reset_session:
        fail("inactive editor teardown must not relax the fail-closed transient privacy policy")
    if "updateSuggestions()" in reset_session:
        fail("editor-session teardown must clear the suggestion strip instead of repopulating candidates")

    print(
        "Keyboard editor privacy lifecycle boundary passed: inactive/no-editor state is fail-closed; "
        "current editor policy is applied at onStartInput/onStartInputView; null editor metadata "
        "remains sensitive and suggestions-suppressed; transient composing, shift, layer, and visible "
        "candidates are cleared at both onFinishInput and onFinishInputView."
    )


if __name__ == "__main__":
    main()
