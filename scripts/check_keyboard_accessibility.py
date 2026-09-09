#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
VIEW = ROOT / "android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardView.kt"
DELEGATE = ROOT / "android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardAccessibilityDelegate.kt"
EDITOR_ACTION_POLICY = ROOT / "android/app/src/main/kotlin/com/goreecloud/keyboard/EditorActionPolicy.kt"
TEST = ROOT / "android/app/src/androidTest/kotlin/com/goreecloud/keyboard/KeyboardAccessibilityRuntimeTest.kt"
INPUT_SURFACE_TEST = ROOT / "android/app/src/androidTest/kotlin/com/goreecloud/keyboard/KeyboardInputSurfaceRuntimeTest.kt"
GRADLE = ROOT / "android/app/build.gradle.kts"
ACTION_IDS = ROOT / "android/app/src/main/res/values/accessibility_ids.xml"
STRINGS = ROOT / "android/app/src/main/res/values/strings.xml"


def fail(message: str) -> None:
    raise SystemExit(f"Keyboard accessibility boundary failed: {message}")


def require_all(label: str, text: str, markers: tuple[str, ...]) -> None:
    for marker in markers:
        if marker not in text:
            fail(f"{label} missing `{marker}`")


def main() -> None:
    for path in (
        VIEW,
        DELEGATE,
        EDITOR_ACTION_POLICY,
        TEST,
        INPUT_SURFACE_TEST,
        GRADLE,
        ACTION_IDS,
        STRINGS,
    ):
        if not path.is_file():
            fail(f"missing required evidence: {path.relative_to(ROOT)}")

    view_text = VIEW.read_text(encoding="utf-8")
    delegate_text = DELEGATE.read_text(encoding="utf-8")
    editor_action_text = EDITOR_ACTION_POLICY.read_text(encoding="utf-8")
    test_text = TEST.read_text(encoding="utf-8")
    input_surface_test_text = INPUT_SURFACE_TEST.read_text(encoding="utf-8")
    gradle_text = GRADLE.read_text(encoding="utf-8")
    action_ids_text = ACTION_IDS.read_text(encoding="utf-8")
    strings_text = STRINGS.read_text(encoding="utf-8")

    require_all(
        "KeyboardView virtual-control integration",
        view_text,
        (
            "private val accessibilityDelegate = KeyboardAccessibilityDelegate(this)",
            "ViewCompat.setAccessibilityDelegate(this, accessibilityDelegate)",
            "override fun dispatchHoverEvent(event: MotionEvent)",
            "accessibilityDelegate.dispatchHoverEvent(event)",
            "internal fun accessibilityTargets()",
            "internal fun accessibilityTarget(id: Int)",
            "internal fun performAccessibilityTarget(id: Int)",
            "private var editorAction: EditorActionPresentation = EditorActionPolicy.Enter",
            "fun setEditorAction(value: EditorActionPresentation)",
            'Action.SHIFT -> "Shift"',
            'Action.BACKSPACE -> "Backspace"',
            'Action.SPACE -> "Space"',
            "Action.ENTER -> editorAction.accessibilityLabel",
            "label = hit.entry.accessibilityLabel",
            'label = "Suggestion ${hit.value}"',
            "accessibilityDelegate.invalidateVirtualRoot()",
        ),
    )

    require_all(
        "adaptive editor action accessibility policy",
        editor_action_text,
        (
            'accessibilityLabel = "Enter"',
            "EditorInfo.IME_FLAG_NO_ENTER_ACTION",
            'EditorInfo.IME_ACTION_GO -> EditorActionPresentation("Go", "Go", EditorInfo.IME_ACTION_GO)',
            'EditorInfo.IME_ACTION_SEARCH -> EditorActionPresentation("⌕", "Search", EditorInfo.IME_ACTION_SEARCH)',
            'EditorInfo.IME_ACTION_SEND -> EditorActionPresentation("Send", "Send", EditorInfo.IME_ACTION_SEND)',
            'EditorInfo.IME_ACTION_NEXT -> EditorActionPresentation("Next", "Next", EditorInfo.IME_ACTION_NEXT)',
            'EditorInfo.IME_ACTION_DONE -> EditorActionPresentation("Done", "Done", EditorInfo.IME_ACTION_DONE)',
            'EditorInfo.IME_ACTION_PREVIOUS -> EditorActionPresentation("Prev", "Previous", EditorInfo.IME_ACTION_PREVIOUS)',
        ),
    )

    require_all(
        "ExploreByTouchHelper delegate",
        delegate_text,
        (
            "class KeyboardAccessibilityDelegate(",
            ") : ExploreByTouchHelper(keyboardView)",
            "override fun getVirtualViewAt(x: Float, y: Float): Int",
            "override fun getVisibleVirtualViews(virtualViewIds: MutableList<Int>)",
            "override fun onPopulateNodeForVirtualView(",
            'node.className = "android.widget.Button"',
            "node.isClickable = true",
            "node.isSelected = target.selected",
            "node.stateDescription = stateDescriptionFor(target)",
            "AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK",
            "node.isLongClickable = true",
            "R.string.accessibility_alternates_available",
            "AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_LONG_CLICK",
            "R.string.accessibility_insert_alternate",
            "KeyAlternates.forKey(target.label)",
            "emojiSearchKeyboardIsActive()",
            'target.label == "Clear emoji search" || target.label == "Close emoji search"',
            "override fun onPerformActionForVirtualView(",
            "AccessibilityNodeInfo.ACTION_LONG_CLICK",
            "R.string.accessibility_alternates_announcement",
            "R.string.accessibility_inserted_alternate",
            "keyboardView.listener?.onText(value)",
            "keyboardView.performAccessibilityTarget(virtualViewId)",
            "AccessibilityEvent.TYPE_VIEW_CLICKED",
        ),
    )

    require_all(
        "resource-backed accessibility copy",
        strings_text,
        (
            '<string name="accessibility_state_on">',
            '<string name="accessibility_state_off">',
            '<string name="accessibility_state_selected">',
            '<string name="accessibility_unavailable_control">',
            '<string name="accessibility_alternates_available">',
            '<string name="accessibility_insert_alternate">',
            '<string name="accessibility_alternates_announcement">',
            '<string name="accessibility_inserted_alternate">',
        ),
    )

    require_all(
        "Android virtual-node runtime evidence",
        test_text,
        (
            "view.accessibilityNodeProvider",
            "createAccessibilityNodeInfo(q.id)",
            "AccessibilityNodeInfo.ACTION_CLICK",
            "longPressAlternatesAreDiscoverableAndActionableThroughNativeNodeActions",
            "AccessibilityNodeInfo.ACTION_LONG_CLICK",
            "R.string.accessibility_alternates_available",
            "R.string.accessibility_insert_alternate",
            '"Emoji-search query keys must not gain long-press alternate semantics"',
            '"Emoji-search query keys must not expose alternate custom actions"',
            "view.performAccessibilityTarget(hello.id)",
            'it.label == "Search emoji"',
            'it.label == "Clear emoji search"',
            'it.label == "Close emoji search"',
            'it.label == "Shift"',
            "R.string.accessibility_state_off",
            "R.string.accessibility_state_on",
            "R.string.accessibility_state_selected",
        ),
    )

    require_all(
        "adaptive editor action runtime evidence",
        input_surface_test_text,
        (
            "adaptiveEditorActionChangesTheRenderedAccessibilityContract",
            "EditorActionPolicy.resolve(EditorInfo.IME_ACTION_DONE)",
            'it.label == "Done"',
            "EditorActionPolicy.resolve(EditorInfo.IME_ACTION_SEARCH)",
            'it.label == "Search"',
        ),
    )

    require_all(
        "Android accessibility dependency",
        gradle_text,
        ('implementation("androidx.customview:customview:1.2.0")',),
    )

    for index in range(7):
        marker = f'<item name="accessibility_alternate_{index}" type="id" />'
        if marker not in action_ids_text:
            fail(f"custom alternate action IDs missing `{marker}`")

    for forbidden in (
        "InputConnection",
        "ClipboardManager",
        "getTextBeforeCursor",
        "getTextAfterCursor",
        "SharedPreferences",
        "HttpURLConnection",
        "java.net.",
    ):
        if forbidden in delegate_text:
            fail(f"accessibility delegate gained forbidden data authority `{forbidden}`")

    print(
        "Keyboard virtual accessibility boundary passed: custom-drawn keys, semantic adaptive editor actions, "
        "suggestions, emoji categories, local emoji-search results, and bounded local key alternates expose "
        "actionable native accessibility semantics with resource-backed user-facing alternate actions and state "
        "descriptions, without editor, clipboard, persistence, or network authority in the accessibility delegate. "
        "Emoji-search query mode cannot gain alternate-commit authority. Representative translated-resource, RTL, "
        "TalkBack, and Switch Access physical-device acceptance remain separate."
    )


if __name__ == "__main__":
    main()
