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
            "InputPrivacyClassifier.isSensitive(inputType)",
            "EditorSuggestionPolicy.shouldSuppress(inputType)",
            "composingWord.clear()",
        ),
    )

    reset_session = function_body(source, "private fun resetEditorSession()")
    require_all(
        "editor-session teardown",
        reset_session,
        (
            "shifted = false",
            "sensitiveInput = false",
            "suggestionsSuppressed = false",
            "composingWord.clear()",
            "keyboardView?.setLayer(KeyboardLayer.LETTERS)",
            "keyboardView?.setShifted(false)",
            "keyboardView?.setSuggestions(emptyList())",
        ),
    )
    if "updateSuggestions()" in reset_session:
        fail("editor-session teardown must clear the suggestion strip instead of repopulating candidates")

    print(
        "Keyboard editor privacy lifecycle boundary passed: current editor policy is applied at "
        "onStartInput/onStartInputView; transient composing, sensitive/no-suggestions policy, shift, "
        "layer, and visible candidates are cleared at both onFinishInput and onFinishInputView."
    )


if __name__ == "__main__":
    main()
